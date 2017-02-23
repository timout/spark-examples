package timout.spark.generator

import java.io.PrintWriter
import java.net.ServerSocket
import java.util.concurrent.atomic.AtomicLong

/**
  * Created by timout on 2/22/17.
  *
  * Simple Text Stream Producer
  * Creates socket on port - first argument (default: 18988)
  * and sends messages in the following format: "Message <counter> <timestamp>" every x (second argument, default=200) milliseconds
  *
  */
object SampleDataProducer {
  val counter = new AtomicLong(0)

  def message : String = {
    val c = counter.incrementAndGet()
    val t = System.currentTimeMillis()
    s"Message $c $t"
  }

  def main(args: Array[String]) {
    val port = if ( args.length > 0 ) args(0).toInt else 18988
    val sleep = if ( args.length > 1 ) args(1).toLong else 200L
    val listener = new ServerSocket(port)
    println("Listening on port: " + port)

    while (true) {
      val socket = listener.accept()
      socket.setKeepAlive(true)
      new Thread() {
        override def run(): Unit = {
          println("Got client connected from: " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream, true)
          while (true) {
            Thread.sleep(sleep)
            out.write(message)
            out.flush()
          }
          socket.close()
        }
      }.start()
    }
  }
}