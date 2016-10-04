package com.zw.kafka.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import storm.kafka.bolt.KafkaBolt;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置kafka提交topology到storm的代码
 * <p>
 *     topic1的含义kafka接收生产者过来的数据所需要的topic;
 *     topic2是KafkaBolt也就是storm中的bolt生成的topic，当然这里topic2这行配置可以省略，
 *     是没有任何问题的，类似于一个中转的东西
 * </p>
 * Created by zhangws on 16/10/2.
 */
public class KafkaTopology {

    public static void main(String[] args) {
        BrokerHosts brokerHosts = new ZkHosts("192.168.99.145:2181/kafka");

        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, "topic1", "/kafka", "kafkaspout");

        Config conf = new Config();
        Map<String, String> map = new HashMap<String, String>();

        map.put("metadata.broker.list", "192.168.99.145:9092");
        map.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put("kafka.broker.properties", map);
        conf.put("topic", "topic2");

        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig));
        builder.setBolt("bolt", new SenqueceBolt()).shuffleGrouping("spout");
        builder.setBolt("kafkabolt", new KafkaBolt<String, Integer>()).shuffleGrouping("bolt");

        if(args != null && args.length > 0) {
            //提交到集群运行
            try {
                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            }
        } else {
            //本地模式运行
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Topotest1121", conf, builder.createTopology());
            Utils.sleep(1000000);
            cluster.killTopology("Topotest1121");
            cluster.shutdown();
        }
    }
}
