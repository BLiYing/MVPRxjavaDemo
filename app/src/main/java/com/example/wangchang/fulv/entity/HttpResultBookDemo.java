package com.example.wangchang.fulv.entity;

/**
 * Describe:实体的基类泛型
 * Created by liying on 2018/2/7.
 */
public class HttpResultBookDemo<T> {

    //用来模仿resultCode和resultMessage
    private int count;
    //用来模仿Data
    private T subjects;

    private int start;
    private int total;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }




}
