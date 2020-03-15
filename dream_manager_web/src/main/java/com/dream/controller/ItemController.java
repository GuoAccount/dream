package com.dream.controller;

import com.alibaba.fastjson.JSON;
import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.pojo.TbItem;
import com.dream.service.ItemService;
import com.dream.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Value("${DREAM_IMAGE_SERVER_URL}")
    private String DREAM_IMAGE_SERVER_URL;
    /**
     *
     * @param uploadFile MultipartFile前端发送的请求如果是一个文件，则需要和使用这个对象来接收
     * @return
     * MediaType.TEXT_PLAIN_VALUE+";charset=utf-8"
     * 指定接收前端发送的数据格式 ,前端发送的是一个文件格式，不是String 也不是json ajax发送文件时、
     * 后台通过定义接收方式来获取完整的文件
     * applicaton /json ;charset-utf-8
     * 如果使用的不是ajax，form表单  就要在form表单中加入文件传输的格式定义
     * enctype="multipart/form-data"
     */
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    public String uploading(MultipartFile uploadFile){

        try {
            //1、先获取数据的扩展名  .jpg   add.jpg   找到最后一个. 的下标之后的字符串 .jpg->jpg
            String originalFilename = uploadFile.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            //2、获取文件的字节数组
            byte[] bytes = uploadFile.getBytes();

            //3、通过FastFDSClient来实现上传图片(需要有字节数组，和扩展名，但是扩展名不包括“.”)
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/fastdfs.conf");
            // 返回值  group1/M00/00/00/图片地址
            String imgUrl = fastDFSClient.uploadFile(bytes, substring);
            //再根据服务器的ip地址，拼接成一个完整的url 192.168.0.103
            String path=DREAM_IMAGE_SERVER_URL+imgUrl;

            //4、成功 设置map，要返回的JSON的数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("error",0);
            map.put("url",path);//把url给添加商品时的要添加的图片的地址

            //返回map
            return JSON.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
            //5、失败
            HashMap<String, Object> map = new HashMap<>();
            map.put("error",1);
            map.put("message","上传失败");
            return JSON.toJSONString(map);
        }
    }



    @RequestMapping("/delete")
    public DreamResult delete(Long [] ids){
        System.out.println(ids);
        return itemService.delete(Arrays.asList(ids));
    }

    @RequestMapping("/save")
    public DreamResult save(TbItem tbItem,String desc){
        return itemService.saveItem(tbItem,desc);
    }

    @RequestMapping("/list")
    public EasyUiDataGridResult list(@RequestParam("page") int pageNum,@RequestParam("rows") int pageSize){
        return itemService.list(pageNum,pageSize);
    }

    @RequestMapping("/selectByKey/{itemId}")
    public TbItem selectByKey(@PathVariable long itemId){
        return itemService.selectByKey(itemId);
    }

}
