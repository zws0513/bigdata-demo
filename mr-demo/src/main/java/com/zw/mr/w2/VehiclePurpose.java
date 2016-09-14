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
 * 1.1 统计车辆不同用途的数量分布
 *
 * Map:
 *   1. 以<code>使用性质 + "\t" + 市</code>为key, 数值"1"为value输出;
 *
 * Reduce:
 *   1. 统计values的总数, 并输出
 *
 * <pre><code>
 *   hadoop jar mr-demo.jar \
 *   com.zw.mr.w2.VehiclePurpose \
 *   /hw/hdfs/w2/cars/autocar.txt \
 *   /hw/hdfs/mr/w2/output/vp
 * </code></pre>
 * <p>
 *
 * Created by zhangws on 16/8/12.
 */
public class VehiclePurpose {

    private static final String NOTHING = "无";

    public static class VehiclePurposeMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            String[] strs = value.toString().split("\t");

            // 过滤非法数据
            if (strs.length == 39 && !NOTHING.equals(strs[10]) && !NOTHING.equals(strs[2])) {
                // 使用性质10 市2
                context.write(new Text(strs[10] + "\t" + strs[2]), new IntWritable(1));
            }
        }
    }

    public static class VehiclePurposeReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

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

        Job job = Job.getInstance(conf, "VehiclePurpose");
        job.setJarByClass(VehiclePurpose.class);

        job.setMapperClass(VehiclePurposeMapper.class);
        job.setReducerClass(VehiclePurposeReducer.class);

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
