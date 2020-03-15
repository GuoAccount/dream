package com.dream.common.pojo;

import java.io.Serializable;

public class EasyUiTreeNode implements Serializable {
    //树的ID cid
    private Long id;
    //树的文本
    private String text;
    //树的状态
    private String state;

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
