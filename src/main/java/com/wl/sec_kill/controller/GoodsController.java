package com.wl.sec_kill.controller;

import com.wl.sec_kill.entity.*;
import com.wl.sec_kill.service.GoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : wl
 * @Description :
 * @date : 2020/5/30 11:19
 */
@Controller
@Slf4j
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private Configuration freemarkerConfig;

    @RequestMapping("/showgoods")
    public ModelAndView showGoods(Long gid) {
        log.info("请求的gid: " + gid);
        Goods goods = goodsService.findGoods(gid);
        List<GoodsCover> goodsCovers = goodsService.findCovers(gid);
        List<GoodsDetail> goodsDetails = goodsService.findDetails(gid);
        List<GoodsParam> goodsParams = goodsService.findParams(gid);
        ModelAndView mav = new ModelAndView("/goods");
        mav.addObject("goods", goods);
        mav.addObject("covers", goodsCovers);
        mav.addObject("details", goodsDetails);
        mav.addObject("params", goodsParams);
        return mav;
    }

    @GetMapping("/evaluate/{gid}")
    @ResponseBody
    public List<Evaluate> findEvaluates(@PathVariable("gid") Long goodsId) {
        return goodsService.findEvaluates(goodsId);

    }

    @GetMapping("/static/{gid}")
    @ResponseBody
    public String doStatic(@PathVariable("gid") Long gid) throws IOException, TemplateException {
        //获取模板对象
        Template template = freemarkerConfig.getTemplate("goods.ftl");
        Map param = new HashMap();
        param.put("goods", goodsService.findGoods(gid));
        param.put("covers", goodsService.findCovers(gid));
        param.put("details", goodsService.findDetails(gid));
        param.put("params", goodsService.findParams(gid));
        File targetFile = new File("d:/my_Java/goods/" + gid + ".html");
        FileWriter out = new FileWriter(targetFile);
        template.process(param, out);
        out.close();
        return targetFile.getPath();
    }

    @GetMapping("/static_all")
    @ResponseBody
    public String doStatic() throws IOException, TemplateException {
        //获取模板对象
        Template template = freemarkerConfig.getTemplate("goods.ftl");
        List<Goods> allGoods = goodsService.findAllGoods();
        for (Goods g : allGoods) {
            Long gid = g.getGoodsId();
            Map param = new HashMap();
            param.put("goods", goodsService.findGoods(gid));
            param.put("covers", goodsService.findCovers(gid));
            param.put("details", goodsService.findDetails(gid));
            param.put("params", goodsService.findParams(gid));
            File targetFile = new File("d:/my_Java/goods/" + gid + ".html");
            FileWriter out = new FileWriter(targetFile);
            template.process(param, out);
            out.close();
        }

        return "ok";
    }

}
