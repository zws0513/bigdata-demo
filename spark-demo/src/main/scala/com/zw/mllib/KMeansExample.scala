// scalastyle:off println
package com.zw.mllib

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
//import org.apache.spark.ml.clustering.KMeansModel
import org.apache.spark.mllib.clustering.{KMeansModel, KMeans}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

/**
  * An example k-means app. Run with
  * {{{
  * ./bin/run-example org.apache.spark.examples.mllib.DenseKMeans [options] <input>
  * }}}
  * If you use it as a template to create your own app, please use `spark-submit` to submit your app.
  */
object KMeansExample {

    def main(args: Array[String]) {
        var masterUrl = "local[1]"
        var dataPath = "data/mllib/kmeans_data.txt"
        if (args.length > 0) {
            masterUrl = args(0)
        } else if (args.length > 1) {
            dataPath = args(1)
        }

        // Create a SparContext with the given master URL
        val conf = new SparkConf().setMaster(masterUrl).setAppName("KMeansExample")

        val sc = new SparkContext(conf)

        Logger.getRootLogger.setLevel(Level.WARN)

        val examples = sc.textFile(dataPath).map { line =>
            Vectors.dense(line.split(' ').map(_.toDouble))
        }.cache()

        val numExamples = examples.count()

        println(s"numExamples = $numExamples.")

        val k = 2
        val numIterations = 10

        val model = new KMeans()
            .setInitializationMode(KMeans.RANDOM)
            .setK(k)
            .setMaxIterations(numIterations)
            .run(examples)

        val cost = model.computeCost(examples)

        println(s"Total cost = $cost.")

        val kmeansModelPath = "/tmp/model/kmeans"

        val fs = FileSystem.get(new Configuration())
        fs.delete(new Path(kmeansModelPath), true)

        model.save(sc, kmeansModelPath)
        val keansModel = KMeansModel.load(sc, kmeansModelPath)

        sc.stop()
    }
}

// scalastyle:on println