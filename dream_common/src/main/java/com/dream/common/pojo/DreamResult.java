package com.dream.common.pojo;

import java.io.Serializable;

public class DreamResult implements Serializable {
      //状态码 定义为跟http响应的状态码含义一样 200代表成功
    private Integer status;
    //响应之后的提示
    private String msg;
    //向英国如果需要带数据，封装到data
    private Object data;


    //1如果成功了 但是没有数据要返回，直接调用OK方法代表成功
    public static DreamResult ok(){
        return new DreamResult(null);
    }
    //2如果失败了，则自定义DreamResule对象
    public static DreamResult build(Integer status,String name,Object data){
        return new DreamResult(status,name,data);
    }
    //3如果成功了，需要返回对象
    public static DreamResult ok(Object data){
        return new DreamResult(data);
    }

    public DreamResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public DreamResult() {
    }
   //如果返回有数据，则代表成功
    public DreamResult(Object data) {
        this.data = data;
        this.status=200;
        this.msg="ok";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
