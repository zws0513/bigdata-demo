package com.zw.website.controller.res.car;

import com.zw.framework.controller.dto.BaseJsonResponse;

/**
 * Created by zhangws on 16/9/23.
 */
public class DiffBrandMonthSaleShowJsonResponse extends BaseJsonResponse {

    private String[] xAxisData;

    private String name;

    public String[] getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(String[] xAxisData) {
        this.xAxisData = xAxisData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
