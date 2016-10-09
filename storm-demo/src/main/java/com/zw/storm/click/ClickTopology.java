package com.zw.storm.click;

import backtype.storm.Config;
import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.NotAliveException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

/**
 * Created by zhangws on 16/10/6.
 */
public class ClickTopology {

    TopologyBuilder builder = new TopologyBuilder();

    Config conf = new Config();

    ILocalCluster cluster;

    public ClickTopology() {
        builder.setSpout("clickSpout", new ClickSpout(), 10);

        builder.setBolt("repeatBolt", new RepeatVisitBolt(), 10).shuffleGrouping("clickSpout");
        builder.setBolt("geographyBolt", new GeographyBolt(new HttpIPResolver()), 10)
                .shuffleGrouping("clickSpout");

        builder.setBolt("totalStats", new VisitStatsBolt(), 1).globalGrouping("repeatBolt");
        builder.setBolt("geoStats", new GeoStatsBolt(), 10).fieldsGrouping("geographyBolt",
                new Fields(Constants.COUNTRY));

        conf.put(Constants.REDIS_PORT_KEY, Constants.DEFAULT_JEDIS_PORT);
    }

    public void runLocal(int runTime) throws AlreadyAliveException,
            InvalidTopologyException, NotAliveException {
        conf.setDebug(true);
        conf.put(Constants.REDIS_HOST_KEY, "localhost");
        cluster = new LocalCluster();
        cluster.submitTopology(ClickTopology.class.getSimpleName(), conf, builder.createTopology());

        if (runTime > 0) {
            Utils.sleep(runTime);
            shutDownLocal();
        }
    }

    public void shutDownLocal() throws NotAliveException {
        if (cluster != null) {
            cluster.killTopology(ClickTopology.class.getSimpleName());
            cluster.shutdown();
        }
    }

    public void runCluster(String name, String redisHost)
            throws AlreadyAliveException, InvalidTopologyException {
        conf.setNumWorkers(1);
        conf.put(Constants.REDIS_HOST_KEY, redisHost);
        StormSubmitter.submitTopology(name, conf, builder.createTopology());
    }

    public static void main(String[] args) throws Exception {
        ClickTopology topology = new ClickTopology();

        if (args != null && args.length > 1) {
            topology.runCluster(args[0], args[1]);
        } else {
            if (args != null && args.length == 1) {
                System.out.println("Running in local mode, redis ip missing for cluster run");
                topology.runLocal(10000);
            }
        }
    }
}
