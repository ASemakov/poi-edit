package desktop.controller.controls

import javafx.fxml.FXML
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.control.{SelectionMode, TableColumn}

import desktop.model.UiPointDistantiated
import desktop.utils.converters.{BigDecimalConverter, DoubleConverter}

class PointDistantiatedTable extends PointTable[UiPointDistantiated] {
  private var tableColumnDistance: TableColumn[UiPointDistantiated, Double] = _

  @FXML
  override def initialize() = {
    tableColumnDistance = new TableColumn[UiPointDistantiated, Double]("distance")
    super.initialize()
    getSelectionModel.setSelectionMode(SelectionMode.SINGLE)
    setEditable(false)

    tableColumnDistance.setCellValueFactory(x => x.getValue.distanceProperty)
    tableColumnDistance.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleConverter))

    getColumns.add(0, tableColumnDistance)
  }
}
