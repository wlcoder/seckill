package com.wl.sec_kill.mapper;


import com.wl.sec_kill.entity.GoodsDetail;

import java.util.List;

public interface GoodsDetailMapper {
     List<GoodsDetail> findByGoodsId(Long goodsId);
}
