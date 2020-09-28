import org.specs2.mutable.Specification
import io.jaegertracing.Configuration
import io.jaegertracing.internal.metrics.InMemoryMetricsFactory
import io.jaegertracing.internal.reporters.{CompositeReporter, LoggingReporter, RemoteReporter}
import io.jaegertracing.internal.samplers.ConstSampler
import io.jaegertracing.internal.{JaegerSpan, JaegerTracer}
import io.opentracing.propagation.TextMapAdapter
import io.opentracing.propagation.Format
import scala.collection.JavaConverters._


class TestUnit extends Specification {
  println("hello")
  val config = new Configuration("close")
  val sender = new Configuration.SenderConfiguration
  sender.withAgentHost("stage.raiborn")
  sender.withAgentPort(6831)

  config.withReporter(new Configuration.ReporterConfiguration().withSender(sender))
  config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1))
  val trace: JaegerTracer = config.getTracer

  val map = new java.util.HashMap[String, String]


  "test1" should {
    val span: JaegerSpan = trace.buildSpan("operationName").start()
    span.log("test close")
    Thread.sleep(100)
    val span2 = trace.buildSpan("2").asChildOf(span).start()

    span2.log("span2 hello")
    Thread.sleep(1000)
    span.finish()
//    trace.scopeManager().activate(span2)
    span2.finish()
    val tmp = new TextMapAdapter(map)
    trace.inject(span2.context(), Format.Builtin.TEXT_MAP, tmp)
    println(map.size())

    map.asScala.foreach(item => {
      println(item)
    })

    "" mustEqual ""
  }

  "test2" should {
    Thread.sleep(5000)
    val tmp = new TextMapAdapter(map)
    val tt = trace.extract(Format.Builtin.TEXT_MAP, tmp)
//    val s = trace.buildSpan(tt).
    val span = trace.buildSpan("test2").asChildOf(tt).start()
    Thread.sleep(1000)
    span.finish()
    "" mustEqual ""
  }


}
