package com.zw.sparkstreaming.constant

/**
  * Created by zhangws on 16/11/13.
  */
object Constants {
    val REDIS_SERVER: String = "hss01"
    val REDIS_PORT: Int = 6379

    val KAFKA_SERVER: String = "hsm01:9092,hss01:9092,hss02:9092"
//    val KAFKA_ADDR: String = KAFKA_SERVER + ":9092"
    val KAFKA_USER_TOPIC: String = "user_events"
    val KAFKA_RECO_TOPIC: String = "reco6"
}
