package udpchannel

import akka.actor.Actor

class ReceiverActor extends Actor{
  override def receive = {
    case msg: (Array[Byte], Int) => {
      val frame = new Frame(msg._1, msg._2)
      println(frame)
    }
  }
}
