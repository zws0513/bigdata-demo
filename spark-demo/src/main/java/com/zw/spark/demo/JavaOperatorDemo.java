package com.zw.spark.demo;

import com.google.gson.Gson;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhangws on 16/10/30.
 */
public class JavaOperatorDemo {

    public static void main(String[] args) {
//        map();
//        filter();
        flatMap();
//        cartesian();
//        mapPartitionsWithIndex();
    }

    public static class User {
        public int id;
        public String name;
        public String pwd;
        public int sex;
    }

    /**
     * map算子
     * <p>
     * map和foreach算子:
     * 1. 循环map调用元的每一个元素;
     * 2. 执行call函数, 并返回.
     * </p>
     */
    private static void map() {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> datas = Arrays.asList(
                "{'id':1,'name':'xl1','pwd':'xl123','sex':2}",
                "{'id':2,'name':'xl2','pwd':'xl123','sex':1}",
                "{'id':3,'name':'xl3','pwd':'xl123','sex':2}");

        JavaRDD<String> datasRDD = sc.parallelize(datas);

        JavaRDD<User> mapRDD = datasRDD.map(
                new Function<String, User>() {
                    public User call(String v) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(v, User.class);
                    }
                });

        mapRDD.foreach(new VoidFunction<User>() {
            public void call(User user) throws Exception {
                System.out.println("id: " + user.id
                        + " name: " + user.name
                        + " pwd: " + user.pwd
                        + " sex:" + user.sex);
            }
        });

        sc.close();
    }

    static void filter() {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> datas = Arrays.asList(1, 2, 3, 7, 4, 5, 8);

        JavaRDD<Integer> rddData = sc.parallelize(datas);
        JavaRDD<Integer> filterRDD = rddData.filter(
                v1 -> v1 >= 3
//                new Function<Integer, Boolean>() {
//                    public Boolean call(Integer v) throws Exception {
//                        return v >= 3;
//                    }
//                }
        );
        filterRDD.foreach(
                v -> System.out.println(v)
//                new VoidFunction<Integer>() {
//                    @Override
//                    public void call(Integer integer) throws Exception {
//                        System.out.println(integer);
//                    }
//                }
        );
        sc.close();
    }

    static void flatMap() {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> data = Arrays.asList(
                "aa,bb,cc",
                "cxf,spring,struts2",
                "java,C++,javaScript");
        JavaRDD<String> rddData = sc.parallelize(data);
        JavaRDD<String> flatMapData = rddData.flatMap(
                v -> Arrays.asList(v.split(",")).iterator()
//                new FlatMapFunction<String, String>() {
//                    @Override
//                    public Iterator<String> call(String t) throws Exception {
//                        List<String> list= Arrays.asList(t.split(","));
//                        return list.iterator();
//                    }
//                }
        );
        flatMapData.foreach(v -> System.out.println(v));

        sc.close();
    }

    static void mapPartitions() {

    }

    /**
     * mapPartitionsWithIndex算子
     */
    private static void mapPartitionsWithIndex() {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> names = Arrays.asList("张三1", "李四1", "王五1", "张三2", "李四2",
                "王五2", "张三3", "李四3", "王五3", "张三4");

        // 初始化，分为3个分区
        JavaRDD<String> namesRDD = sc.parallelize(names, 3);
        JavaRDD<String> mapPartitionsWithIndexRDD = namesRDD.mapPartitionsWithIndex(
                new Function2<Integer, Iterator<String>, Iterator<String>>() {

                    private static final long serialVersionUID = 1L;

                    public Iterator<String> call(Integer v1, Iterator<String> v2) throws Exception {
                        List<String> list = new ArrayList<String>();
                        while (v2.hasNext()) {
                            list.add("分区索引:" + v1 + "\t" + v2.next());
                        }
                        return list.iterator();
                    }
                },
                true);

        // 从集群获取数据到本地内存中
        List<String> result = mapPartitionsWithIndexRDD.collect();

        for (String v : result) {
            System.out.println(v);
        }

        sc.close();
    }

    /**
     * 笛卡尔积算子
     */
    private static void cartesian() {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> names = Arrays.asList("张三", "李四", "王五");
        List<Integer> scores = Arrays.asList(60, 70, 80);

        JavaRDD<String> namesRDD = sc.parallelize(names);
        JavaRDD<Integer> scoreRDD = sc.parallelize(scores);

        JavaPairRDD<String, Integer> cartesianRDD = namesRDD.cartesian(scoreRDD);
        cartesianRDD.foreach(new VoidFunction<Tuple2<String, Integer>>() {

            private static final long serialVersionUID = 1L;

            public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println(t._1 + "\t" + t._2());
            }
        });

        sc.close();
    }

}
