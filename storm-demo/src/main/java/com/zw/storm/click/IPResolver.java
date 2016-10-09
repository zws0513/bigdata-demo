package com.zw.storm.click;

import org.json.simple.JSONObject;

/**
 * Created by zhangws on 16/10/6.
 */
public interface IPResolver {

    JSONObject resolveIP(String ip);
}
