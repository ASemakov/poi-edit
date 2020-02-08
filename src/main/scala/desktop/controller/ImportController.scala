package desktop.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import desktop.controller.controls.{PointDistantiatedTable, PointTable}
import desktop.model.{UiPoint, UiPointDistantiated}
import desktop.utils.{FileChoosers, WindowUtils}
import javafx.application.Platform
import repository.{GPXRepository, PointRepository, PointSourceRepository, PointTypeRepository, TrustLevelRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class ImportController {
  @FXML private var mainPane: AnchorPane = _
  @FXML private var tableGpx: PointTable[UiPoint] = _
  @FXML private var tableMatch: PointDistantiatedTable = _


  def stage: Stage = mainPane.getScene.getWindow.asInstanceOf[Stage]

  def onMenuImportGpxClick(): Unit = {
    FileChoosers.selectImportGpxFile(stage) match {
      case Some(f) =>
        tableGpx.initDropdowns()
        PointTypeRepository().getById(0)
          .flatMap(t => TrustLevelRepository().getById(0).map(t -> _))
          .map {
            case (Some(t), Some(l)) =>
              val value = FXCollections.observableArrayList(GPXRepository(f).readWpt().map(p => UiPoint(p, t, l, None)): _*)
              tableGpx.setItems(value)
              if (!value.isEmpty) {
                tableGpx.getSelectionModel.select(0)
              }

            case _ => println("No reference items were found")
          }

      case None => onMenuCloseClick()
    }
  }

  def onMenuCloseClick(): Unit = {
    stage.close()
  }

  def onMenuSaveClick(): Unit = {
    val itemsList = tableGpx.getItems.toArray(new Array[UiPoint](tableGpx.getItems.size())).toList
    if (WindowUtils.confirmation(s"Are you sure to save ${itemsList.size} items (${itemsList.count(_.idProperty.get.isDefined)} new)?")) {
      val items = Await.result(
        Future.sequence(
          itemsList.map(x => PointRepository().save(x.asPoint)
          )
        ), Duration.Inf
      )
      tableGpx.getItems.clear()
      onMenuCloseClick()
    }
  }

  def onMenuAssociateClick(): Unit = {
    if (Option(tableGpx.getSelectionModel).isEmpty || Option(tableMatch.getSelectionModel).isEmpty) {
      return
    }
    val g = tableGpx.getSelectionModel.getSelectedItem
    val m = tableMatch.getSelectionModel.getSelectedItem
    g.idProperty.set(m.idProperty.get())
    g.nameProperty.set(m.nameProperty.get())
    g.descriptionProperty.set(m.descriptionProperty.get())
    g.pointtypeProperty.set(m.pointtypeProperty.get())
    g.trustlevelProperty.set(m.trustlevelProperty.get())
    g.dataidProperty.set(m.dataidProperty.get())
  }

  def onMenuDeassociateClick(): Unit = {
    if (Option(tableGpx.getSelectionModel).isEmpty) {
      return
    }
    tableGpx.getSelectionModel.getSelectedItem.idProperty.set(None)
  }

  def onMenuDeleteClick(): Unit = {
    if (Option(tableGpx.getSelectionModel.getSelectedItem).isEmpty) {
      return
    }
    val items = tableGpx.getSelectionModel.getSelectedItems.toArray(new Array[UiPoint](tableGpx.getSelectionModel.getSelectedItems.size()))
    items.foreach(tableGpx.getItems.remove)
  }

  @FXML
  protected def initialize(): Unit = {
    tableGpx.getSelectionModel.selectedItemProperty().addListener((obs, oldSelection, newSelection) => {
      Option(newSelection) match {
        case Some(item) =>
          PointRepository()
            .topNByDistance(item.latProperty.get(), item.lonProperty.get(), 10)
            .foreach(s => Platform.runLater(() => {
              tableMatch.getItems.setAll(s.map { case (p, t, l, s, d) => UiPointDistantiated(p, t, l, s, d) }: _*)
              if (s.nonEmpty) {
                tableMatch.getSelectionModel.select(0)
              }
            }))
        case None =>
          tableMatch.getItems.clear()
      }
    })
  }
}
