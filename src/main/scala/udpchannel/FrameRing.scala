package udpchannel

class FrameRing {
  private val frames = new Array[Frame](2560)
  private var freeIndex = 0
  private var lastImageIndex = -1

  def put(frame: Frame) = {
    frames(freeIndex) = frame
    freeIndex += 1
    if (freeIndex == frames.length)
      freeIndex = 0
  }

  def getFrames: Array[Byte] = {
    val fullImages = frames
      .filter(x => x != null)
      .groupBy(x => x.imageId)
      .filter(x => x._2.length > 0)
      .filter(x => x._2.length == x._2(0).maxId)
      .toArray
      .sortBy(x => -x._1)

    fullImages.length match {
      case 0 => null
      case _ => {
        val index  = fullImages(0)._1
        val result = fullImages(0)._2
        if (index > lastImageIndex){
          lastImageIndex = index

          result
            .sortBy(x => x.id)
            .foldLeft(new Array[Byte](0))((acc, x) => acc ++ x.data)
        }else
          null
      }
    }
  }
}