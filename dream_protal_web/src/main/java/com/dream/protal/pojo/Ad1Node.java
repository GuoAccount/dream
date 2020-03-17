package com.dream.protal.pojo;

public class Ad1Node {
    private String src;
    private String src2;
    private String href;
    private String width;
    private String width2;
    private String height;
    private String height2;
    private String alt;

    public Ad1Node() {
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc2() {
        return src2;
    }

    public void setSrc2(String src2) {
        this.src2 = src2;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth2() {
        return width2;
    }

    public void setWidth2(String width2) {
        this.width2 = width2;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight2() {
        return height2;
    }

    public void setHeight2(String height2) {
        this.height2 = height2;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Ad1Node(String src, String src2, String href, String width, String width2, String height, String height2, String alt) {
        this.src = src;
        this.src2 = src2;
        this.href = href;
        this.width = width;
        this.width2 = width2;
        this.height = height;
        this.height2 = height2;
        this.alt = alt;
    }
}
