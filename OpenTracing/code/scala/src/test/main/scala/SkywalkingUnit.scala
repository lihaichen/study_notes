//import org.specs2.mutable.Specification
//import io.opentracing.{References, Tracer}
//import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer
//import org.apache.skywalking.apm.toolkit.trace.{ActiveSpan, Trace, TraceContext}
//
//class SkywalkingUnit extends Specification {
//  println("hello1")
////  @Trace
////  def test():Unit ={
////    println(TraceContext.traceId())
////    println(TraceContext.spanId())
////    ActiveSpan.info("hello")
////    ActiveSpan.setOperationName("-----")
////    println(TraceContext.segmentId())
////  }
////
////  test()
//
//
//  val tracer = new SkywalkingTracer()
//  println("hello2")
//  val spanBuild = tracer.buildSpan("span1")
//  println("hello3")
//  val span = spanBuild.start()
//  println("hello4", span)
//  span.setTag("tag1","tag1")
//  span.log("2")
//  println("hello5")
//
//  val span2 = tracer.buildSpan("span2")
//    .asChildOf(span).startManual()
//  Thread.sleep(1000)
//  span2.log("span2")
//  span2.finish()
//
//  span.finish()
//
//
//
//
//  println("hello6")
//}
