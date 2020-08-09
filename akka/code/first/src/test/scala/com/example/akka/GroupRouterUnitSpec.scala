package com.example.akka

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.{Behaviors, GroupRouter, Routers}
import org.specs2.mutable.Specification
//
//object Worker {
//  sealed trait Command
//
//  case class DoLog(text: String) extends Command
//
//  def apply(): Behavior[Command] = Behaviors.setup {
//    context =>{
//      println(s"Starting worker ${context.self.path}")
//      Behaviors.receiveMessage{
//        case DoLog(text)=>
//          println(s"Got message {$text} @${context.self.path}")
//          Behaviors.same
//      }
//    }
//  }
//}

class GroupRouterUnitSpec  extends Specification {
  val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty , "GroupRouterUnitSpec")
  val work1: ActorRef[Worker.Command] = system.systemActorOf(Worker(), "work1")
  val work2: ActorRef[Worker.Command] = system.systemActorOf(Worker(), "work2")
  val work3: ActorRef[Worker.Command] = system.systemActorOf(Worker(), "work3")
  val serviceKey: ServiceKey[Worker.Command] = ServiceKey[Worker.Command]("work")
  system.receptionist ! Receptionist.Register(serviceKey, work1)
  system.receptionist ! Receptionist.Register(serviceKey, work2)
  system.receptionist ! Receptionist.Register(serviceKey, work3)

  val group: GroupRouter[Worker.Command] = Routers.group(serviceKey).withRoundRobinRouting()
  val router: ActorRef[Worker.Command] = system.systemActorOf(group, "group")

  (0 to 10).foreach { n =>
    router ! Worker.DoLog(s"msg $n")
  }
}
