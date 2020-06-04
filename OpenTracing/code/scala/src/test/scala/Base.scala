import org.specs2.mutable.Specification
import io.jaegertracing.internal.{JaegerSpan, JaegerTracer}
import io.opentracing.Span
import io.opentracing.Tracer
import io.opentracing.util.GlobalTracer
import io.jaegertracing.Configuration


class Base extends Specification {
  val config = new Configuration("Base")
  val sender = new Configuration.SenderConfiguration
  sender.withAgentHost("192.168.31.84")
  sender.withAgentPort(5775)
  config.withReporter(new Configuration.ReporterConfiguration().withSender(sender))

  config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1))
  val trace: JaegerTracer = config.getTracer
  println(trace.toString)

  GlobalTracer.registerIfAbsent(trace)
  val span: JaegerSpan = trace.buildSpan("1").start()
  span.log("hello 1")
  span.finish()
}
