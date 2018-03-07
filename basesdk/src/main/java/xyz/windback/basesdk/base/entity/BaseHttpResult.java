package xyz.windback.basesdk.base.entity;

import java.io.Serializable;

/**
 * Class description
 * 请求返回类
 *
 * @author WJ
 * @version 1.0, 2018-1-16
 */

public class BaseHttpResult<T> implements Serializable {
    private int state;
    private String msg;
    private T data;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
