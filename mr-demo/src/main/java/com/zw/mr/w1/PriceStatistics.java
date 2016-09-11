package com.zw.mr.w1;

import com.zw.util.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * 计算山西省的每种农产品的价格波动趋势,即计算每天价格均值
 *
 * 流程:
 *   map:
 *     1. 以1-5号的数据为数据;
 *     2. 过滤非目标数据;
 *     3. 以品种+日期为key, 价格为value作为输出参数
 *
 *   reduce:
 *     1. 计算均价
 *        同一天品种价格样本大于2, 采用 (sum - max - min) / N - 2 计算
 *        否则, 采用(sum / N) 计算
 *     2. 在cleanup中, 格式化数据(未完成)
 *
 * 残留问题:
 *     未进行数据格式化
 *
 * 运行:
 *   hadoop jar com.zw.mr.weekone.PriceStatistics /w1/m/input /w1/pr/output
 *
 * Created by zhangws on 16/8/4.
 */
public class PriceStatistics {
    public static class PriceMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            InputSplit inputSplit = context.getInputSplit();
            String fileName = ((FileSplit) inputSplit).getPath().toString();

            String[] strings = value.toString().split("\t");

            // 过滤非法数据; 过滤非山西数据
            // key: 品种; value: 价格
            if (strings.length == 6 &&  strings[4].equals("山西")) {
                context.write(new Text(strings[0]
                        + fileName.substring(fileName.length() - 5, fileName.length() - 4)),
                        new FloatWritable(Float.valueOf(strings[1])));
            }
        }
    }

    public static class PriceReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
        public void reduce(Text key, Iterable<FloatWritable> values, Context context)
                throws IOException, InterruptedException {

            float sum = 0.0f;
            float max = 0.0f;
            float min = 0.0f;
            int count = 0;
//            if (key.toString().contains("人参果")) {
//                System.out.println("test");
//            }
            for (FloatWritable v : values) {
                sum = sum + v.get();
                max = Math.max(max, v.get());
                min = Math.min(min, v.get());
                count++;
            }
            float avg;
            if (count > 2) {
                avg = (sum - max - min) / (count - 2);
            } else {
                avg = sum / count;
            }
            //输出key
            context.write(key, new FloatWritable(avg));
        }

        /**
         * Called once at the end of the task.
         *
         * @param context
         */
        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
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

        Job job = Job.getInstance(conf, "PriceStatistics");
        job.setJarByClass(PriceStatistics.class);

        job.setMapperClass(PriceMapper.class);
        //job.setCombinerClass(PriceReducer.class); // 由于参数类型不一致, 而使用默认的combiner
        job.setReducerClass(PriceReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FloatWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);

        String input_path = HdfsUtil.ls(conf, otherArgs[0]);
        if (input_path.contains(",")) {
            FileInputFormat.setInputPaths(job, input_path);
        } else {
            FileInputFormat.addInputPath(job, new Path(input_path));
        }
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
