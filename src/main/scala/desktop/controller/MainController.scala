package desktop.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._
import javafx.stage.Stage

import desktop.model.UiPoint
import desktop.utils._
import repository.{GPXRepository, PointRepository, PointTypeRepository, TrustLevelRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success




class MainController() {
  @FXML private var menuBar: MenuBar = _
  @FXML private var tableView: PointTable = _

  def stage: Stage = menuBar.getScene.getWindow.asInstanceOf[Stage]

  protected def onMenuImportGpxClick(): Unit = {
    FileChoosers.selectGpxFile(stage) match {
      case Some(f) =>
        val points = GPXRepository(f).readWpt()
        println(s"imported ${points.size} points")
      case None => println("no file specified")
    }
  }

  protected def onMenuCloseClick(): Unit = {
    stage.close()
  }

  protected def btnReloadClick(): Unit = {
    PointRepository().allJoined().foreach(x => {
      val data = FXCollections.observableArrayList(x.map { case (p, t, l) => UiPoint(p, t, l) }: _*)
      tableView.setItems(data)
    })

    PointTypeRepository().all().foreach(x => {
      tableView.setPointTypes(x)
    })

    TrustLevelRepository().all().foreach(x => {
      tableView.setTrustLevels(x)
    })
  }

  protected def btnSaveClick(): Unit = {
    val changedPoints = tableView.getItems.filtered(p => p.isChanged || p.isNew)
    val points = changedPoints.toArray(new Array[UiPoint](changedPoints.size())).toList
    if (points.isEmpty){
      return
    }
    val alert = new Alert(AlertType.CONFIRMATION,
      s"Are you sure to save ${points.length} item(s) (${points.count(_.idProperty.get().isEmpty)} new)",
      ButtonType.OK, ButtonType.CANCEL)
    alert.showAndWait()
    if (alert.getResult == ButtonType.OK) {
      Future.sequence(points.map(u => PointRepository().save(u.asPoint)))
        .andThen { case Success(_) => btnReloadClick() }
    }
  }

  protected def btnDeleteClick(): Unit = {
    val items = tableView.getSelectionModel.getSelectedItems
    val itemsList = items.toArray(new Array[UiPoint](items.size())).toList
    if (itemsList.isEmpty){
      return
    }

    val alert = new Alert(
      AlertType.CONFIRMATION,
      s"Are you sure to delete ${itemsList.length} item(s): ${itemsList.flatMap(_.idProperty.get()).map(_.toString).mkString(", ")}",
      ButtonType.OK, ButtonType.CANCEL)
    alert.showAndWait()
    if (alert.getResult == ButtonType.OK) {
      Future.sequence(itemsList.map(u => PointRepository().delete(u.asPoint)))
        .andThen { case Success(_) => btnReloadClick() }
    }
  }

  protected def btnAddClick(): Unit = {
    PointTypeRepository().getById(1).flatMap(
      pt => TrustLevelRepository().getById(1).map(pt -> _)
    ).foreach{ case (t, l) =>
      tableView.getItems.add(UiPoint(None, "name", 0, 0, None, None, None, t.get, l.get, None))
    }
  }

  @FXML
  protected def initialize(): Unit = {
    btnReloadClick()
  }
}
