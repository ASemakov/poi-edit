package desktop.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, ButtonType}
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

import desktop.controller.controls.{PointDistantiatedTable, PointTable}
import desktop.model.{UiPoint, UiPointDistantiated}
import desktop.utils.FileChoosers
import repository.{GPXRepository, PointRepository, PointTypeRepository, TrustLevelRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class ImportController {
  @FXML private var mainPane: AnchorPane = _
  @FXML private var tableGpx: PointTable[UiPoint] = _
  @FXML private var tableMatch: PointDistantiatedTable = _


  def stage: Stage = mainPane.getScene.getWindow.asInstanceOf[Stage]

  def onMenuImportGpxClick(): Unit = {
    FileChoosers.selectGpxFile(stage) match {
      case Some(f) =>
        PointTypeRepository().all().foreach(x => {
          tableGpx.setPointTypes(x)
        })

        TrustLevelRepository().all().foreach(x => {
          tableGpx.setTrustLevels(x)
        })
        PointTypeRepository().getById(0)
          .flatMap(t => TrustLevelRepository().getById(0).map(t -> _))
          .map {
            case (Some(t), Some(l)) =>
              val value = FXCollections.observableArrayList(GPXRepository(f).readWpt().map(p => UiPoint(p, t, l)): _*)
              tableGpx.tableView.setItems(value)
            case _ => println("No reference items were found")
          }

      case None =>
    }
  }

  def onMenuCloseClick(): Unit = {
    stage.close()
  }

  def onMenuSaveClick(): Unit = {

    val fResult = Future.sequence(
      tableGpx.getItems
        .toArray(new Array[UiPoint](tableGpx.getItems.size()))
        .toList
        .map(x => PointRepository().save(x.asPoint))
    )
    val items = Await.result(fResult, Duration.Inf)
    tableGpx.getItems.clear()
    val alert = new Alert(AlertType.INFORMATION, s"${items.size} items were saved.", ButtonType.OK)
    alert.showAndWait()
  }

  def onMenuAssociateClick(): Unit = {
    if (Option(tableGpx.getSelectionModel).isEmpty || Option(tableMatch.getSelectionModel).isEmpty){
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
    if (Option(tableGpx.getSelectionModel).isEmpty){
      return
    }
    tableGpx.getSelectionModel.getSelectedItem.idProperty.set(None)
  }

  @FXML
  protected def initialize(): Unit = {
    tableGpx.getSelectionModel.selectedItemProperty().addListener((obs, oldSelection, newSelection) => {
      Option(newSelection) match {
        case Some(item) =>
          PointRepository()
            .topNByDistance(item.latProperty.get(), item.lonProperty.get(), 10)
            .foreach(s => {
              tableMatch.getItems.setAll(s.map { case (p, t, l, d) => UiPointDistantiated(p, t, l, d) }: _*)
              tableMatch.getSelectionModel.select(0)
            })
        case None =>
          tableMatch.getItems.clear()
      }
    })
  }
}
