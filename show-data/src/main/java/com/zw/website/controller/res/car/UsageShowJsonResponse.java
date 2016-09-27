package com.zw.website.controller.res.car;

import com.zw.framework.controller.dto.BaseJsonResponse;

/**
 * Created by zhangws on 16/9/23.
 */
public class UsageShowJsonResponse extends BaseJsonResponse {

    private int min;

    private int max;

    private String[] legendData;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String[] getLegendData() {
        return legendData;
    }

    public void setLegendData(String[] legendData) {
        this.legendData = legendData;
    }
}
