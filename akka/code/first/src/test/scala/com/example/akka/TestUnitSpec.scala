package com.example.akka

import akka.actor.typed.ActorSystem

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.Behaviors

import org.specs2.mutable.Specification

class FirstActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  override def onMessage(msg: String): Behavior[String] = {
    msg match {
      case "start" =>
        println(s"First: ${msg}")
        println(this.context.self)
        val secondRef = context.spawn(Behaviors.empty[String], "second-actor")
        println(s"Second: $secondRef")
        this
    }
  }
}

object FirstActor {
  def apply(): Behavior[String] = Behaviors.setup(context => new FirstActor(context))
}

sealed trait Command

case class Tell(message: String) extends Command

object SecondActor {
  def apply(): Behavior[Command] = Behaviors.setup {
    context =>
      Behaviors.receiveMessage {
        case Tell(message) =>
          println(message)
          Behaviors.same
      }
  }
}

class TestUnitSpec extends Specification {
  val testSystem = ActorSystem(SecondActor(), "first")
  testSystem ! Tell("hello")
}
