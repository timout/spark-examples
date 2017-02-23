package timout.spark.metrics

import java.util.concurrent.atomic.AtomicLong

import com.codahale.metrics.Gauge
import org.apache.spark.SparkContext
import org.apache.spark.metrics.source.SimpleSource

/**
  * Created by timout on 2/22/17.
  */

trait SimpleMetrics {

  def update(value: Long)

}

class SimpleGauge extends Gauge[Long] with SimpleMetrics with Serializable{

  private val counter = new AtomicLong(0)

  override def getValue: Long = counter.get()

  override def update(value: Long): Unit = counter.set(value)
}

object SimpleGauge {

  def apply(name: String): SimpleGauge = {
    val g = new SimpleGauge
    SimpleSource.register(name, g)
    g
  }
}