import java.io.Closeable
import java.lang.AutoCloseable
import org.specs2.mutable.Specification


class A extends AutoCloseable {
  override def close(): Unit = {
    println(" run close fun")
  }

  def hello(): Unit = {
    println("hello")
  }
}

class CloseAble extends Specification {
  println("start .... ")
  try {
    val a = new A
    1 / 0
    a.hello()
  } catch {
    case exception: Exception =>
      println(exception)
  }
  println("end .... ")
  // scala 的 try 不能设置资源
  // AutoCloseable 不能自动释放
}
