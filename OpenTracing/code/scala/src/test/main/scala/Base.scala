import io.jaegertracing.Configuration
import io.jaegertracing.internal.{JaegerSpan, JaegerTracer}
import io.opentracing.util.GlobalTracer
import org.specs2.mutable.Specification


class Base extends Specification {
  val config = new Configuration("Base")
  val sender = new Configuration.SenderConfiguration
  sender.withAgentHost("192.168.31.84")
  sender.withAgentPort(5775)
  config.withReporter(new Configuration.ReporterConfiguration().withSender(sender))

  config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1))
  val trace: JaegerTracer = config.getTracer

  GlobalTracer.registerIfAbsent(trace)
  val span: JaegerSpan = trace.buildSpan("operationName").start()
  println(span.context().getSpanId.toHexString, span.context().toTraceId)
  span.setTag("tag", "tag1")
  span.setBaggageItem("Baggage","Baggage1")
  span.log("log 1")
  val span2 = trace.buildSpan("operationName2").asChildOf(span).start()
  span2.setTag("tag", "tag2")

  val bb = span2.getBaggageItem("Baggage")
  println(bb)
  println(span2.context().getSpanId.toHexString, span.context().toTraceId)
//  span2.setBaggageItem("Baggage","Baggage1")
  span2.log("log 2")
  span2.finish()
  span.finish()
}
