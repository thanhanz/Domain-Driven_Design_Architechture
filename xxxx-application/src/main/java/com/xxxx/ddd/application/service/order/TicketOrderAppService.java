package com.xxxx.ddd.application.service.order;

import com.xxxx.ddd.application.model.TicketOrderDTO;
import com.xxxx.ddd.domain.model.entity.TickerOrder;

import java.util.List;

public interface TicketOrderAppService {

    boolean decreaseStockLevel1(Long tickerId, int quantity);
    boolean decreaseStockLevel2(Long tickerId, int quantity);
    boolean decreaseStockLevel3CAS(Long tickerId, int quantity);

    int getStockAvailable(Long ticketId);

    // order..
    List<TicketOrderDTO> findAll(String yearMonth);
    boolean insertOrder(String yearMonth, TickerOrder tickerOrder);
    TicketOrderDTO findByOrderNumber(String yearMonth, String orderNumber);
}
