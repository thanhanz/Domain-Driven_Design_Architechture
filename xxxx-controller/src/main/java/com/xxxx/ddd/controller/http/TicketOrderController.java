package com.xxxx.ddd.controller.http;

import com.xxxx.ddd.application.model.TicketDetailDTO;
import com.xxxx.ddd.application.model.TicketOrderDTO;
import com.xxxx.ddd.application.service.order.TicketOrderAppService;
import com.xxxx.ddd.controller.model.enums.ResultUtil;
import com.xxxx.ddd.controller.model.vo.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class TicketOrderController {

    @Autowired
    private TicketOrderAppService ticketOrderAppService;

    /**
        Level 1,2
     */
    @GetMapping("/{ticketId}/{quantity}/order")
    public boolean orderTicketByLevel(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("quantity") int quantity
    ) {
        log.info("Controller:->orderTicketByLevel | {}, {}", ticketId, quantity);
        return ticketOrderAppService.decreaseStockLevel1(ticketId, quantity);
    }

    /**
     Level 3
     */

    @GetMapping("/{ticketId}/{quantity}/cas")
    public boolean orderTicketByLevel3(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("quantity") int quantity
    ) {
        log.info("Controller:->orderTicketByLevel3 | {}, {}", ticketId, quantity);
        return ticketOrderAppService.decreaseStockLevel3CAS(ticketId, quantity);
    }


    @GetMapping("/{userId}/list")
    public ResultMessage<List<TicketOrderDTO>> getListOrderByUser(
            @PathVariable("userId") Long userId,
            @RequestParam("ntable") String ntable
    ) {
        log.info("Controller:->getListOrderByUser | {}, {}", userId, ntable);
        return ResultUtil.data(ticketOrderAppService.findAll(ntable));
    }

    // get orderItem
    @GetMapping("/{userId}/{orderNumber}")
    public ResultMessage<TicketOrderDTO> getOrderByUser(
            @PathVariable("userId") Long userId,
            @PathVariable("orderNumber") String orderNumber
    ) {
        log.info("Controller:->getOrderByUser | {}, {}", userId, orderNumber);
        return ResultUtil.data(ticketOrderAppService.findByOrderNumber("2025xx",orderNumber));
    }
}
