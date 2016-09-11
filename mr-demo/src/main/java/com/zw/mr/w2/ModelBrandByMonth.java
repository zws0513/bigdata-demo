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
 * 3.1 统计不同品牌的车在每个月的销售量分布
 * <p>
 *     map:
 *       1. 以品牌 月份为key, map
 *
 *     reduce:
 *       1. 合计输出
 * </p>
 *
 * Created by zhangws on 16/8/12.
 */
public class ModelBrandByMonth {

    private static final String NOTHING = "无";

    public static class ModelBrandByMonthMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] strs = value.toString().split("\t");

            // 过滤非法数据
            if (strs.length == 39
                    && !NOTHING.equals(strs[7])
                    && !NOTHING.equals(strs[1])) {
                String temp = String.format("%s\t%02d", strs[7].trim(), Integer.valueOf(strs[1].trim()));
                context.write(new Text(temp),
                        new IntWritable(1));
            }
        }
    }

    public static class ModelBrandByMonthReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

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

        Job job = Job.getInstance(conf, "ModelBrandByMonth");
        job.setJarByClass(ModelBrandByMonth.class);

        job.setMapperClass(ModelBrandByMonthMapper.class);
        job.setReducerClass(ModelBrandByMonthReducer.class);

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
