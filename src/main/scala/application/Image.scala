package application

import java.awt.image.BufferedImage

import org.joda.time.DateTime

class Image(val loadTime: Option[DateTime], val image: BufferedImage)
