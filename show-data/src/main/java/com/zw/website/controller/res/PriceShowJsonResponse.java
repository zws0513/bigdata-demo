package com.zw.website.controller.res;

import com.zw.framework.controller.dto.BaseJsonResponse;

/**
 * Created by zhangws on 16/9/23.
 */
public class PriceShowJsonResponse extends BaseJsonResponse {

    public static class EntityData {

        private String name;

        private String type = "line";

        private String stack = "总量";

        private Float[] data;

        public EntityData() {

        }

        public EntityData(String name, Float[] data) {
            this.name = name;
            this.data = data;
        }

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

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }

        public Float[] getData() {
            return data;
        }

        public void setData(Float[] data) {
            this.data = data;
        }
    }

    private String[] legendData;

    private String[] xAxisData;

    public String[] getLegendData() {
        return legendData;
    }

    public void setLegendData(String[] legendData) {
        this.legendData = legendData;
    }

    public String[] getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(String[] xAxisData) {
        this.xAxisData = xAxisData;
    }

}
