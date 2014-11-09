package udpchannel

import java.nio.ByteBuffer

class Frame(var imageId: Int, var id: Int, var maxId: Int, var data: Array[Byte]){
  def this(buffer: Array[Byte], length: Int) = {
    this(0, 0, 0, new Array[Byte](0))

    imageId = ByteBuffer.wrap(buffer.take(4)).getInt
    id      = ByteBuffer.wrap(buffer.drop(4).take(4)).getInt
    maxId   = ByteBuffer.wrap(buffer.drop(8).take(4)).getInt
    data    = buffer.drop(12).take(length - 12)//buffer.length - 12)
  }

  def toBytesArray = {
    val bb = ByteBuffer.allocate(3*4 + data.length)
    bb.putInt(imageId)
    bb.putInt(id)
    bb.putInt(maxId)
    bb.put(data)
    bb.array
  }

  override def toString = s"image: $imageId, id: $id, max: $maxId "
}


