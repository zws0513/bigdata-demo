package com.zw.mr.demo;

import com.zw.util.HdfsUtil;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * Created by zhangws on 16/8/14.
 */
public class Support {

    public static void main(String[] args) {
//        mkdir();
        //get();
        float   a  =   123.2334f;
        float  b   =  (float)(Math.round(a*100))/100;
        System.out.println(b);
    }

    private static void mkdir() {
        String remotePath = "hdfs://master:9000/w2/misb/input";

        try {
            HdfsUtil.mkdir(new Configuration(), remotePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void get() {
        String localPath = "/Users/zhangws/Documents/doc/02.study/01.大数据开发实战/week2/answer/3.2";
        String remotePath = "hdfs://master:9000/w2/misb/output/part-r-00000";

        try {
            HdfsUtil.get(new Configuration(), remotePath, localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
