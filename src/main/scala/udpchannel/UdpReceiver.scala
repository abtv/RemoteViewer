package udpchannel

import java.io.ByteArrayInputStream
import java.net.{DatagramPacket, InetAddress, MulticastSocket}
import javax.imageio.ImageIO

import application.{Image, TReceiver}
import org.joda.time.DateTime

class UdpReceiver(address: String) extends TReceiver{
  private val lock  = new Object()
  private var image = new Image(None, null)
  private val frameRing = new FrameRing()

  def getImage = {
    synchronized(lock)
      image
  }

  override def getAddress = address

  private def receive(socket: MulticastSocket) = {
    try {
      val buf = new Array[Byte](64*1024)
      val packet = new DatagramPacket(buf, buf.length)
      socket.receive(packet)
      if (packet.getLength > 0){
        val received = packet.getData().take(packet.getLength)
        val frame = new Frame(received)

        frameRing.put(frame)

        val tmp = frameRing.getFrames
        if (tmp != null){
          common.using(new ByteArrayInputStream(tmp)){
            in =>
              val newImage =  ImageIO.read(in)
              image = new Image(Some(DateTime.now()), newImage)
          }
        }
      }
    }catch{
      case ex: Exception => println("Error: " + ex.getMessage)
    }
  }

  private val thread = new Thread(new Runnable {
    override def run(): Unit ={
      val socket = new MulticastSocket(4446)
      socket.setReceiveBufferSize(64*1024)
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
