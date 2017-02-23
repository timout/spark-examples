package timout.spark.metrics

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by timout on 2/22/17.
  * Simple socket stream
  * Connects to provided host and counts records, and update simple metrics gauge with rdd id
  * gauge path: metrics.example.driver.timout.spark.metrics.SampleDataStream.simple
  * Arguments:
  *   host: default localhost
  *   port: default 18988
  *   duration(seconds): default 1
  */
object SampleDataStream  {

  def main(args: Array[String]): Unit = {
    val host = if ( args.length > 0 ) args(0) else "localhost"
    val port = if ( args.length > 1 ) args(1).toInt else 18988
    val duration = if ( args.length > 2 ) args(2).toInt else 1

    val sparkConf = new SparkConf()
    sparkConf.set("spark.metrics.namespace", "metrics.example")
    val ssc = new StreamingContext(sparkConf, Seconds(duration))
    ssc.sparkContext.setLogLevel("WARN")

    val stream = ssc.socketTextStream(host, port)

    val name = ssc.sparkContext.appName

    val simpleMetrics = SimpleGauge(name)

    stream.foreachRDD{ rdd =>
      simpleMetrics.update(rdd.id)
      rdd.foreachPartition{ p =>
        val c = p.count( _ => true)
        println(c)
      }
    }
    ssc.start()
    ssc.awaitTermination()
  }

}
