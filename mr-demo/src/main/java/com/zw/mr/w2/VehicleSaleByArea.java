package com.zw.mr.w2;

import com.zw.util.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 *
 * 1.3 统计山西省2013年各市、区县的汽车销售的分布
 *
 * <p>
 *     map:
 *       1. 以市 区县为key, map计数
 *
 *     reduce:
 *       1. 合计输出
 * </p>
 * <pre><code>
 *   hadoop jar mr-demo.jar \
 *   com.zw.mr.w2.VehicleSaleByArea \
 *   /hw/hdfs/w2/cars/autocar.txt \
 *   /hw/hdfs/mr/w2/output/vsa
 * </code></pre>
 *
 * Created by zhangws on 16/8/12.
 */
public class VehicleSaleByArea {

    private static final String NOTHING = "无";

    public static class VehicleSaleByAreaMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] strs = value.toString().split("\t");

            // 过滤非法数据
            if (strs.length == 39 && !NOTHING.equals(strs[2]) && !NOTHING.equals(strs[3])) {
                // 月1
                context.write(new Text(strs[2].trim() + "\t" + strs[3].trim()), new IntWritable(1));
            }
        }
    }

    public static class VehicleSaleByAreaReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable v : values) {
                count += v.get();
            }
            context.write(key, new IntWritable(count));
        }

    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: <in> [<in>...] <out>");
            System.exit(2);
        }

        //先删除output目录
        HdfsUtil.rmr(conf, otherArgs[otherArgs.length - 1]);

        Job job = Job.getInstance(conf, "VehicleSaleByArea");
        job.setJarByClass(VehicleSaleByArea.class);

        job.setMapperClass(VehicleSaleByAreaMapper.class);
        job.setReducerClass(VehicleSaleByAreaReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        if (job.waitForCompletion(true)) {
            HdfsUtil.cat(conf, otherArgs[1] + "/part-r-00000");
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }
}
