package udpchannel

import java.net.{DatagramPacket, InetAddress, MulticastSocket}

import application.{IO, TServer}

class UdpServer(port: Int) extends TServer{
  private var imageId = 0

  override def getPort = port

  private def send(socket: MulticastSocket, group: InetAddress) = {
    try {
      val image = IO.get()
      if (image != null){
        val frames = ArraySlicer.slice(imageId, image)
        frames.foreach(frame => {
          val buffer = frame.toBytesArray
          val packet = new DatagramPacket(buffer, buffer.length, group, 44446)
          socket.send(packet)
          println(frame)
        })
        imageId += 1
      }
    }catch{
      case ex: Exception => println("Error: " + ex.getMessage)
    }
  }

  private val thread = new Thread(new Runnable {
    override def run(): Unit = {
      val socket = new MulticastSocket()
      val group = InetAddress.getByName("225.4.5.6")
      while (true) {
        send(socket, group)
        Thread.sleep(200)
      }
      socket.close
    }
  })

  override def start(): Unit = thread.start
}
