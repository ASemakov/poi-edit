package desktop.controller

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.stage.Stage
import desktop.controller.controls.PointTable
import desktop.model.UiPoint
import desktop.utils._
import repository.{ExportRepository, GPXRepository, PointRepository, PointSourceRepository, PointTypeRepository, TrustLevelRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


class MainController() {
  @FXML private var menuBar: MenuBar = _
  @FXML private var tableView: PointTable[UiPoint] = _

  def stage: Stage = menuBar.getScene.getWindow.asInstanceOf[Stage]

  protected def onMenuImportGpxClick(): Unit = {
    val ctrl = WindowUtils.createStage[ImportController](stage, getClass.getResource("/fxml/scenes/import.fxml"))
    ctrl.onMenuImportGpxClick()
  }

  protected def onMenuExportGpxClick(): Unit = {
    FileChoosers.selectExportGpxFile(stage) match {
      case Some(file) =>
        PointRepository().allJoined().foreach(s=>{
          GPXRepository(file).writeWpt(s.map(_._1).filter(x=>x.trustlevelid >= 0))
          Platform.runLater(()=>{
            WindowUtils.information("GPX file was exported.")
          })
        })
      case None =>
    }
  }
  protected def onMenuCloseClick(): Unit = {
    stage.close()
  }

  def btnBackupClick(): Unit = {
    FileChoosers.selectBackupFile(stage) match {
      case Some(file) => ExportRepository(file).backup()
      case None =>
    }
  }

  def btnRestoreClick(): Unit = {
    if (WindowUtils.confirmation("Are you sure that you want to restore data from file?")) {
      FileChoosers.selectRestoreFile(stage) match {
        case Some(file) => ExportRepository(file).restore().andThen {
          case Success(r) =>
            Platform.runLater(() => {
              WindowUtils.information("Import was successful.")
              btnReloadClick()
            })
          case Failure(ex) =>
            Platform.runLater(() => {
              WindowUtils.error(s"Import failed: $ex")
            })
        }
        case None =>
      }
    }
  }

  protected def btnReloadClick(): Unit = {
    PointRepository().allJoined().foreach(x => {
      val data = FXCollections.observableArrayList(x.map { case (p, t, l, s) => UiPoint(p, t, l, s) }: _*)
      tableView.setItems(data)
      if (!data.isEmpty) {
        tableView.getSelectionModel.select(0)
      }
    })

    tableView.initDropdowns()
  }

  protected def btnSaveClick(): Unit = {
    val changedPoints = tableView.getItems.filtered(p => p.isChanged || p.isNew)
    val points = changedPoints.toArray(new Array[UiPoint](changedPoints.size())).toList
    if (points.isEmpty) {
      return
    }
    if (WindowUtils.confirmation(s"Are you sure to save ${points.length} item(s) (${points.count(_.idProperty.get().isEmpty)} new)")) {
      Future.sequence(points.map(u => PointRepository().save(u.asPoint)))
        .andThen { case Success(_) => btnReloadClick() }
    }
  }

  protected def btnDeleteClick(): Unit = {
    val items = tableView.getSelectionModel.getSelectedItems
    val itemsList = items.toArray(new Array[UiPoint](items.size())).toList
    if (itemsList.isEmpty) {
      return
    }
    if (WindowUtils.confirmation(s"Are you sure to delete ${itemsList.length} item(s): ${itemsList.flatMap(_.idProperty.get()).map(_.toString).mkString(", ")}")) {
      Future.sequence(itemsList.map(u => PointRepository().delete(u.asPoint)))
        .andThen { case Success(_) => btnReloadClick() }
    }
  }

  protected def btnAddClick(): Unit = {
    PointTypeRepository().getById(1).flatMap(
      pt => TrustLevelRepository().getById(1).map(pt -> _)
    ).foreach { case (t, l) =>
      tableView.getItems.add(UiPoint(None, "name", 0, 0, None, None, None, t.get, l.get, None, None)) // TODO: AS Source
    }
  }

  @FXML
  protected def initialize(): Unit = {
    btnReloadClick()
  }
}
