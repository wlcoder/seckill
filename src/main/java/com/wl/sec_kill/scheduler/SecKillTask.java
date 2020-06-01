package com.wl.sec_kill.scheduler;

import com.wl.sec_kill.entity.PromotionSecKill;
import com.wl.sec_kill.mapper.PromotionSecKillMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SecKillTask {
    @Resource
    private PromotionSecKillMapper promotionSecKillMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void startSecKill() {
        List<PromotionSecKill> list = promotionSecKillMapper.findUnstartSecKill();
        for (PromotionSecKill ps : list) {
            System.out.println(ps.getPsId() + "秒杀活动已启动");
            //删掉以前重复的活动任务缓存
            redisTemplate.delete("seckill:count:" + ps.getPsId());
            //有几个库存商品，则初始化几个list对象
            for (int i = 0; i < ps.getPsCount(); i++) {
                redisTemplate.opsForList().rightPush("seckill:count:" + ps.getPsId(), ps.getGoodsId());
            }
            ps.setStatus(1);
            promotionSecKillMapper.update(ps);
        }
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void endSecKill() {
        List<PromotionSecKill> psList = promotionSecKillMapper.findExpireSecKill();
        for (PromotionSecKill ps : psList) {
            System.out.println(ps.getPsId() + "秒杀活动已结束");
            ps.setStatus(2);
            promotionSecKillMapper.update(ps);
            redisTemplate.delete("seckill:count:" + ps.getPsId());
        }
    }
}
