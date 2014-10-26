import java.io.FileWriter
import java.nio.file.{Files, Paths}

import common._

import scala.io.Source

object Settings{
  def loadSettings() = {
    val settingsFile: String = getNameSettingsFile
    if (!Files.exists(Paths.get(settingsFile)))
      saveDefaultSettingsFile(settingsFile)

    val lines = Source.fromFile(settingsFile).getLines.toArray
    val host  = readKey("host", lines)
    val port  = readKey("port", lines).toInt

    new Settings(host, port)
  }

  private def getNameSettingsFile: String = {
    val currentDirectory = new java.io.File(".").getCanonicalPath
    Paths.get(currentDirectory, "settings.txt").toString
  }

  private def saveDefaultSettingsFile(fileName: String) {
    writeKey("host", "localhost", fileName)
    writeKey("port", "8880", fileName)
  }

  private def writeKey(key: String, value: String, fileName: String) {
    using(new FileWriter(fileName, true)){ fw => fw.write(s"$key $value\n") }
  }

  private def readKey(key: String, lines: Array[String]) = {
    val line = lines.filter(x => x.startsWith(key))
    if (line.length != 1)
      throw new Exception(s"Error with key $key")
    line(0).substring(key.length).trim
  }
}

class Settings(val host: String, val port: Int)