package com.dream.search.listener;

import com.dream.search.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemUpdateListener implements MessageListener {
    @Autowired
    private SearchItemService searchItemService;
    @Override
    public void onMessage(Message message) {
        try {
            //把发送消息的类型转型成ETextMessage
            //判断message能不能被TextMessage实例化
            if (message instanceof TextMessage) {
                TextMessage message1 = (TextMessage) message;
                long itemId = Long.parseLong(message1.getText());
                //向索引库中添加更新索引
                searchItemService.updateSearchItemById(itemId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
