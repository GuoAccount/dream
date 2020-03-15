package com.dream.common.util;

import java.util.Random;

public class UUIDUtils {
    public static long getItenId() {
        //1.先得到当前的 时间戳
        long millis = System.currentTimeMillis();
        //2.加上一个两位的随机数0~99的
        int ran = new Random().nextInt(99);//Math.random()*99
        //3.有可能生成的时一位数  则 给他们前面补上零
        String id = millis + String.format("%02d", ran);
        return new Long(id);
    }

    public static void main(String[] args) {
        for (int i=0;i<=10;i++){
            System.out.println(getItenId());
        }
    }
}
