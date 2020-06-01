package com.wl.sec_kill.mapper;

import com.wl.sec_kill.entity.Evaluate;

import java.util.List;

public interface EvaluateMapper {
     List<Evaluate> findByGoodsId(Long goodsId);
}
