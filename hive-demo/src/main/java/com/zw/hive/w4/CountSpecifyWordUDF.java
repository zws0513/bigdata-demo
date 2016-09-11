package com.zw.hive.w4;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 *
 * 计算src中包含word的个数
 *
 * Created by zhangws on 16/8/27.
 */
public class CountSpecifyWordUDF extends UDF {

    /**
     * 计算src中包含word的个数
     * @param src src
     * @param word word
     * @return counter
     */
    public int evaluate(String src, String word) {
        try {
            int counter=0;
            if (!src.contains(word)) {
                return 0;
            }
            int pos;
            while((pos = src.indexOf(word)) != -1){
                counter++;
                src = src.substring(pos + word.length());
            }
            return counter;
        } catch (Exception e) {
            return 0;
        }
    }

}
