package com.zw.storm.click;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * 在内存中维护了访客与独立访客的数量, 它需要接收所有的计数信息并进行统计.
 * <p>
 * Created by zhangws on 16/10/6.
 */
public class VisitStatsBolt extends BaseRichBolt {

    private OutputCollector collector;
    private int total = 0;
    private int uniqueCount = 0;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        boolean unique = Boolean.parseBoolean(input.getStringByField(Constants.UNIQUE));
        total++;
        if (unique) {
            uniqueCount++;
        }
        collector.emit(new Values(total, uniqueCount));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Constants.TOTAL_COUNT,
                Constants.TOTAL_UNIQUE));
    }
}
