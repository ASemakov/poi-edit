package desktop.controller

import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.scene.control.cell.{ComboBoxTableCell, TextFieldTableCell}
import javafx.scene.control.{MenuBar, TableColumn, TableView}
import javafx.stage.Stage

import desktop.model.UiPoint
import desktop.utils._
import model.{PointType, TrustLevel}
import repository.{PointRepository, PointTypeRepository, TrustLevelRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success


class MainController() {
  @FXML private var menuBar: MenuBar = _
  @FXML private var tableView: TableView[UiPoint] = _
  @FXML private var tableColumnId: TableColumn[UiPoint, Option[Int]] = _
  @FXML private var tableColumnName: TableColumn[UiPoint, String] = _
  @FXML private var tableColumnLat: TableColumn[UiPoint, BigDecimal] = _
  @FXML private var tableColumnLon: TableColumn[UiPoint, BigDecimal] = _
  @FXML private var tableColumnAltitude: TableColumn[UiPoint, Option[BigDecimal]] = _
  @FXML private var tableColumnPrecision: TableColumn[UiPoint, Option[BigDecimal]] = _
  @FXML private var tableColumnDescription: TableColumn[UiPoint, Option[String]] = _
  @FXML private var tableColumnPointtype: TableColumn[UiPoint, PointType] = _
  @FXML private var tableColumnTrustlevel: TableColumn[UiPoint, TrustLevel] = _
  @FXML private var tableColumnDataid: TableColumn[UiPoint, Option[Int]] = _

  protected def onMenuCloseClick(): Unit = {
    val stage = menuBar.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }

  protected def btnReloadClick(): Unit = {
    PointRepository().allJoined().foreach(x => {
      val data = FXCollections.observableArrayList(x.map { case (p, t, l) => UiPoint(p, t, l) }: _*)
      tableView.setItems(data)
    })

    PointTypeRepository().all().foreach(x => {
      tableColumnPointtype.setCellFactory(ComboBoxTableCell.forTableColumn(new IdNameStringConverter[PointType], x: _*))
      // tableColumnPointtype.setOnEditCommit(x => x.getTableView.getItems.get(x.getTablePosition.getRow).pointtypeProperty.set(x.getNewValue))
    })

    TrustLevelRepository().all().foreach(x => {
      tableColumnTrustlevel.setCellFactory(ComboBoxTableCell.forTableColumn(new IdNameStringConverter[TrustLevel], x: _*))
      // tableColumnTrustlevel.setOnEditCommit(x => x.getTableView.getItems.get(x.getTablePosition.getRow).trustlevelProperty.set(x.getNewValue))
    })
  }

  protected def btnSaveClick(): Unit = {
    val changedPoints = tableView.getItems.filtered(p => p.isChanged || p.isNew)
    val points: Array[UiPoint] = changedPoints.toArray(new Array[UiPoint](changedPoints.size()))
    Future.sequence(points.toList.map(u => PointRepository().save(u.asPoint)))
      .andThen{case Success(v) => btnReloadClick()}
  }

  protected def btnDeleteClick(): Unit = {
    val items = tableView.getSelectionModel.getSelectedItems
    Future.sequence(items.toArray(new Array[UiPoint](items.size())).toList.map(u => PointRepository().delete(u.asPoint)))
      .andThen{case Success(v) => btnReloadClick()}
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

    tableColumnId.setCellValueFactory(x => x.getValue.idProperty)
    tableColumnId.setCellFactory(TextFieldTableCell.forTableColumn(new OptionIntConverter))

    tableColumnName.setCellValueFactory(x => x.getValue.nameProperty)
    tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn())
    tableColumnName.setOnEditCommit(x => x.getTableView.getItems.get(x.getTablePosition.getRow).nameProperty.set(x.getNewValue))

    tableColumnLat.setCellValueFactory(x => x.getValue.latProperty)
    tableColumnLat.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalConverter))

    tableColumnLon.setCellValueFactory(x => x.getValue.lonProperty)
    tableColumnLon.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalConverter))

    tableColumnAltitude.setCellValueFactory(x => x.getValue.altitudeProperty)
    tableColumnAltitude.setCellFactory(TextFieldTableCell.forTableColumn(new OptionBigDecimalConverter))

    tableColumnPrecision.setCellValueFactory(x => x.getValue.precisionProperty)
    tableColumnPrecision.setCellFactory(TextFieldTableCell.forTableColumn(new OptionBigDecimalConverter))

    tableColumnDescription.setCellValueFactory(x => x.getValue.descriptionProperty)
    tableColumnDescription.setCellFactory(TextFieldTableCell.forTableColumn(new OptionStringConverter))

    tableColumnPointtype.setCellValueFactory(x => x.getValue.pointtypeProperty)
    tableColumnTrustlevel.setCellValueFactory(x => x.getValue.trustlevelProperty)

    tableColumnDataid.setCellValueFactory(x => x.getValue.dataidProperty)
    tableColumnDataid.setCellFactory(TextFieldTableCell.forTableColumn(new OptionIntConverter))

    btnReloadClick()

  }
}
