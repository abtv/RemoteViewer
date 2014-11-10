package application

import java.awt.image.BufferedImage

import org.joda.time.DateTime

class Image(var imageId: Int, var loadTime: Option[DateTime], var image: BufferedImage)
