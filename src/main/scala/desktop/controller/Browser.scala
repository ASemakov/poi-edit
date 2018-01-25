package desktop.controller
import scala.concurrent.ExecutionContext.Implicits.global
import javafx.collections.FXCollections
import javafx.fxml.FXML

import desktop.controller.controls.{PointTable, YandexMap}
import desktop.model.UiPoint
import repository.{PointRepository, PointTypeRepository, TrustLevelRepository}

class Browser {
  @FXML protected var webView: YandexMap = _
  @FXML protected var tableView: PointTable[UiPoint] = _

  @FXML
  protected def initialize(): Unit = {
    PointRepository().allJoined().foreach(x => {
      val uiPoints = x.map { case (p, t, l) => UiPoint(p, t, l) }
      val data = FXCollections.observableArrayList(uiPoints: _*)
      tableView.setItems(data)
      if (!data.isEmpty) {
        tableView.getSelectionModel.select(0)
      }
    })

    PointTypeRepository().all().foreach(x => {
      tableView.setPointTypes(x)
    })

    TrustLevelRepository().all().foreach(x => {
      tableView.setTrustLevels(x)
    })
    tableView.getSelectionModel.selectedItemProperty().addListener((obs, oldSelection, newSelection) => webView.addPoint(newSelection))
  }
}
