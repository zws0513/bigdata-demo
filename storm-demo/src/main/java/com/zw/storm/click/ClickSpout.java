package com.zw.storm.click;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by zhangws on 16/10/6.
 */
public class ClickSpout extends BaseRichSpout {

    public static Logger log = Logger.getLogger(ClickSpout.class);
    private Jedis jedis;
    private String host;
    private int port;
    private SpoutOutputCollector collector;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Constants.IP, Constants.URL, Constants.CLIENT_KEY));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        host = conf.get(Constants.REDIS_HOST_KEY).toString();
        port = Integer.valueOf(conf.get(Constants.REDIS_PORT_KEY).toString());
        this.collector = collector;
        connectToRedis();
    }

    private void connectToRedis() {
        jedis = new Jedis(host, port);
        jedis.connect();
    }

    @Override
    public void nextTuple() {
        String content = jedis.rpop("count");
        if (content == null || "nil".equals(content)) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // nothing
            }
        } else {
            JSONObject obj = (JSONObject) JSONValue.parse(content);
            String ip = obj.get(Constants.IP).toString();
            String url = obj.get(Constants.URL).toString();
            String clientKey = obj.get(Constants.CLIENT_KEY).toString();
            collector.emit(new Values(ip, url, clientKey));
        }
    }
}
