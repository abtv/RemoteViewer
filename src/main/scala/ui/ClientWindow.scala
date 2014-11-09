package ui

import java.awt.image.BufferedImage
import java.awt.{Color, Dimension, Font, GradientPaint}

import application.Image
import org.joda.time.DateTime

import scala.swing.{MainFrame, Panel, Swing}

class ClientWindow(image: Image, address: String) extends MainFrame {
  title = "Remote viewer"
  centerOnScreen()

  maximize()
  preferredSize = new Dimension(800,600)

  contents = new Panel {
    var last: Option[DateTime] = None
    var img: BufferedImage = null

    listenTo(mouse.clicks)

    override def paintComponent(g: java.awt.Graphics2D) {
      if (img == null){
        val text = "Нет соединения с " + address

        val paint = new GradientPaint(0, 0, Color.white, size.width, 0, Color.gray)
        g.setPaint(paint)
        g.fillRect(0, 0, size.width, size.height)

        g.setFont(new Font("Courier", Font.PLAIN, 16))
        g.setColor(Color.black)

        val stringLen = g.getFontMetrics().getStringBounds(text, g).getWidth()
        val start = size.width/2 - stringLen/2
        g.drawString(text, start.toInt, size.height/2)
      }
      else{
        val scalex = (size.width)/(img.getWidth.asInstanceOf[Double])
        val scaley = (size.height)/(img.getHeight.asInstanceOf[Double])

        val scale = math.min(scalex, scaley)
        val w = (scale*img.getWidth()).asInstanceOf[Int]
        val h = (scale*img.getHeight).asInstanceOf[Int]

        g.drawImage(img, (size.width - w)/2, (size.height - h)/2, w, h, null)
      }
    }

    def isConnectionLost(image: Image): Boolean = {
      val timeoutMs = 2000
      if (image.loadTime == None)
        true
      else if (DateTime.now().getMillis - image.loadTime.get.getMillis > timeoutMs)
        true
      else
        false
    }

    def isUpdateNeeded(image: Image): Boolean = {
      image.loadTime match {
        case None    => false
        case Some(x) => x != last
      }
    }

    val timer = new javax.swing.Timer(
      100, Swing.ActionListener(e =>
      {
        if(isConnectionLost(image)){
          img = null
          repaint()
        }else if (isUpdateNeeded(image)){
          img = image.image
          repaint()
        }
      })
    )
    timer.start()
  }
}
