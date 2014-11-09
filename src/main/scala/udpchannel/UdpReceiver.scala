package udpchannel

import java.net.{DatagramPacket, InetAddress, MulticastSocket}

import application.{Image, TReceiver}

class UdpReceiver(address: String) extends TReceiver{
  private val lock  = new Object()
  private var image = new Image(None, null)
  private val frameRing = new FrameRing()
  private var count = 0L

  def getImage = {
    synchronized(lock)
      image
  }

  override def getAddress = address

  private def receive(socket: MulticastSocket) = {
    try {
      val buf = new Array[Byte](16*1024)
      val packet = new DatagramPacket(buf, buf.length)
      socket.receive(packet)
      count += 1
      if (packet.getLength > 0){
        //val received = packet.getData().take(packet.getLength)
        val buffer = packet.getData
        val length = packet.getLength
        //val buffer = new Array[Byte]()
        //Array.copy(received, 0, buffer, 0, buffer.length)

        //println(ByteBuffer.wrap(buffer.take(4)).getInt)
        Actors.writerActor ! (buffer, length)

        //val frame = new Frame(buffer, length)

        //frameRing.put(frame)
        //println(frame)

//        if (count % 20 == 0){
//          val tmp = frameRing.getFrames
//          if (tmp != null){
//            common.using(new ByteArrayInputStream(tmp)){
//              in =>
//                val newImage =  ImageIO.read(in)
//                image = new Image(Some(DateTime.now()), newImage)
//            }
//          }
//        }
      }
    }catch{
      case ex: Exception => println("Error: " + ex.getMessage)
    }
  }

  private val thread = new Thread(new Runnable {
    override def run(): Unit ={
      val socket = new MulticastSocket(44446)
      socket.setReceiveBufferSize(8*1024*1024)//64*1024)
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
