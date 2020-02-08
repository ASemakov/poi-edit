package desktop.utils

import java.io.File
import javafx.stage.{FileChooser, Stage}

object FileChoosers{
  private val gpxFilter = new FileChooser.ExtensionFilter("GPX files (*.gpx)", "*.gpx")
  private val jsonFilter = new FileChooser.ExtensionFilter("Backup json files (*.json)", "*.json")
  private val jsonGzFilter = new FileChooser.ExtensionFilter("Backup json files (*.json.gz)", "*.json.gz")

  private def getFileChooser(title: String, filter: FileChooser.ExtensionFilter): FileChooser = getFileChooser(title, Seq(filter))

    private def getFileChooser(title: String, filter: Seq[FileChooser.ExtensionFilter]): FileChooser = {
    val fc = new FileChooser()
    fc.setTitle(title)
    fc.getExtensionFilters.addAll(filter: _*)
    fc
  }

  def selectImportGpxFile(stage: Stage): Option[File] = {
    val fc = getFileChooser("Import GPX file", gpxFilter)
    Option(fc.showOpenDialog(stage))
  }

  def selectExportGpxFile(stage: Stage): Option[File] = {
    val fc = getFileChooser("Export GPX file", gpxFilter)
    Option(fc.showSaveDialog(stage))
      .map(file => {
        val ext = fc.getSelectedExtensionFilter.getExtensions.get(0).substring(1)
        if (file.getName.endsWith(ext)) file else new File(file.getAbsolutePath + ext)
      })
  }

  def selectBackupFile(stage: Stage): Option[File] = {
    val fc = getFileChooser("Backup file", Seq(jsonGzFilter, jsonFilter))
    Option(fc.showSaveDialog(stage))
      .map(file => {
        val ext = fc.getSelectedExtensionFilter.getExtensions.get(0).substring(1)
        if (file.getName.endsWith(ext)) file else new File(file.getAbsolutePath + ext)
      })
  }
  def selectRestoreFile(stage: Stage): Option[File] = {
    val fc = getFileChooser("Backup file", Seq(jsonGzFilter, jsonFilter))
    Option(fc.showOpenDialog(stage))
  }
}
