package desktop.controller.controls

import java.io.IOException
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{TableColumn, TableView}
import javafx.scene.control.cell.{ComboBoxTableCell, TextFieldTableCell}

import desktop.model.UiKadastr
import desktop.utils.converters._
import model.{Category, Region}

class KadastrTable[T <: UiKadastr] extends TableView[T] {
  @FXML private var tableColumnId: TableColumn[T, Option[Int]] = _
  @FXML private var tableColumnNum: TableColumn[T, Option[String]] = _
  @FXML private var tableColumnNum2: TableColumn[T, Option[String]] = _
  @FXML private var tableColumnName: TableColumn[T, Option[String]] = _
  @FXML private var tableColumnLength: TableColumn[T, Option[Double]] = _
  @FXML private var tableColumnAmplitude: TableColumn[T, Option[Double]] = _
  @FXML private var tableColumnVolume: TableColumn[T, Option[Double]] = _
  @FXML private var tableColumnRegion: TableColumn[T, Option[Region]] = _
  @FXML private var tableColumnCategory: TableColumn[T, Option[Category]] = _
  @FXML private var tableColumnComment: TableColumn[T, Option[String]] = _

  private val fxmlLoader = new FXMLLoader(getClass.getResource("/fxml/controls/KadastrTable.fxml"))
  fxmlLoader.setRoot(this)
  fxmlLoader.setController(this)

  try {
    fxmlLoader.load()
  } catch {
    case exception: IOException => throw new RuntimeException(exception)
  }
  @FXML
  def initialize() = {
    tableColumnId.setCellValueFactory(_.getValue.idProperty)
    tableColumnId.setCellFactory(TextFieldTableCell.forTableColumn(new OptionIntConverter))

    tableColumnNum.setCellValueFactory(_.getValue.numProperty)
    tableColumnNum.setCellFactory(TextFieldTableCell.forTableColumn(new OptionStringConverter))

    tableColumnNum2.setCellValueFactory(_.getValue.num2Property)
    tableColumnNum2.setCellFactory(TextFieldTableCell.forTableColumn(new OptionStringConverter))

    tableColumnName.setCellValueFactory(_.getValue.nameProperty)
    tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn(new OptionStringConverter))

    tableColumnLength.setCellValueFactory(_.getValue.lProperty)
    tableColumnLength.setCellFactory(TextFieldTableCell.forTableColumn(new OptionDoubleConverter))

    tableColumnAmplitude.setCellValueFactory(_.getValue.aProperty)
    tableColumnAmplitude.setCellFactory(TextFieldTableCell.forTableColumn(new OptionDoubleConverter))

    tableColumnVolume.setCellValueFactory(_.getValue.vProperty)
    tableColumnVolume.setCellFactory(TextFieldTableCell.forTableColumn(new OptionDoubleConverter))

    tableColumnRegion.setCellValueFactory(_.getValue.regionProperty)
    tableColumnCategory.setCellValueFactory(_.getValue.categoryProperty)

    tableColumnComment.setCellValueFactory(_.getValue.commentProperty)
    tableColumnComment.setCellFactory(TextFieldTableCell.forTableColumn(new OptionStringConverter))
  }

  def setRegions(ts: Seq[Region]) = {
    tableColumnRegion.setCellFactory(ComboBoxTableCell.forTableColumn(
      new OptionIdNameStringConverter[Region], None::ts.toList.map(Option(_)): _*
    ))
  }

  def setCategories(ts: Seq[Category]) = {
    tableColumnCategory.setCellFactory(ComboBoxTableCell.forTableColumn(
      new OptionIdNameStringConverter[Category], None::ts.toList.map(Option(_)): _*
    ))
  }

}
