package com.zw.spark.demo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhangws on 16/10/30.
 */
public class JavaWordCount {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName(JavaWordCount.class.getSimpleName())
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

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
}
