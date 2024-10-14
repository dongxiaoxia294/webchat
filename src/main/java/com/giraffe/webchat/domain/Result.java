package com.giraffe.webchat.domain;

/**
 * 用于登录响应回给浏览器的数据
 */
public class Result {
    private String message;
    private boolean flag;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
