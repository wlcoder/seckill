package com.wl.sec_kill.mapper;

import com.wl.sec_kill.entity.Goods;

import java.util.List;

/**
 * @author : wl
 * @Description :
 * @date : 2020/5/30 14:50
 */
public interface GoodsMapper {
    Goods findById(Long goodsId);

    List<Goods> findAll();

    List<Goods> findLastGoods();
}
