package com.dream.item.listener;

import com.dream.item.pojo.Item;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbItemDesc;
import com.dream.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemChangeGenHtmlMessageListener implements MessageListener {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private ItemService itemService;
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage message1 = (TextMessage) message;

            try {
                String text = message1.getText();
                Long itemId = Long.valueOf(text);
                //调用商品服务查询商品的信息--上午的业务层
                TbItemDesc itemDescById = itemService.getItemDescById(itemId);
                TbItem tbItem = itemService.selectByKey(itemId);
                //把这两个对象作为数据生成静态页面
                this.genHtml("item.ftl",tbItem,itemDescById);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //生成静态网页的方法
    private void genHtml(String telmplateName,TbItem item,TbItemDesc desc) throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate(telmplateName);

        Map map = new HashMap();
        Item item1 = new Item(item);
        map.put("item",item1);
        map.put("itemDesc",desc);
        //指定文件输出的路径 --真实开发中这个路径应该在properties属性文件中配，并且是在linux中，我们这里就直接在本地测试
        FileWriter fileWriter = new FileWriter(new File("C:\\Users\\34851\\Desktop\\Freemakerdemo"+item.getId()+".html"));
        template.process(map,fileWriter);
        fileWriter.close();
    }
}
