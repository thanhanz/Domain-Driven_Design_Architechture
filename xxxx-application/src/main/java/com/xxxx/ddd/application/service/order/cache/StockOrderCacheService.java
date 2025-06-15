package com.xxxx.ddd.application.service.order.cache;

import com.xxxx.ddd.application.model.cache.TicketDetailCache;
import com.xxxx.ddd.application.service.ticket.cache.TicketDetailCacheServiceRefactor;
import com.xxxx.ddd.infrastructure.cache.redis.RedisInfrasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class StockOrderCacheService {

    @Autowired
    private TicketDetailCacheServiceRefactor ticketDetailCacheServiceRefactor;

    @Autowired
    private RedisInfrasService redisInfrasService;

    public boolean AddStockAvailableToCache(Long ticketId) {
        // That's remember check validation(*)
        if(ticketId == null) {
            return false;
        }
        // get stock_available from mysql
        TicketDetailCache ticketDetailCache = ticketDetailCacheServiceRefactor.getTicketDetail(ticketId, null);
        if(ticketDetailCache == null) {
            return false;
        }
        String keyStockItemCache = getKeyStockItemCache(ticketId);
        log.info("get->getKeyStockItemCache() | {}, {}, {}", ticketId, keyStockItemCache,
                ticketDetailCache.getTicketDetail().getStockAvailable());
        // stockAvailable = ticketDetailCache.getTicketDetail().getStockAvailable();
        redisInfrasService.setInt(keyStockItemCache, ticketDetailCache.getTicketDetail().getStockAvailable());
        return true;
    }

    // decreaseStockCache
    public int decreaseStockCache(Long ticketId, Integer quantity) {
        // 1. Get Stock Available
        String keyStockNormal = getKeyStockItemCache(ticketId);
        int stockAvailable = redisInfrasService.getInt(keyStockNormal); // 100
        log.info("stockAvailable Normal: {}, {}, {} ", keyStockNormal, stockAvailable, String.valueOf(stockAvailable - quantity));
        // 2. Decrease Stock

        if(stockAvailable >= quantity){ // 100 > 1 = 99
            redisInfrasService.setInt(keyStockNormal, stockAvailable - quantity); // 99
            log.info("stockAvailable racing...: {}", stockAvailable - quantity);
            return 1;
        }
        return 0; // stockAvailable = 0 , quantity = 1
    }

    public int decreaseStockCacheByLUA(Long ticketId, Integer quantity) {
        // 1. Get Stock Available
        String keyStockLUA = getKeyStockItemCache(ticketId);
        String luaScript = "local stock = tonumber(redis.call('GET', KEYS[1])); " +
                "if (stock >= tonumber(ARGV[1])) then " +
                "   redis.call('SET', KEYS[1], stock - tonumber(ARGV[1])); " +
                "   return 1; " +
                "end; " +
                "   return 0; ";
        // Execute Lua script
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Long result = redisInfrasService.getRedisTemplate().execute(redisScript, Collections.singletonList(keyStockLUA), quantity);
//        log.info("Lua result: {}", result.intValue());
        return result.intValue(); // Shouldn't
    }


    private String getKeyStockItemCache(Long ticketId) {
        return "TICKET:"+ ticketId + ":STOCK";
    }

    private String getKeyStockCacheLUA(Long ticketId){
        return "LUA:TICKET:" + ticketId + ":STOCK";
    }
}
