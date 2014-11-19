import application.{IO, Screenshot}
import udpchannel.UdpServer
import ui.ServerWindow
import webchannel.WebServer

object Server {
  def main(args: Array[String]) = {
    val window = new ServerWindow()
    window.visible = true

    val udpServer = new UdpServer()
    udpServer.start()

    while(true){
      IO.put(Screenshot.capture)
      Thread.sleep(application.Settings.screenShotTimeout)
    }
  }
}