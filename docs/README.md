# Apache Spark Examples

#### Spark User Side Metrics
Spark metrics system based on the Dropwizard Metrics Library.
To start sending app level metrics the following needs to be done:
 1. Create Metrics class - for instance simple Gauge like [SimpleGauge.scala](https://github.com/timout/spark-examples/blob/master/src/main/scala/timout/spark/metrics/SimpleGauge.scala).
 2. Implement org.apache.spark.metrics.source.Source like for example [SimpleSource.scala](https://github.com/timout/spark-examples/blob/master/src/main/scala/org/apache/spark/metrics/source/SimpleSource.scala).
 3. Create [metrics.properties](https://github.com/timout/spark-examples/blob/master/conf/metrics.properties).
 4. In your spark app register the metrics class like in [SampleDataStream](https://github.com/timout/spark-examples/blob/master/src/main/scala/timout/spark/metrics/SampleDataStream.scala).
 
Metrics properties file should be under SPARK_HOME/conf/ or spark.metrics.conf configuration parameter must point to the file.