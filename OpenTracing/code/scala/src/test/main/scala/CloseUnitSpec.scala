//import io.jaegertracing.Configuration
//import io.jaegertracing.internal.{JaegerSpan, JaegerTracer}
//import org.specs2.mutable.Specification
//
//class CloseUnitSpec extends Specification {
//  println("CloseUnitSpec")
//  val config = new Configuration("close")
//  val sender = new Configuration.SenderConfiguration
//  sender.withAgentHost("stage.raiborn")
//  sender.withAgentPort(6831)
//
//  config.withReporter(new Configuration.ReporterConfiguration().withSender(sender))
//  config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1))
//  val trace: JaegerTracer = config.getTracer
//  val parentSpan: JaegerSpan = trace.buildSpan("parent").start()
//  trace.activateSpan(parentSpan)
//  parentSpan.log("parentSpan log")
//  Thread.sleep(100)
//
//  val spanChild1: JaegerSpan = trace.buildSpan("child1").asChildOf(parentSpan).start()
//  spanChild1.log("spanChild1 log")
//  Thread.sleep(100)
////  parentSpan.finish()
////  Thread.sleep(100)
//  spanChild1.finish()
//
//
//  val spanChild2: JaegerSpan = trace.buildSpan("child2")
//    .addReference("follows_from", parentSpan.context()).start()
//  spanChild2.log("spanChild2 log")
//  Thread.sleep(100)
//  spanChild2.finish()
//  Thread.sleep(100)
//  trace.activeSpan().finish()
//
////  trace.buildSpan(parentSpan.context()).start().finish()
//
//
//}
