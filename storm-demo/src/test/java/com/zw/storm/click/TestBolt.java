package com.zw.storm.click;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangws on 16/10/6.
 */
public class TestBolt extends BaseRichBolt {

    private static final long serialVersionUID = 1L;

    private static final transient Logger LOG = Logger.getLogger(TestBolt.class);

    private static Jedis jedis;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        jedis = new Jedis("localhost", Integer.parseInt(Constants.DEFAULT_JEDIS_PORT));
        jedis.connect();
    }

    @Override
    public void execute(Tuple input) {
        List<Object> objects = input.getValues();
        objects.add(0, input.getSourceComponent());
        jedis.rpush("TestTuple", JSONArray.toJSONString(objects));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
