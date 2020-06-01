package com.wl.sec_kill.mapper;

import com.wl.sec_kill.entity.GoodsParam;

import java.util.List;

public interface GoodsParamMapper {
     List<GoodsParam> findByGoodsId(Long goodsId);
}
