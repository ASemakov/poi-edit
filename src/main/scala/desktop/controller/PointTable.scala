package desktop.controller;

import java.io.IOException
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.cell.{ComboBoxTableCell, TextFieldTableCell}
import javafx.scene.control.{SelectionMode, TableColumn, TableView}
import javafx.scene.layout.AnchorPane

import desktop.model.UiPoint
import desktop.utils.converters._
import model.{PointType, TrustLevel}

class PointTable extends TableView[UiPoint] {
  @FXML def tableView: TableView[UiPoint] = this
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

  private val fxmlLoader = new FXMLLoader(getClass.getResource("/scenes/PointTable.fxml"))
  fxmlLoader.setRoot(this)
  fxmlLoader.setController(this)

  try {
    fxmlLoader.load()
  } catch {
    case exception: IOException => throw new RuntimeException(exception)
  }

  def setPointTypes(ts: Seq[PointType]) = {
    tableColumnPointtype.setCellFactory(ComboBoxTableCell.forTableColumn(new IdNameStringConverter[PointType], ts: _*))
  }

  def setTrustLevels(ts: Seq[TrustLevel]) = {
    tableColumnTrustlevel.setCellFactory(ComboBoxTableCell.forTableColumn(new IdNameStringConverter[TrustLevel], ts: _*))
  }

  @FXML
  def initialize() = {
    tableView.getSelectionModel.setSelectionMode(SelectionMode.MULTIPLE)

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
  }
}
