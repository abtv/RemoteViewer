package application

import java.awt.{Rectangle, Robot, Toolkit}
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

import common._

object Screenshot {
  def capture = {
    val screenSize = Toolkit.getDefaultToolkit().getScreenSize()
    val screenRect = new Rectangle(screenSize)
    val capture = new Robot().createScreenCapture(screenRect)

    usage(new ByteArrayOutputStream()){
      baos =>
        ImageIO.write(capture, Settings.imageFormat, baos )
        baos.flush()
        baos.toByteArray()
    }
  }
}
