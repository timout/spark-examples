package org.apache.spark.metrics.source
import com.codahale.metrics.{Gauge, MetricRegistry}
import org.apache.spark.SparkEnv

/**
  * Created by timout on 2/22/17.
  */
class SimpleSource(appName: String, gauge: Gauge[_]) extends Source {

  override val sourceName: String = s"$appName"

  override val metricRegistry: MetricRegistry = new MetricRegistry

  metricRegistry.register("simple", gauge)
}

object SimpleSource {
  def register(appName: String, gauge: Gauge[_]): Unit = {
    val source = new SimpleSource(appName, gauge)
    SparkEnv.get.metricsSystem.registerSource(source)
  }
}
