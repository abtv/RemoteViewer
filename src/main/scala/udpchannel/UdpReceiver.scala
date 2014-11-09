package udpchannel

import java.net.{DatagramPacket, InetAddress, MulticastSocket}

import akka.actor.{ActorSystem, Props}
import application.Image

class UdpReceiver(image: Image, address: String) {
  private val system = ActorSystem("MySystem")
  private val writerActor = system.actorOf(Props(new ReceiverActor(image)),name = "receiver_actor")

  private def receive(socket: MulticastSocket) = {
    try {
      val buf = new Array[Byte](16*1024)
      val packet = new DatagramPacket(buf, buf.length)
      socket.receive(packet)
      if (packet.getLength > 0){
        val buffer = packet.getData
        val length = packet.getLength
        writerActor ! new Packet(buffer, length)
      }
    }catch{
      case ex: Exception => println("Error: " + ex.getMessage)
    }
  }

  private val thread = new Thread(new Runnable {
    override def run(): Unit ={
      val socket = new MulticastSocket(44446)
      socket.setReceiveBufferSize(8*1024*1024)
      socket.setSoTimeout(10000)
      val groupAddress = InetAddress.getByName("225.4.5.6")
      socket.joinGroup(groupAddress)

      while(true)
        receive(socket)

      socket.leaveGroup(groupAddress)
      socket.close()
    }
  })
  thread.start
}
