package com.wl.sec_kill.service.serviceImpl;

import com.wl.sec_kill.entity.*;
import com.wl.sec_kill.mapper.*;
import com.wl.sec_kill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : wl
 * @Description :
 * @date : 2020/5/30 14:57
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsCoverMapper goodsCoverMapper;
    @Autowired
    private GoodsDetailMapper goodsDetailMapper;
    @Autowired
    private GoodsParamMapper goodsParamMapper;
    @Autowired
    private EvaluateMapper evaluateMapper;

    @Override
    @Cacheable(value = "goods", key = "#goodsId")
    public Goods findGoods(Long goodsId) {
        return goodsMapper.findById(goodsId);
    }

    @Override
    public List<Goods> findAllGoods() {
        return goodsMapper.findAll();
    }

    @Override
    @Cacheable(value = "covers", key = "#goodsId")
    public List<GoodsCover> findCovers(Long goodsId) {
        return goodsCoverMapper.findByGoodsId(goodsId);
    }

    @Override
    @Cacheable(value = "details", key = "#goodsId")
    public List<GoodsDetail> findDetails(Long goodsId) {
        return goodsDetailMapper.findByGoodsId(goodsId);
    }

    @Override
    @Cacheable(value = "params", key = "#goodsId")
    public List<GoodsParam> findParams(Long goodsId) {
        return goodsParamMapper.findByGoodsId(goodsId);
    }

    @Override
    public List<Goods> findLastGoods() {
        return goodsMapper.findLastGoods();
    }

    @Override
    public List<Evaluate> findEvaluates(Long goodsId) {
        return evaluateMapper.findByGoodsId(goodsId);
    }

}
