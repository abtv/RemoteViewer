package webchannel

import java.net.URL
import javax.imageio.ImageIO

import application.{TReceiver, Image}
import org.joda.time.DateTime

class WebReceiver(val address: String) extends TReceiver{
  private val lock  = new Object()
  private var image = new Image(-1, None, null)

  def getImage = {
    synchronized(lock)
      image
  }

  def getAddress = address

  private def receive() = {
    try {
      val data = ImageIO.read(new URL(address))
      if (data != null){
        synchronized(lock)
          image = new Image(0, Some(DateTime.now()), data)
      }
    }catch{
      case ex: Exception => println("Error: " + ex.getMessage)
    }
  }

  private val thread = new Thread(new Runnable {
    override def run(): Unit =
      while(true){
        receive()
        Thread.sleep(200)
      }
  })
  thread.start
}
