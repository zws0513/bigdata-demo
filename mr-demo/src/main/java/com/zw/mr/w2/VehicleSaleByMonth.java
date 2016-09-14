package com.zw.mr.w2;

import com.zw.util.HdfsUtil;
import com.zw.util.NumericUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.2 统计山西省2013年每个月的汽车销售数量的比例
 * <p/>
 * Map:
 * 1. 以月为key，map(观察数据集, 全部数据都是2013年的, 所以不需要过滤年份)
 * <p/>
 * Reduce:
 * 1. 合计
 * 2. cleanup中计算最后的比例
 * <p/>
 * <pre><code>
 *   hadoop jar mr-demo.jar \
 *   com.zw.mr.w2.VehicleSaleByMonth \
 *   /hw/hdfs/w2/cars/autocar.txt \
 *   /hw/hdfs/mr/w2/output/vsm
 * </code></pre>
 * Created by zhangws on 16/8/12.
 */
public class VehicleSaleByMonth {

    private static final String NOTHING = "无";

    public static class VehicleSaleByMonthMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] strs = value.toString().split("\t");

            // 过滤非法数据
            if (strs.length == 39 && !NOTHING.equals(strs[1])) {
                // 月1
                context.write(new IntWritable(Integer.valueOf(strs[1].trim())), new IntWritable(1));
            }
        }
    }

    public static class VehicleSaleByMonthReducer extends Reducer<IntWritable, IntWritable, IntWritable, Text> {

        Map<IntWritable, Integer> map = new HashMap<>();
        long total = 0;

        @Override
        protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable v : values) {
                count += v.get();
            }
            map.put(new IntWritable(key.get()), count);
            total += count;
//            context.write(key, new IntWritable(count));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Map.Entry<IntWritable, Integer> entry : map.entrySet()) {
                context.write(entry.getKey(), new Text(NumericUtil.percent(entry.getValue(), total)));
            }
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

        Job job = Job.getInstance(conf, "VehicleSaleByMonth");
        job.setJarByClass(VehicleSaleByMonth.class);

        job.setMapperClass(VehicleSaleByMonthMapper.class);
        job.setReducerClass(VehicleSaleByMonthReducer.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

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
