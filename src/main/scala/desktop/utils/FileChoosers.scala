package desktop.utils

import java.io.File
import javafx.stage.{FileChooser, Stage}

object FileChoosers{
  def selectGpxFile(stage: Stage): Option[File] = {
    val fc = new FileChooser()
    fc.setTitle("Import GPX file")
    fc.getExtensionFilters.add(new FileChooser.ExtensionFilter("GPX files (*.gpx)", "*.gpx"))
    Option(fc.showOpenDialog(stage))
  }
}
