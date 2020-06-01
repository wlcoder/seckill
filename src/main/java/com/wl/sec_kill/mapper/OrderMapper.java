package com.wl.sec_kill.mapper;


import com.wl.sec_kill.entity.Order;

public interface OrderMapper {
     void insert(Order order);

     Order findByOrderNo(String orderNo);
}
