package udpchannel

object ArraySlicer {
  val maxSize = 8*1024 - 3*4

  def slice(imageId: Int, buffer: Array[Byte]) = {
    val frames = buffer
      .grouped(maxSize)
      .map(x => new Frame(imageId, 0, 0, x))
      .toArray

    val maxId = frames.length - 1
    for(i <- 0 to maxId){
      frames(i).id = i
      frames(i).maxId = maxId
    }

    frames
  }
}
