package channel

import java.net.URL
import javax.imageio.ImageIO

import org.joda.time.DateTime

class Receiver(val address: String) {
  private var image = new Image(None, null)

  def getImage = {
    synchronized(this)
      image
  }

  private def receive() = {
    try {
      val data = ImageIO.read(new URL(address))
      if (data != null){
        synchronized(this)
          image = new Image(Some(DateTime.now()), data)
      }
    }catch{
      case ex: Exception => println("Error: " + ex.getMessage)
    }
  }

  val thread = new Thread(new Runnable {
    override def run(): Unit =
      while(true){
        receive()
        Thread.sleep(200)
      }
  })
  thread.start
}
