package application

trait TServer {
  def getPort: Int
  def start(): Unit
}
