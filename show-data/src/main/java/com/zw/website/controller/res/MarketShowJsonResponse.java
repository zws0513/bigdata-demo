package com.zw.website.controller.res;

import com.zw.framework.controller.dto.BaseJsonResponse;

/**
 * Created by zhangws on 16/9/23.
 */
public class MarketShowJsonResponse extends BaseJsonResponse {

    private int min;

    private int max;

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
}
