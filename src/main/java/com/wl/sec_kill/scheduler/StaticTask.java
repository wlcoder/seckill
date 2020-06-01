package com.wl.sec_kill.scheduler;

import com.wl.sec_kill.entity.Goods;
import com.wl.sec_kill.service.GoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StaticTask {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private Configuration freemarkerConfig;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void doStatic() throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate("goods.ftl");
        List<Goods> allGoods = goodsService.findLastGoods();
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
            System.out.println(new Date() + ":" + targetFile + "已生成！");
            out.close();
        }
    }
}
