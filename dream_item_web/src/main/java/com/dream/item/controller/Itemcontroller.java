package com.dream.item.controller;

import com.dream.item.pojo.Item;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbItemDesc;
import com.dream.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Itemcontroller {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        //根据商品信息id查找商品信息和详细信息
        TbItem tbItem = itemService.selectByKey(itemId);
        TbItemDesc itemDescById = itemService.getItemDescById(itemId);

        //把tbItem封装到Item对象中（这么做的原因是，逆向工程获得的图片是一个或多个字符串是无法通过地址访问的，所以通过Item继承，可以封装父类所有属性并继承方法，
        // 在不改变父类的方法下，自定义一个获取图片的getImage方法，通过此方法能返回一个图片地址的字符数组，可以通过数组下标来访问图片地址）
        Item item= new Item(tbItem);

        //把item和描述绑定
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDescById);

        return "item";
    }

}
