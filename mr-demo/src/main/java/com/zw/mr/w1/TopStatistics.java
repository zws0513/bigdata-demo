package com.zw.mr.w1;

import com.zw.util.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * 统计排名前 3 的省份共同拥有的农产品类型
 * <p>
 * <p>
 * <p>
 * 流程:
 * 1. 利用TopSortStatistics输出的结果, 读取前三省名;
 * 2. 读取所有数据, 过滤非前三省的记录;
 * 3. 以品种为key, 省名为value, 结束map;
 * 4. reduce中, value中有三个省的, 输出
 * <p>
 * 残留问题:
 * 未按老师提示使用cleanup
 * <p>
 * 运行:
 * hadoop jar hadoop-demo-1.0-SNAPSHOT.jar com.zw.mr.weekone.TopStatistics /w1/ts/output/part-r-00000 /w1/m/input /w1/t/output
 * </p>
 * <p>
 * Created by zhangws on 16/8/4.
 */
public class TopStatistics {
    public static class TopMapper extends Mapper<LongWritable, Text, Text, Text> {

        private String top3OfProvince;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            BufferedReader br = null;

            //获得当前作业的DistributedCache相关文件
            URI[] paths = context.getCacheFiles();
//            Path[] distributePaths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
//            if (distributePaths == null || distributePaths.length == 0) {
//                return;
//            }
            String cityInfo = null;
//            for (Path p : distributePaths) {
//                if (p.toString().endsWith("part-r-00000")) {
            //读缓存文件，并放到mem中
            try {
                br = new BufferedReader(new FileReader(paths[0].toString()));
                int lines = 0;
                while (lines < 3 && null != (cityInfo = br.readLine())) {
                    String[] cityPart = cityInfo.split("\t");
                    top3OfProvince = top3OfProvince + cityPart[0].substring(0, 2) + ",";
                    lines++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null)
                    br.close();
            }
//                }
//            }
        }

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] strings = value.toString().split("\t");

            // 前三的省份进入下一步计算
            if (strings.length == 6 && top3OfProvince.contains(strings[4].substring(0, 2))) {
                // key: 品种名; value: 省份
                context.write(new Text(strings[0]), new Text(strings[4]));
            }
        }
    }

    public static class TopReducer extends Reducer<Text, Text, Text, NullWritable> {
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

            Set<String> provinces = new HashSet<>();
            for (Text v : values) {
                provinces.add(v.toString());
            }
            //输出key
            if (provinces.size() == 3) {
                context.write(key, NullWritable.get());
            }
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
        if (otherArgs.length < 3) {
            System.err.println("Usage: <in> <in> <out>");
            System.exit(2);
        }

        //先删除output目录
        HdfsUtil.rmr(conf, otherArgs[otherArgs.length - 1]);

        Job job = Job.getInstance(conf, "TopStatistics");
        job.setJarByClass(TopStatistics.class);

        URI[] paths = new URI[1];
        paths[0] = new Path(args[0]).toUri();
        job.setCacheFiles(paths);
//        DistributedCache.addCacheFile(new Path(args[0]).toUri(), conf);//为该job添加缓存文件

        job.setMapperClass(TopMapper.class);
        //job.setCombinerClass(TopReducer.class); // 由于参数类型不一致, 而使用默认的combiner
        job.setReducerClass(TopReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        String input_path = HdfsUtil.ls(conf, otherArgs[1]);
        if (input_path.contains(",")) {
            FileInputFormat.setInputPaths(job, input_path);
        } else {
            FileInputFormat.addInputPath(job, new Path(input_path));
        }
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

        if (job.waitForCompletion(true)) {
            HdfsUtil.cat(conf, otherArgs[1] + "/part-r-00000");
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }
}
