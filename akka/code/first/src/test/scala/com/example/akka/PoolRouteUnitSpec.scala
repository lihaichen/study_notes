package com.example.akka

import akka.actor.typed.{ActorSystem, Behavior, SupervisorStrategy}
import org.specs2.mutable.Specification
import akka.actor.typed.scaladsl.{Behaviors, PoolRouter, Routers}

object Worker {

  sealed trait Command

  case class DoLog(text: String) extends Command

  def apply(): Behavior[Command] = Behaviors.setup {
    context => {
      println("Starting worker")
      Behaviors.receiveMessage {
        case DoLog(text) => {
          println(s"Got message {$text} @${context.self.path}")
          Behaviors.same
        }
      }
    }

  }

}

class PoolRouteUnitSpec extends Specification {
  val pool: PoolRouter[Worker.Command] = Routers.pool(4)(
    Behaviors.supervise(Worker()).onFailure[Exception](SupervisorStrategy.restart)).withRandomRouting()
  val system: ActorSystem[Worker.Command] = ActorSystem(pool, "worker-pool")
  (0 to 10).foreach { n =>
    system ! Worker.DoLog(s"msg $n")
  }
}
