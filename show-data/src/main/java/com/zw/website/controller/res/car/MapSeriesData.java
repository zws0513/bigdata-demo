package com.zw.website.controller.res.car;

import com.zw.framework.controller.dto.NameIntValuePair;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
public class MapSeriesData {

    private String name;

    private String type = "map";

    private String mapType;

    private List<NameIntValuePair> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public List<NameIntValuePair> getData() {
        return data;
    }

    public void setData(List<NameIntValuePair> data) {
        this.data = data;
    }
}
