package desktop.controller.controls

import java.io.IOException
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.cell.{ComboBoxTableCell, TextFieldTableCell}
import javafx.scene.control.{SelectionMode, TableColumn, TableView}

import desktop.model.UiPoint
import desktop.utils.OptionComparator
import desktop.utils.converters._
import model.{PointType, TrustLevel}

class PointTable[T <: UiPoint] extends TableView[T] {
  @FXML private var tableColumnId: TableColumn[T, Option[Int]] = _
  @FXML private var tableColumnName: TableColumn[T, String] = _
  @FXML private var tableColumnLat: TableColumn[T, BigDecimal] = _
  @FXML private var tableColumnLon: TableColumn[T, BigDecimal] = _
  @FXML private var tableColumnAltitude: TableColumn[T, Option[BigDecimal]] = _
  @FXML private var tableColumnPrecision: TableColumn[T, Option[BigDecimal]] = _
  @FXML private var tableColumnDescription: TableColumn[T, Option[String]] = _
  @FXML private var tableColumnPointtype: TableColumn[T, PointType] = _
  @FXML private var tableColumnTrustlevel: TableColumn[T, TrustLevel] = _
  @FXML private var tableColumnDataid: TableColumn[T, Option[Int]] = _

  private val fxmlLoader = new FXMLLoader(getClass.getResource("/fxml/controls/PointTable.fxml"))
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
    getSelectionModel.setSelectionMode(SelectionMode.MULTIPLE)

    tableColumnId.setCellValueFactory(_.getValue.idProperty)
    tableColumnId.setCellFactory(TextFieldTableCell.forTableColumn(new OptionIntConverter))
    tableColumnId.setComparator(new OptionComparator[Int])

    tableColumnName.setCellValueFactory(_.getValue.nameProperty)
    tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn())

    tableColumnLat.setCellValueFactory(_.getValue.latProperty)
    tableColumnLat.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalConverter))

    tableColumnLon.setCellValueFactory(_.getValue.lonProperty)
    tableColumnLon.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalConverter))

    tableColumnAltitude.setCellValueFactory(_.getValue.altitudeProperty)
    tableColumnAltitude.setCellFactory(TextFieldTableCell.forTableColumn(new OptionBigDecimalConverter))
    tableColumnAltitude.setComparator(new OptionComparator[BigDecimal])

    tableColumnPrecision.setCellValueFactory(_.getValue.precisionProperty)
    tableColumnPrecision.setCellFactory(TextFieldTableCell.forTableColumn(new OptionBigDecimalConverter))
    tableColumnPrecision.setComparator(new OptionComparator[BigDecimal])

    tableColumnDescription.setCellValueFactory(_.getValue.descriptionProperty)
    tableColumnDescription.setCellFactory(TextFieldTableCell.forTableColumn(new OptionStringConverter))
    tableColumnDescription.setComparator(new OptionComparator[String])

    tableColumnPointtype.setCellValueFactory(_.getValue.pointtypeProperty)
    tableColumnTrustlevel.setCellValueFactory(_.getValue.trustlevelProperty)

    tableColumnDataid.setCellValueFactory(_.getValue.dataidProperty)
    tableColumnDataid.setCellFactory(TextFieldTableCell.forTableColumn(new OptionIntConverter))
    tableColumnDataid.setComparator(new OptionComparator[Int])
  }
}
