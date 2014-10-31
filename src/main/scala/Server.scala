import application.{IO, Screenshot}
import udpchannel.UdpServer
import ui.ServerWindow
import webchannel.WebServer

object Server {
  def main(args: Array[String]) = {
    val settings = Settings.loadSettings()
    val port = settings.port

    val window = new ServerWindow(port.toString)
    window.visible = true

    val webServer = new WebServer(port)
    webServer.start()
    val udpServer = new UdpServer(port)
    udpServer.start()

    while(true){
      IO.put(Screenshot.capture)
      Thread.sleep(200)
    }
  }
}