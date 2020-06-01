package com.wl.sec_kill.mapper;

import com.wl.sec_kill.entity.PromotionSecKill;

import java.util.List;

public interface PromotionSecKillMapper {
    List<PromotionSecKill> findUnstartSecKill();
    void update(PromotionSecKill ps);
    PromotionSecKill findById(Long psId);
    List<PromotionSecKill> findExpireSecKill();
}
