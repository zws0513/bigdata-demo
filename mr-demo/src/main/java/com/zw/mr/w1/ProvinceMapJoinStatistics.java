package com.zw.mr.w1;

import com.zw.util.HdfsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

/**
 * 统计没有农产品市场的省份有哪些
 * <p>
 * Map侧实现连接
 * <p>
 * 在Map的setup中读取拥有农产品市场的省份;
 * 在map函数中,读取所有省份,然后过滤掉含有农产品的省
 * <p>
 * 输入参数:
 * param1: 含全部省份的文件
 * param2: 含农产品的省份(MarketStatistics的输出)
 * param3: 输出目录
 * <p>
 * <pre>
 * <code>
 * hadoop jar mr-demo.jar \
 * com.zw.mr.w1.ProvinceMapJoinStatistics \
 * /hw/hdfs/w1/province/province.txt \
 * /hw/hdfs/mr/w1/output/ms/part-r-00000 \
 * /hw/hdfs/mr/w1/output/pmjs
 * </code>
 * </pre>
 * <p>
 * Created by zhangws on 16/8/4.
 */
public class ProvinceMapJoinStatistics {
    public static class ProvinceLeftJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

        private static Log log = LogFactory.getLog(ProvinceLeftJoinMapper.class);

        private String provinceWithProduct = "";

        /**
         * 加载缓存文件
         * <p>
         * 加载拥有农产品的省份
         * </p>
         *
         * @param context 上下文
         *
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            BufferedReader br = null;

            URI[] uri = context.getCacheFiles();
            if (uri == null || uri.length == 0) {
                return;
            }
            String cityInfo = null;
            for (URI p : uri) {
                if (p.toString().endsWith("part-r-00000")) {
                    //读缓存文件，并放到mem中
                    try {
                        provinceWithProduct = HdfsUtil.read(new Configuration(), p.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            //将文本行放入key
            if (!provinceWithProduct.contains(value.toString()
                    .substring(0, 2))) {
                context.write(value, NullWritable.get());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 3) {
            System.err.println("Usage: <in> [<in>...] <out>");
            System.exit(2);
        }

        //先删除output目录
        HdfsUtil.rmr(conf, otherArgs[otherArgs.length - 1]);

        Job job = Job.getInstance(conf, "ProvinceMapJoinStatistics");
        job.setJarByClass(ProvinceMapJoinStatistics.class);

        job.addCacheFile(new Path(args[1]).toUri());

        job.setMapperClass(ProvinceLeftJoinMapper.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

        if (job.waitForCompletion(true)) {
            HdfsUtil.cat(conf, otherArgs[2] + "/part-r-00000");
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }
}
