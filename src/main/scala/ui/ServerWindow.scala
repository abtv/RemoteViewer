package ui

import java.awt.image.BufferedImage
import java.awt.{Color, Dimension, Font, GradientPaint}

import scala.swing.{MainFrame, Panel}

class ServerWindow(address: String) extends MainFrame {
  title = "Remote server"
  preferredSize = new Dimension(320, 240)
  centerOnScreen()

  contents = new Panel {
    var img: BufferedImage = null

    override def paintComponent(g: java.awt.Graphics2D) {
      if (img == null) {
        val text = s"Сервер запущен на $address"

        val paint = new GradientPaint(0, 0, Color.white, size.width, 0, Color.gray)
        g.setPaint(paint)
        g.fillRect(0, 0, size.width, size.height)

        g.setFont(new Font("Courier", Font.PLAIN, 16))
        g.setColor(Color.black)

        val stringLen = g.getFontMetrics().getStringBounds(text, g).getWidth()
        val start = size.width / 2 - stringLen / 2
        g.drawString(text, start.toInt, size.height / 2)
      }
      else
        g.drawImage(img, 0, 0, null)

    }

  }
}