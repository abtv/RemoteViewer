package application

object IO {
  private var image: Array[Byte] = null

  def put(buffer: Array[Byte]) = {
    synchronized(this)
      image = buffer
  }

  def get(): Array[Byte] ={
    synchronized(this)
      image
  }
}
