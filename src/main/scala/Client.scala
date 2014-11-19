import application.Image
import udpchannel.UdpReceiver
import ui.ClientWindow

import scala.swing._

object Client extends SimpleSwingApplication {
  val image = new Image(-1, None, null)
  val receiver = new UdpReceiver(image)

  def top = new ClientWindow(image)
}