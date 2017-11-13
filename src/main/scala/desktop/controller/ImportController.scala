package desktop.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

import desktop.model.UiPoint
import desktop.utils.FileChoosers
import repository.{GPXRepository, PointTypeRepository, TrustLevelRepository}

import scala.concurrent.ExecutionContext.Implicits.global

class ImportController {
  @FXML private var mainPane: AnchorPane = _
  @FXML private var tblGpx: PointTable = _

  def stage: Stage = mainPane.getScene.getWindow.asInstanceOf[Stage]

  def btnImportClick(): Unit = {
    FileChoosers.selectGpxFile(stage) match {
      case Some(f) =>
        PointTypeRepository().all().foreach(x => {
          tblGpx.setPointTypes(x)
        })

        TrustLevelRepository().all().foreach(x => {
          tblGpx.setTrustLevels(x)
        })
        PointTypeRepository().getById(0)
          .flatMap(t => TrustLevelRepository().getById(0).map(t -> _))
          .map{case (Some(t), Some(l)) =>
            val value = FXCollections.observableArrayList(GPXRepository(f).readWpt().map(p => UiPoint(p, t, l)): _*)
            tblGpx.tableView.setItems(value)
          }
      case None =>

    }
  }

  def btnCloseClick(): Unit = {
    stage.close()
  }

  @FXML
  protected def initialize(): Unit = {
  }
}
