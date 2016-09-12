package com.zw.mr.w1;

import com.zw.mr.w1.dto.IntWritableCompare;
import com.zw.util.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * 根据农产品数量排序省份
 * <p>
 * 利用KindStatistics的输出结果(统计每个省农产品种类总数)
 * </p>
 * <pre>
 * <code>
 * hadoop jar mr-demo-1.0-SNAPSHOT.jar \
 * com.zw.mr.w1.TopSortStatistics \
 * /hw/hdfs/mr/w1/output/ks/part-r-00000 \
 * /hw/hdfs/mr/w1/output/tss
 * </code>
 * </pre>
 * <p>
 * Created by zhangws on 16/8/4.
 */
public class TopSortStatistics {
    public static class SortMapper extends Mapper<LongWritable, Text, IntWritableCompare, Text> {

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] strings = value.toString().split("\t");

            //将文本行放入key
            if (strings.length == 2) {
                context.write(new IntWritableCompare(Integer.valueOf(strings[1])), new Text(strings[0]));
            }
        }
    }

    public static class SortReducer extends Reducer<IntWritableCompare, Text, Text, IntWritable> {
        public void reduce(IntWritableCompare key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

            //输出key
            context.write(values.iterator().next(), new IntWritable(key.get()));
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

        Job job = Job.getInstance(conf, "TopSortStatistics");
        job.setJarByClass(TopSortStatistics.class);

        job.setMapperClass(SortMapper.class);
        job.setReducerClass(SortReducer.class);

        job.setMapOutputKeyClass(IntWritableCompare.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

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
