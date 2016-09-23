package com.zw.framework.controller.dto;

/**
 * RESTFul API返回值基类
 * <p>
 * Created by zhangws on 16/9/23.
 */
public class BaseJsonResponse {

    private boolean result;

    private String title;

    private Object data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
