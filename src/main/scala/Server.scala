import application.{Screenshot, IO}
import channel.WebServer
import ui.ServerWindow

object Server {
  def main(args: Array[String]) = {
    val settings = Settings.loadSettings()
    val port = settings.port

    val window = new ServerWindow(port.toString)
    window.visible = true

    val webServer = new WebServer(port)

    while(true){
      IO.put(Screenshot.capture)
      Thread.sleep(200)
    }
  }
}