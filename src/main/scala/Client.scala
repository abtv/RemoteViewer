import channel.Receiver
import ui.ClientWindow

import scala.swing._

object Client extends SimpleSwingApplication {
  val settings = Settings.loadSettings()
  val host = settings.host
  val port = settings.port
  val address = s"http://$host:$port"

  val receiver = new Receiver(address)

  def top = new ClientWindow(receiver)
}