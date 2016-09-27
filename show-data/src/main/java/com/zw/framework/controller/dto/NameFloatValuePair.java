package com.zw.framework.controller.dto;

/**
 * Created by zhangws on 16/9/23.
 */
public class NameFloatValuePair {

    private String name;

    private float value;

    public NameFloatValuePair(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
