package desktop.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.cell.{ComboBoxTableCell, TextFieldTableCell}
import javafx.scene.control.{SelectionMode, SplitPane, TableColumn, TableView}
import javafx.scene.layout.{AnchorPane, HBox, VBox}
import javafx.stage.{FileChooser, Stage}

import desktop.model.UiPoint
import desktop.utils.FileChoosers
import desktop.utils.converters._
import model.{PointType, TrustLevel}
import repository.{GPXRepository, PointTypeRepository, TrustLevelRepository}

import scala.concurrent.ExecutionContext.Implicits.global

class ImportController {
  @FXML private var mainPane: AnchorPane = _
  @FXML private var tableGpx: TableView[UiPoint] = _
  @FXML private var tableMatch: TableView[UiPoint] = _
  @FXML private var tableGpxColumnId: TableColumn[UiPoint, Option[Int]] = _
  @FXML private var tableGpxColumnName: TableColumn[UiPoint, String] = _
  @FXML private var tableGpxColumnLat: TableColumn[UiPoint, BigDecimal] = _
  @FXML private var tableGpxColumnLon: TableColumn[UiPoint, BigDecimal] = _
  @FXML private var tableGpxColumnAltitude: TableColumn[UiPoint, Option[BigDecimal]] = _
  @FXML private var tableGpxColumnPrecision: TableColumn[UiPoint, Option[BigDecimal]] = _
  @FXML private var tableGpxColumnDescription: TableColumn[UiPoint, Option[String]] = _
  @FXML private var tableGpxColumnPointtype: TableColumn[UiPoint, PointType] = _
  @FXML private var tableGpxColumnTrustlevel: TableColumn[UiPoint, TrustLevel] = _
  @FXML private var tableGpxColumnDataid: TableColumn[UiPoint, Option[Int]] = _

  def stage: Stage = mainPane.getScene.getWindow.asInstanceOf[Stage]

  def btnImportClick(): Unit = {
    FileChoosers.selectGpxFile(stage) match {
      case Some(f) =>
        PointTypeRepository().all().foreach(x => {
          tableGpxColumnPointtype.setCellFactory(ComboBoxTableCell.forTableColumn(new IdNameStringConverter[PointType], x: _*))
        })

        TrustLevelRepository().all().foreach(x => {
          tableGpxColumnTrustlevel.setCellFactory(ComboBoxTableCell.forTableColumn(new IdNameStringConverter[TrustLevel], x: _*))
        })
        PointTypeRepository().getById(0)
          .flatMap(t => TrustLevelRepository().getById(0).map(t -> _))
          .map{case (Some(t), Some(l)) =>
            val value = FXCollections.observableArrayList(GPXRepository(f).readWpt().map(p => UiPoint(p, t, l)): _*)
            tableGpx.setItems(value)
          }
      case None =>

    }
  }

  def btnCloseClick(): Unit = {
    stage.close()
  }

  @FXML
  protected def initialize(): Unit = {
    tableGpx.getSelectionModel.setSelectionMode(SelectionMode.MULTIPLE)

    tableGpxColumnId.setCellValueFactory(x => x.getValue.idProperty)
    tableGpxColumnId.setCellFactory(TextFieldTableCell.forTableColumn(new OptionIntConverter))

    tableGpxColumnName.setCellValueFactory(x => x.getValue.nameProperty)
    tableGpxColumnName.setCellFactory(TextFieldTableCell.forTableColumn())
    tableGpxColumnName.setOnEditCommit(x => x.getTableView.getItems.get(x.getTablePosition.getRow).nameProperty.set(x.getNewValue))

    tableGpxColumnLat.setCellValueFactory(x => x.getValue.latProperty)
    tableGpxColumnLat.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalConverter))

    tableGpxColumnLon.setCellValueFactory(x => x.getValue.lonProperty)
    tableGpxColumnLon.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalConverter))

    tableGpxColumnAltitude.setCellValueFactory(x => x.getValue.altitudeProperty)
    tableGpxColumnAltitude.setCellFactory(TextFieldTableCell.forTableColumn(new OptionBigDecimalConverter))

    tableGpxColumnPrecision.setCellValueFactory(x => x.getValue.precisionProperty)
    tableGpxColumnPrecision.setCellFactory(TextFieldTableCell.forTableColumn(new OptionBigDecimalConverter))

    tableGpxColumnDescription.setCellValueFactory(x => x.getValue.descriptionProperty)
    tableGpxColumnDescription.setCellFactory(TextFieldTableCell.forTableColumn(new OptionStringConverter))

    tableGpxColumnPointtype.setCellValueFactory(x => x.getValue.pointtypeProperty)
    tableGpxColumnTrustlevel.setCellValueFactory(x => x.getValue.trustlevelProperty)

    tableGpxColumnDataid.setCellValueFactory(x => x.getValue.dataidProperty)
    tableGpxColumnDataid.setCellFactory(TextFieldTableCell.forTableColumn(new OptionIntConverter))

  }
}
