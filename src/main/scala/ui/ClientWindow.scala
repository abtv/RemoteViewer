package ui

import java.awt.image.BufferedImage
import java.awt.{Color, GradientPaint, Dimension, Font}

import application.{Settings, Image}
import org.joda.time.DateTime

import scala.swing.event.MousePressed
import scala.swing.{MainFrame, Panel, Swing}

class ClientWindow(image: Image) extends MainFrame {
  title = "Remote viewer"
  centerOnScreen()

  maximize()
  preferredSize = new Dimension(800,600)

  contents = new Panel {
    var last: Option[DateTime] = None
    var img: BufferedImage = null


    val fontSize = 16
    val textFont = new Font("Courier", Font.BOLD, fontSize)

    listenTo(mouse.clicks)

    var bestFit = false
    reactions += {
      case e: MousePressed => bestFit = !bestFit
    }

    override def paintComponent(g: java.awt.Graphics2D) {
      g.setFont(textFont)
      g.setColor(Color.green)

      if (img == null){
        val text = "Нет соединения с " + Settings.multicastAddress

        val paint = new GradientPaint(0, 0, Color.white, size.width, 0, Color.gray)
        g.setPaint(paint)
        g.fillRect(0, 0, size.width, size.height)

        val stringLen = g.getFontMetrics().getStringBounds(text, g).getWidth()
        val start = size.width/2 - stringLen/2
        g.drawString(text, start.toInt, size.height/2)
      }
      else{
        g.clearRect(0, 0, size.width, size.height)
        if (bestFit){
          val scalex = (size.width)/(img.getWidth.asInstanceOf[Double])
          val scaley = (size.height)/(img.getHeight.asInstanceOf[Double])

          val scale = math.min(scalex, scaley)
          val w = (scale*img.getWidth()).asInstanceOf[Int]
          val h = (scale*img.getHeight).asInstanceOf[Int]

          g.drawImage(img, (size.width - w)/2, (size.height - h)/2, w, h, null)
        } else
          g.drawImage(img, 0, 0, img.getWidth, img.getHeight, null)



        val text = "Frame: " + image.imageId
        val stringLen = g.getFontMetrics().getStringBounds(text, g).getWidth().toInt
        val start = size.width/2 - stringLen/2

        g.drawString(text, size.width - stringLen - 10, 20)
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
