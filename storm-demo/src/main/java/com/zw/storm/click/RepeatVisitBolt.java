package com.zw.storm.click;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 比较客户端ID与之前访问记录是否一致, 以此判断用户是否第一次访问,
 * 然后发送处理后包含这个标记的Tuple.
 * <p>
 * Created by zhangws on 16/10/6.
 */
public class RepeatVisitBolt extends BaseRichBolt {

    private OutputCollector collector;

    private Jedis jedis;
    private String host;
    private int port;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        host = stormConf.get(Constants.REDIS_HOST_KEY).toString();
        port = Integer.valueOf(stormConf.get(Constants.REDIS_PORT_KEY).toString());

        connectToRedis();
    }

    private void connectToRedis() {
        jedis = new Jedis(host, port);
        jedis.connect();
    }

    public boolean isConnected() {
        return jedis != null && jedis.isConnected();
    }

    @Override
    public void execute(Tuple input) {
        String ip = input.getStringByField(Constants.IP);
        String clientKey = input.getStringByField(Constants.CLIENT_KEY);
        String url = input.getStringByField(Constants.URL);
        String key = url + ":" + clientKey;
        String value = jedis.get(key);
        if (value == null) {
            jedis.set(key, "visited");
            collector.emit(new Values(clientKey, url, Boolean.TRUE.toString()));
        } else {
            collector.emit(new Values(clientKey, url, Boolean.FALSE.toString()));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Constants.CLIENT_KEY,
                Constants.URL,
                Constants.UNIQUE));
    }
}
