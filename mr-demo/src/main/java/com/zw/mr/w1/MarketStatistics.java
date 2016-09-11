package com.zw.mr.w1;

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
import java.util.HashSet;
import java.util.Set;

/**
 * 统计每个省份的农产品市场总数
 * <p>
 * 输入参数:
 * param1: 指定输入目录(遍历目录下所有文件,不含子目录)
 * param2: 计算结果输出目录
 * 例:
 * <pre><code>
 *   hadoop jar mr-demo.jar \
 *   com.zw.mr.w1.MarketStatistics \
 *   /hw/hdfs/w1/products \
 *   /hw/hdfs/mr/w1/output/ms
 *   </code></pre>
 * <p>
 * Created by zhangws on 16/8/4.
 */
public class MarketStatistics {

    public static class MarketMapper extends Mapper<LongWritable, Text, Text, Text> {

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] strings = value.toString().split("\t");

            //将文本行放入key
            if (strings.length == 6) {
                context.write(new Text(strings[4]), new Text(strings[3]));
            }
        }
    }

    public static class MarketReducer extends Reducer<Text, Text, Text, IntWritable> {
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

            Set<String> market = new HashSet<>();
            for (Text v : values) {
                market.add(v.toString());
            }
            //输出key
            context.write(key, new IntWritable(market.size()));
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

        Job job = Job.getInstance(conf, "MarketStatistics");
        job.setJarByClass(MarketStatistics.class);

        job.setMapperClass(MarketMapper.class);
        job.setReducerClass(MarketReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        String input_path = HdfsUtil.ls(conf, otherArgs[0]);
        if (input_path.contains(",")) {
            FileInputFormat.setInputPaths(job, input_path);
        } else {
            FileInputFormat.addInputPath(job, new Path(input_path));
        }
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        if (job.waitForCompletion(true)) {
            HdfsUtil.cat(conf, otherArgs[1] + "/part-r-00000");
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }
}
