package com.zw.storm.click;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * 通过远程API调用查询客户IP地址所对应的国家和城市.
 * <p>
 * Created by zhangws on 16/10/6.
 */
public class GeographyBolt extends BaseRichBolt {

    private OutputCollector collector;
    private IPResolver resolver;

    public GeographyBolt(IPResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String ip = input.getStringByField(Constants.IP);
        JSONObject json = resolver.resolveIP(ip);
        String city = (String) json.get(Constants.CITY);
        String country = (String) json.get(Constants.COUNTRY_NAME);
        collector.emit(new Values(country, city));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Constants.COUNTRY, Constants.CITY));
    }
}
