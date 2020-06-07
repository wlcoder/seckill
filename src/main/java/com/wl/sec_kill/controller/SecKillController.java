package com.wl.sec_kill.controller;

import com.wl.sec_kill.entity.Order;
import com.wl.sec_kill.service.PromotionSecKillService;
import com.wl.sec_kill.util.SecKillException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class SecKillController {
    @Resource
    PromotionSecKillService promotionSecKillService;
    @RequestMapping("/seckill")
    @ResponseBody
    public Map processSecKill(Long psid , String userid){
        Map result = new HashMap();
        try {
            promotionSecKillService.processSecKill(psid , userid , 1);
            String orderNo = promotionSecKillService.sendOrderToQueue(userid);
            Map data = new HashMap();
            data.put("orderNo", orderNo);
            result.put("code", "0");
            result.put("message", "success");
            result.put("data", data);
        } catch (SecKillException e) {
            result.put("code", "500");
            result.put("message", e.getMessage());
        }
        return result;
    }
    @GetMapping("/checkorder")
    public ModelAndView checkOrder(String orderNo){
        Order order =  promotionSecKillService.checkOrder(orderNo);
        ModelAndView mav = new ModelAndView();
        if(order != null){
            mav.addObject("order", order);
            mav.setViewName("/order");
        }else{
            mav.addObject("orderNo", orderNo);
            mav.setViewName("/waiting");
        }
        return mav;
    }

    @Component
    public static class AntiRefreshInterceptor implements HandlerInterceptor {
        @Resource
        //RedisTemplate用于简化Redis操作，在IOC容器中自动被初始化
        private RedisTemplate<String, Object> redisTemplate;
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            System.out.println("PreHandler");
            String clientIP = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            System.out.println(userAgent);
            String key = "anti:refresh:" + DigestUtils.md5Hex(clientIP + "_" + userAgent);

            response.setContentType("text/html;charset=utf-8");

            if(redisTemplate.hasKey("anti:refresh:blacklist")){
                if (redisTemplate.opsForSet().isMember("anti:refresh:blacklist", clientIP)) {
                    response.getWriter().println("检测到您的IP访问异常，已被加入黑名单");
                    return false;
                }
            }


            Integer num = (Integer)redisTemplate.opsForValue().get(key);
            if(num == null){ //第一次访问
                redisTemplate.opsForValue().set(key, 1, 60, TimeUnit.SECONDS);
            }else{

                if(num > 30 && num  < 100){
                    response.getWriter().println("请求过于频繁，请稍后再试!");
                    redisTemplate.opsForValue().increment(key, 1);
                    return false;
                }else if(num >=100){
                    response.getWriter().println("检测到您的IP访问异常，已被加入黑名单");
                    redisTemplate.opsForSet().add("anti:refresh:blacklist" , clientIP);
                    return false;
                }else{
                    redisTemplate.opsForValue().increment(key, 1);
                }
            }
            return true;
        }
    }
}
