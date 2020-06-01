package com.wl.sec_kill.service;

import com.wl.sec_kill.entity.Order;
import com.wl.sec_kill.entity.PromotionSecKill;
import com.wl.sec_kill.mapper.OrderMapper;
import com.wl.sec_kill.mapper.PromotionSecKillMapper;
import com.wl.sec_kill.util.SecKillException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PromotionSecKillService {
    @Resource
    private PromotionSecKillMapper promotionSecKillMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource //RabbitMQ客户端
    private RabbitTemplate rabbitTemplate;

    @Resource
    private OrderMapper orderMapper;

    public void processSecKill(Long psId, String userid, Integer num) throws SecKillException {
        PromotionSecKill ps = promotionSecKillMapper.findById(psId);
        if (ps == null) {
            throw new SecKillException("秒杀活动不存在");
        }
        if (ps.getStatus() == 0) {
            throw new SecKillException("秒杀活动还未开始");
        } else if (ps.getStatus() == 2) {
            throw new SecKillException("秒杀活动已经结束");
        }
        Integer goodsId = (Integer) redisTemplate.opsForList().leftPop("seckill:count:" + ps.getPsId());
        if (goodsId != null) {
            //判断是否已经抢购过
            boolean isExisted = redisTemplate.opsForSet().isMember("seckill:users:" + ps.getPsId(), userid);
            if (!isExisted) {
                System.out.println("恭喜您，抢到商品啦。快去下单吧");
                redisTemplate.opsForSet().add("seckill:users:" + ps.getPsId(), userid);
            } else {
                redisTemplate.opsForList().rightPush("seckill:count:" + ps.getPsId(), ps.getGoodsId());
                throw new SecKillException("抱歉，您已经参加过此活动，请勿重复抢购！");
            }
        } else {
            throw new SecKillException("抱歉，该商品已被抢光，下次再来吧！！");
        }


    }

    public String sendOrderToQueue(String userid) {
        System.out.println("准备向队列发送信息");
        //订单基本信息
        Map data = new HashMap();
        data.put("userid", userid);
        String orderNo = UUID.randomUUID().toString();
        data.put("orderNo", orderNo);
        //附加额外的订单信息
        rabbitTemplate.convertAndSend("exchange-order", null, data);
        return orderNo;
    }

    public Order checkOrder(String orderNo) {
        Order order = orderMapper.findByOrderNo(orderNo);
        return order;
    }
}
