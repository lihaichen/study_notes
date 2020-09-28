import io.jaegertracing.Configuration
import io.jaegertracing.internal.metrics.InMemoryMetricsFactory
import io.jaegertracing.internal.reporters.{CompositeReporter, LoggingReporter, RemoteReporter}
import io.jaegertracing.internal.samplers.ConstSampler
import io.jaegertracing.internal.{JaegerSpan, JaegerTracer}
import io.jaegertracing.thrift.internal.senders.UdpSender
import org.specs2.mutable.Specification

class CloseUnitSpec extends Specification {
//  val config = new Configuration("close")
//  val sender = new Configuration.SenderConfiguration
//  sender.withAgentHost("stage.raiborn")
//  sender.withAgentPort(6831)
//
//  config.withReporter(new Configuration.ReporterConfiguration().withSender(sender))
//  config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1))
//  val trace: JaegerTracer = config.getTracer
//  val span: JaegerSpan = trace.buildSpan("operationName").start()
//  span.log("test close")
//  Thread.sleep(10000)
//  span.finish()


  println("hello")
//  val reporter = new CompositeReporter(
//    new RemoteReporter.Builder()
//      .withSender(new UdpSender(
//       "stage.raiborn",
//        6831
//      ))
//      .build(),
//    new LoggingReporter()
//  )
//
//  val tracer = new JaegerTracer.Builder("serviceName")
//    .withReporter(reporter)
//    .withSampler(new ConstSampler(true))
//    .build()
//  val span = tracer.buildSpan("operationName").start()
//  span.log("test close")
//  Thread.sleep(1000000)
//  span.finish()
//
//  Thread.sleep(1000000)
}
