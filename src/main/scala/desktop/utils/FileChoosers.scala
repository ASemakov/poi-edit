package desktop.utils

import java.io.File
import javafx.stage.{FileChooser, Stage}

object FileChoosers{
  private val gpxFilter = new FileChooser.ExtensionFilter("GPX files (*.gpx)", "*.gpx")
  private val jsonFilter = new FileChooser.ExtensionFilter("Backup json files (*.json)", "*.json")

  private def getFileChooser(title: String, filter: FileChooser.ExtensionFilter): FileChooser = {
    val fc = new FileChooser()
    fc.setTitle(title)
    fc.getExtensionFilters.add(filter)
    fc
  }

  def selectGpxFile(stage: Stage): Option[File] = {
    val fc = getFileChooser("Import GPX file", gpxFilter)
    Option(fc.showOpenDialog(stage))
  }

  def selectBackupFile(stage: Stage): Option[File] = {
    val fc = getFileChooser("Backup file", jsonFilter)
    Option(fc.showSaveDialog(stage))
      .map(file => if (file.getName.endsWith(".json")) file else new File(file.getAbsolutePath + ".json"))
  }
  def selectRestoreFile(stage: Stage): Option[File] = {
    val fc = getFileChooser("Backup file", jsonFilter)
    Option(fc.showOpenDialog(stage))
  }
}
