package udpchannel

import akka.actor.{ActorSystem, Props}

object Actors {
  private val system = ActorSystem("MySystem")

  val writerActor = system.actorOf(Props(new ReceiverActor()),name = "receiver_actor")
}