package com.wl.sec_kill.service;

import com.wl.sec_kill.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : wl
 * @Description :
 * @date : 2020/5/30 14:55
 */
public interface GoodsService {

    Goods findGoods(Long goodsId);

    List<Goods> findAllGoods();

    List<GoodsCover> findCovers(Long goodsId);

    List<GoodsDetail> findDetails(Long goodsId);

    List<GoodsParam> findParams(Long GoodsId);

    List<Goods> findLastGoods();

    List<Evaluate> findEvaluates(Long goodsId);


}
