package com.example.karan.myapplication2.model;

/**
 * Created by karan on 5/3/2018.
 */

public class Home {

    private String head;
    private String subHead;
    private String detail;
    private String img;

    public Home(String head, String subHead, String detail) {
        this.head = head;
        this.subHead = subHead;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSubHead() {
        return subHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
