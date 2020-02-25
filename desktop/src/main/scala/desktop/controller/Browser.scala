package desktop.controller

import javafx.fxml.FXML
import javafx.scene.web.{WebEngine, WebView}

import desktop.controller.controls.PointTable
import desktop.model.UiPoint

class Browser {
  @FXML protected var webView: WebView = _
  @FXML protected var tableView: PointTable[UiPoint] = _
  def engine: WebEngine = webView.getEngine

  @FXML
  protected def initialize(): Unit = {
    engine.load(getClass.getResource("/html/map.html").toExternalForm)
  }
}
