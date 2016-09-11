package com.zw.hive.w4;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by zhangws on 16/8/27.
 */
public class HelloWorldUDF extends UDF {

    public Long evaluate(long a, long b) {
        return a + b;
    }
}
