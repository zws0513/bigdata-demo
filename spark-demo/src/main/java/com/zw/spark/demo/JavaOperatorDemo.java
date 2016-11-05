package com.zw.spark.demo;

import com.google.gson.Gson;
import org.apache.spark.HashPartitioner;
import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import scala.Tuple2;

import java.util.*;

/**
 * Created by zhangws on 16/10/30.
 */
public class JavaOperatorDemo {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

//        map();
//        filter();
//        flatMap();
//        mapPartitions();
//        mapPartitionsWithIndex();
//        sample();
//        union();
//        intersection(sc);
//        distinct(sc);
//        groupBy(sc);
//        reduceByKey(sc);
//        aggregateByKey(sc);
//        sortByKey(sc);
//        join(sc);
//        cogroup(sc);
//        pipe(sc);
//        coalesce(sc);
//        cartesian(sc);
        repartitionAndSortWithinPartitions(sc);

        sc.close();
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
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> names = Arrays.asList("张三1", "李四1", "王五1", "张三2", "李四2",
                "王五2", "张三3", "李四3", "王五3", "张三4");

        JavaRDD<String> namesRDD = sc.parallelize(names, 3);
        JavaRDD<String> mapPartitionsRDD = namesRDD.mapPartitions(
                new FlatMapFunction<Iterator<String>, String>() {
                    int count = 0;
                    @Override
                    public Iterator<String> call(Iterator<String> stringIterator) throws Exception {
                        List<String> list = new ArrayList<String>();
                        while (stringIterator.hasNext()) {
                            list.add("分区索引:" + count++ + "\t" + stringIterator.next());
                        }
                        return list.iterator();
                    }
                }
        );

        // 从集群获取数据到本地内存中
        List<String> result = mapPartitionsRDD.collect();
        result.forEach(System.out::println);

        sc.close();
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
        result.forEach(System.out::println);

        sc.close();
    }

    static void sample() {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> datas = Arrays.asList(1, 2, 3, 7, 4, 5, 8);

        JavaRDD<Integer> dataRDD = sc.parallelize(datas);
        JavaRDD<Integer> sampleRDD = dataRDD.sample(false, 0.5, System.currentTimeMillis());
        sampleRDD.foreach(v -> System.out.println(v));

        sc.close();
    }

    static void union() {
        SparkConf conf = new SparkConf().setAppName(JavaOperatorDemo.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> datas1 = Arrays.asList("张三", "李四");
        List<String> datas2 = Arrays.asList("tom", "gim");

        JavaRDD<String> data1RDD = sc.parallelize(datas1);
        JavaRDD<String> data2RDD = sc.parallelize(datas2);

        JavaRDD<String> unionRDD = data1RDD
                .union(data2RDD);

        unionRDD.foreach(v -> System.out.println(v));
    }

    static void intersection(JavaSparkContext sc) {
        List<String> datas1 = Arrays.asList("张三", "李四", "tom");
        List<String> datas2 = Arrays.asList("tom", "gim");

        sc.parallelize(datas1)
                .intersection(sc.parallelize(datas2))
                .foreach(v -> System.out.println(v));
    }

    static void distinct(JavaSparkContext sc) {
        List<String> datas = Arrays.asList("张三", "李四", "tom", "张三");

        sc.parallelize(datas)
                .distinct()
                .foreach(v -> System.out.println(v));
    }

    static void groupBy(JavaSparkContext sc) {
        List<Integer> datas = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        sc.parallelize(datas)
                .groupBy(new Function<Integer, Object>() {
                    @Override
                    public Object call(Integer v1) throws Exception {
                        return (v1 % 2 == 0) ? "偶数" : "奇数";
                    }
                })
                .collect()
                .forEach(System.out::println);


        List<String> datas2 = Arrays.asList("dog", "tiger", "lion", "cat", "spider", "eagle");

        sc.parallelize(datas2)
                .keyBy(v1 -> v1.length())
                .groupByKey()
                .collect()
                .forEach(System.out::println);
    }

    static void reduceByKey(JavaSparkContext sc) {
        JavaRDD<String> lines = sc.textFile("file:///Users/zhangws/opt/spark-2.0.1-bin-hadoop2.6/README.md");

        JavaRDD<String> wordsRDD = lines.flatMap(new FlatMapFunction<String, String>() {

            private static final long serialVersionUID = 1L;

            public Iterator<String> call(String line) throws Exception {
                List<String> words = Arrays.asList(line.split(" "));
                return words.iterator();
            }
        });

        JavaPairRDD<String, Integer> wordsCount = wordsRDD.mapToPair(new PairFunction<String, String, Integer>() {

            private static final long serialVersionUID = 1L;

            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        JavaPairRDD<String, Integer> resultRDD = wordsCount.reduceByKey(new Function2<Integer, Integer, Integer>() {

            private static final long serialVersionUID = 1L;

            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        resultRDD.foreach(new VoidFunction<Tuple2<String, Integer>>() {

            private static final long serialVersionUID = 1L;

            public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println(t._1 + "\t" + t._2());
            }
        });

        sc.close();
    }

    static void aggregateByKey(JavaSparkContext sc) {
        List<Tuple2<Integer, Integer>> datas = new ArrayList<>();
        datas.add(new Tuple2<>(1, 3));
        datas.add(new Tuple2<>(1, 2));
        datas.add(new Tuple2<>(1, 4));
        datas.add(new Tuple2<>(2, 3));

        sc.parallelizePairs(datas, 2)
                .aggregateByKey(
                        0,
                        new Function2<Integer, Integer, Integer>() {
                            @Override
                            public Integer call(Integer v1, Integer v2) throws Exception {
                                System.out.println("seq: " + v1 + "\t" + v2);
                                return Math.max(v1, v2);
                            }
                        },
                        new Function2<Integer, Integer, Integer>() {
                            @Override
                            public Integer call(Integer v1, Integer v2) throws Exception {
                                System.out.println("comb: " + v1 + "\t" + v2);
                                return v1 + v2;
                            }
                        })
                .collect()
                .forEach(System.out::println);
    }

    static void sortByKey(JavaSparkContext sc) {

        List<Integer> datas = Arrays.asList(60, 70, 80, 55, 45, 75);

//        sc.parallelize(datas)
//                .sortBy(new Function<Integer, Object>() {
//                    @Override
//                    public Object call(Integer v1) throws Exception {
//                        return v1;
//                    }
//                }, true, 1)
//                .foreach(v -> System.out.println(v));

        sc.parallelize(datas)
                .sortBy((Integer v1) -> v1, false, 1)
                .foreach(v -> System.out.println(v));

        List<Tuple2<Integer, Integer>> datas2 = new ArrayList<>();
        datas2.add(new Tuple2<>(3, 3));
        datas2.add(new Tuple2<>(2, 2));
        datas2.add(new Tuple2<>(1, 4));
        datas2.add(new Tuple2<>(2, 3));

        sc.parallelizePairs(datas2)
                .sortByKey(false)
                .foreach(v -> System.out.println(v));
    }

    static void join(JavaSparkContext sc) {
        List<Tuple2<Integer, String>> products = new ArrayList<>();
        products.add(new Tuple2<>(1, "苹果"));
        products.add(new Tuple2<>(2, "梨"));
        products.add(new Tuple2<>(3, "香蕉"));
        products.add(new Tuple2<>(4, "石榴"));

        List<Tuple2<Integer, Integer>> counts = new ArrayList<>();
        counts.add(new Tuple2<>(1, 7));
        counts.add(new Tuple2<>(2, 3));
        counts.add(new Tuple2<>(3, 8));
        counts.add(new Tuple2<>(4, 3));

        JavaPairRDD<Integer, String> productsRDD = sc.parallelizePairs(products);
        JavaPairRDD<Integer, Integer> countsRDD = sc.parallelizePairs(counts);

        productsRDD.join(countsRDD)
                .foreach(v -> System.out.println(v));
    }

    static void cogroup(JavaSparkContext sc) {
        List<Tuple2<Integer, String>> datas1 = new ArrayList<>();
        datas1.add(new Tuple2<>(1, "苹果"));
        datas1.add(new Tuple2<>(2, "梨"));
        datas1.add(new Tuple2<>(3, "香蕉"));
        datas1.add(new Tuple2<>(4, "石榴"));

        List<Tuple2<Integer, Integer>> datas2 = new ArrayList<>();
        datas2.add(new Tuple2<>(1, 7));
        datas2.add(new Tuple2<>(2, 3));
        datas2.add(new Tuple2<>(3, 8));
        datas2.add(new Tuple2<>(4, 3));


        List<Tuple2<Integer, String>> datas3 = new ArrayList<>();
        datas3.add(new Tuple2<>(1, "7"));
        datas3.add(new Tuple2<>(2, "3"));
        datas3.add(new Tuple2<>(3, "8"));
        datas3.add(new Tuple2<>(4, "3"));
        datas3.add(new Tuple2<>(4, "4"));
        datas3.add(new Tuple2<>(4, "5"));
        datas3.add(new Tuple2<>(4, "6"));

        sc.parallelizePairs(datas1)
                .cogroup(sc.parallelizePairs(datas2),
                        sc.parallelizePairs(datas3))
                .foreach(v -> System.out.println(v));
    }

    static void pipe(JavaSparkContext sc) {
        List<String> datas = Arrays.asList("hi", "hello", "how", "are", "you");
        sc.parallelize(datas)
                .pipe("/Users/zhangws/echo.sh")
                .collect()
                .forEach(System.out::println);
    }

    static void coalesce(JavaSparkContext sc) {
        List<String> datas = Arrays.asList("hi", "hello", "how", "are", "you");
        JavaRDD<String> datasRDD = sc.parallelize(datas, 4);
        System.out.println("RDD的分区数: " + datasRDD.partitions().size());
        JavaRDD<String> datasRDD2 = datasRDD.coalesce(2);
        System.out.println("RDD的分区数: " + datasRDD2.partitions().size());
    }

    /**
     * 笛卡尔积算子
     */
    private static void cartesian(JavaSparkContext sc) {
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
    }

    static void repartitionAndSortWithinPartitions(JavaSparkContext sc) {
        List<String> datas = new ArrayList<>();
        Random random = new Random(1);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                datas.add(String.format("product%02d,url%03d", random.nextInt(10), random.nextInt(100)));
            }
        }
        JavaRDD<String> datasRDD = sc.parallelize(datas);
        JavaPairRDD<String, String> pairRDD = datasRDD.mapToPair((String v) -> {
            String[] values = v.split(",");
            return new Tuple2<>(values[0], values[1]);
        });
        JavaPairRDD<String, String> partSortRDD = pairRDD.repartitionAndSortWithinPartitions(
                new Partitioner() {

                    @Override
                    public int numPartitions() {
                        return 10;
                    }

                    @Override
                    public int getPartition(Object key) {
                        return Integer.valueOf(((String) key).substring(7));
                    }
                }
        );
        partSortRDD.collect()
        .forEach(System.out::println);
    }

}
