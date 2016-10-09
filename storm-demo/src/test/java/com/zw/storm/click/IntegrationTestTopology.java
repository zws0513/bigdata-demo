package com.zw.storm.click;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhangws on 16/10/6.
 */
@RunWith(value = Parameterized.class)
public class IntegrationTestTopology {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{new Object[]{"165.228.250.178", "internal.com", "Client1"}, //input
                new Object[]{"AUSTRALIA", new Long(1), "SYDNEY", new Long(1), new Long(1), new Long(1)}},//expectations
                {new Object[]{"165.228.250.178", "internal.com", "Client1"}, //input
                        new Object[]{"AUSTRALIA", new Long(2), "SYDNEY", new Long(2), new Long(2), new Long(1)}},
                {new Object[]{"4.17.136.0", "internal.com", "Client1"}, //input, same client, different location
                        new Object[]{"UNITED STATES", new Long(1), "DERRY, NH", new Long(1), new Long(3), new Long(1)}},
                {new Object[]{"4.17.136.0", "internal.com", "Client2"}, //input, same client, different location
                        new Object[]{"UNITED STATES", new Long(2), "DERRY, NH", new Long(2), new Long(4), new Long(2)}}};//expectations
        return Arrays.asList(data);
    }

    private static Jedis jedis;
    private static ClickTopology topology = new ClickTopology();
    private static TestBolt testBolt = new TestBolt();
}
