package desktop.controller.controls

import javafx.fxml.FXML
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.control.{SelectionMode, TableColumn}

import desktop.model.UiPointDistantiated
import desktop.utils.converters.BigDecimalConverter

class PointDistantiatedTable extends PointTable[UiPointDistantiated] {
  private var tableColumnDistance: TableColumn[UiPointDistantiated, BigDecimal] = _

  @FXML
  override def initialize() = {
    tableColumnDistance = new TableColumn[UiPointDistantiated, BigDecimal]("distance")
    super.initialize()
    tableView.getSelectionModel.setSelectionMode(SelectionMode.SINGLE)
    tableView.setEditable(false)

    tableColumnDistance.setCellValueFactory(_.getValue.distance)
    tableColumnDistance.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalConverter))

    tableView.getColumns.add(0, tableColumnDistance)
  }
}
