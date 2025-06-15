package com.xxxx.ddd.domain.respository;

import com.xxxx.ddd.domain.model.entity.TickerOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDeductionRepository {
    void insertOrder(String yearMonth, TickerOrder tickerOrder);
    List<Object[]> findAll(String yearMonth);
    Object[] findByOrderNumber(String yearMonth, String orderNumber);
    List<Object[]> findByDateRange(String yearMonth, LocalDateTime startDate, LocalDateTime endDate);
}
