package udpchannel

import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

import akka.actor.Actor
import application.Image
import org.joda.time.DateTime
import common._

class ReceiverActor(image: Image) extends Actor{
  var frames  = new Array[Array[Byte]](128)
  var imageId = -1
  var maxId   = 0
  var completedCount = 0

  override def receive = {
    case packet: Packet => {
      val frame = new Frame(packet.buffer, packet.length)
      if (frame.imageId != imageId){
        clearFrames

        imageId = frame.imageId
        maxId   = frame.maxId
        completedCount = 0
      }

      val id = frame.id
      if (frames(id) == null){
        frames(id) = frame.data
        completedCount += 1
      }

      if (completedCount == maxId + 1){

        val buffer = frames
          .take(completedCount)
          .foldLeft(new Array[Byte](0))((acc,x) => acc ++ x)

        image.imageId = imageId
        image.loadTime = Some(DateTime.now())
        usage(new ByteArrayInputStream(buffer)){
          stream => image.image = ImageIO.read(stream)
        }

        clearFrames
      }
    }
  }

  def clearFrames = {
    for (i <- 0 to frames.length - 1)
      frames(i) = null
  }
}
