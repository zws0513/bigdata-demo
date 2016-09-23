package com.zw.framework.controller.dto;

/**
 * Created by zhangws on 16/9/23.
 */
public class NameIntValuePair {

    private String name;

    private int value;

    public NameIntValuePair(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
