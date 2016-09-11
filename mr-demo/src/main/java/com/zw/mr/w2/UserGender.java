package com.zw.mr.w2;

import com.zw.util.HdfsUtil;
import com.zw.util.NumericUtil;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 2.1 统计买车的男女比例
 * <p/>
 * <p>
 * Map:
 *   1. 以性别为key，map
 * Reduce:
 *   1. 合计
 *   2. cleanup中计算最后的比例
 * </p>
 * <p/>
 * Created by zhangws on 16/8/12.
 */
public class UserGender {

    private static final String NOTHING = "无";

    public static class UserGenderMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] strs = value.toString().split("\t");

            // 过滤非法数据
            if (strs.length == 39 && !NOTHING.equals(strs[38])) {
                // 月1
                context.write(new Text(strs[38].trim()), new IntWritable(1));
            }
        }
    }

    public static class UserGenderReducer extends Reducer<Text, IntWritable, Text, Text> {

        Map<Text, Integer> map = new HashMap<>();
        long total = 0;

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable v : values) {
                count += v.get();
            }
            map.put(new Text(key), count);
            total += count;
//            context.write(key, new IntWritable(count));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Map.Entry<Text, Integer> entry : map.entrySet()) {
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

        Job job = Job.getInstance(conf, "UserGender");
        job.setJarByClass(UserGender.class);

        job.setMapperClass(UserGenderMapper.class);
        job.setReducerClass(UserGenderReducer.class);

        job.setMapOutputKeyClass(Text.class);
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
