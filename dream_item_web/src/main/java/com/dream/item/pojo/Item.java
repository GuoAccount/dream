package com.dream.item.pojo;

import com.dream.pojo.TbItem;

public class Item extends TbItem {

    //详情页图片展示的不是一个图片。在数据库中同一个商品多个图片的路径使用逗号隔开存储的字符串。
    // 要想访问图片必须利用逗号作为分组条件，返回图片的字符数组。
    //通过数组下标来访问图片

    //提供getImages
    public String[] getImages(){
        String image = this.getImage();
        if (image!=null&&image!=""){
            return image.split(",");
        }
        return null;
    }
    public Item(){

    }
    public Item(TbItem tbItem){
        //把查询到的TbItem传到该对象中，封装到自己的属性中
        this.setId(tbItem.getId());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setTitle(tbItem.getTitle());


    }
}
