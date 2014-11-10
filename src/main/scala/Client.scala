import application.Image
import udpchannel.UdpReceiver
import ui.ClientWindow

import scala.swing._

object Client extends SimpleSwingApplication {
  val settings = Settings.loadSettings()
  val host = settings.host
  val port = settings.port
  val address = s"http://$host:$port"

  val image = new Image(-1, None, null)
  val receiver = new UdpReceiver(image, address)

  def top = new ClientWindow(image, address)
}