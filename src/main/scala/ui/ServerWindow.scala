package ui

import java.awt.image.BufferedImage
import java.awt.{Color, Dimension, Font, GradientPaint}

import application.Settings

import scala.swing.{MainFrame, Panel}

class ServerWindow() extends MainFrame {
  title = "Remote server"
  preferredSize = new Dimension(320, 240)
  centerOnScreen()

  contents = new Panel {
    var img: BufferedImage = null

    override def paintComponent(g: java.awt.Graphics2D) {
      if (img == null) {
        val text = "Сервер запущен на " + Settings.multicastAddress

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