package desktop.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.cell.{PropertyValueFactory, TextFieldTableCell}
import javafx.scene.control.{MenuBar, MenuItem, TableColumn, TableView}
import javafx.stage.Stage

import desktop.model.UiPoint
import repository.PointRepository

import scala.concurrent.ExecutionContext.Implicits.global


class MainController(){
  @FXML private var menuItemClose: MenuItem = _
  @FXML private var menuBar: MenuBar = _
  @FXML private var tableView: TableView[UiPoint] = _
  @FXML private var tableColumnId: TableColumn[UiPoint, Option[Int]] = _
  @FXML private var tableColumnName: TableColumn[UiPoint, String] = _
  @FXML private var tableColumnLat: TableColumn[UiPoint, BigDecimal] = _
  @FXML private var tableColumnLon: TableColumn[UiPoint, BigDecimal] = _
  @FXML private var tableColumnAltitude: TableColumn[UiPoint, Option[BigDecimal]] = _
  @FXML private var tableColumnPrecision: TableColumn[UiPoint, Option[BigDecimal]] = _
  @FXML private var tableColumnDescription: TableColumn[UiPoint, Option[String]] = _
  @FXML private var tableColumnPointtypeid: TableColumn[UiPoint, Int] = _
  @FXML private var tableColumnTrustlevelid: TableColumn[UiPoint, Int] = _
  @FXML private var tableColumnDataid: TableColumn[UiPoint, Option[Int]] = _


  @FXML
  protected def initialize(): Unit = {
    PointRepository().all().foreach(x => {
      val data = FXCollections.observableArrayList(x.map(UiPoint.apply): _*)
      tableView.setItems(data)
    })
    tableView.setEditable(true)

    tableColumnId.setCellValueFactory(x => x.getValue.idProperty)
    tableColumnName.setCellValueFactory(x => x.getValue.nameProperty)
    tableColumnLat.setCellValueFactory(x => x.getValue.latProperty)
    tableColumnLon.setCellValueFactory(x => x.getValue.lonProperty)
    tableColumnAltitude.setCellValueFactory(x => x.getValue.altitudeProperty)
    tableColumnPrecision.setCellValueFactory(x => x.getValue.precisionProperty)
    tableColumnDescription.setCellValueFactory(x => x.getValue.descriptionProperty)
    tableColumnPointtypeid.setCellValueFactory(new PropertyValueFactory[UiPoint, Int]("pointtypeid"))
    tableColumnTrustlevelid.setCellValueFactory(new PropertyValueFactory[UiPoint, Int]("trustlevelid"))
    tableColumnDataid.setCellValueFactory(x => x.getValue.dataidProperty)

    tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn())
    tableColumnName.setOnEditCommit(x => x.getTableView.getItems.get(x.getTablePosition.getRow).nameProperty.set(x.getNewValue))
  }

  def onMenuCloseClick(): Unit ={
    val stage = menuBar.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }
}
