import java.util.concurrent.Executors

import org.specs2.mutable.Specification
import io.jaegertracing.Configuration
import io.jaegertracing.internal.metrics.InMemoryMetricsFactory
import io.jaegertracing.internal.reporters.{CompositeReporter, LoggingReporter, RemoteReporter}
import io.jaegertracing.internal.samplers.ConstSampler
import io.jaegertracing.internal.{JaegerSpan, JaegerTracer}
import io.opentracing.propagation.TextMapAdapter
import io.opentracing.propagation.Format
import io.opentracing.util.GlobalTracer

import scala.collection.JavaConverters._
import scala.concurrent._
//import ExecutionContext.Implicits.global

import io.opentracing.util.GlobalTracer

class BaseHttpUnit extends Specification {
  val config = new Configuration("BaseHttp")
  val sender = new Configuration.SenderConfiguration
  sender.withAgentHost("jaeger.raiborn")
  sender.withAgentPort(6831)
  //  sender.withEndpoint("http://dockermisc.raiborn:12800")
  config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1))
  config.withReporter(new Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000))
  val trace: JaegerTracer = config.getTracer

  GlobalTracer.registerIfAbsent(trace)
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(20))


  for (i <- 1 to 100) {

    Future {
      val span: JaegerSpan = trace.buildSpan(s"114operationName_$i").start()
      val scope = trace.activateSpan(span)
      println(span.context().getSpanId.toHexString, span.context().toTraceId)
      span.setTag("tag", "tag1")
      span.setBaggageItem("Baggage", "Baggage1")
      span.log("log 1")
      val span2 = trace.buildSpan("operationName2").asChildOf(span).start()
      span2.setTag("tag", "tag2")
      Thread.sleep(2000)
      val bb = span2.getBaggageItem("Baggage")
      println(bb)
      println(span2.context().getSpanId.toHexString, span.context().toTraceId)
      //  span2.setBaggageItem("Baggage","Baggage1")
      span2.log("log 2")
      span2.finish()
      span.log(throw new Exception(""))
      span.finish()
      scope.close()
    }


  }
  Thread.sleep(20000)
}
