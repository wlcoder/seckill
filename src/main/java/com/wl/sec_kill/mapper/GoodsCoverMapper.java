package com.wl.sec_kill.mapper;


import com.wl.sec_kill.entity.GoodsCover;

import java.util.List;

public interface GoodsCoverMapper {
    List<GoodsCover> findByGoodsId(Long goodsId);
}
