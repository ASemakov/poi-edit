package desktop.controller

import javafx.fxml.FXML
import javafx.scene.web.{WebEngine, WebErrorEvent, WebEvent, WebView}
import desktop.controller.controls.PointTable
import desktop.model.UiPoint
import javafx.event.EventHandler
import javafx.scene.input.{DragEvent, MouseEvent}

class Browser {
  @FXML protected var webView: WebView = _
  @FXML protected var tableView: PointTable[UiPoint] = _
  def engine: WebEngine = webView.getEngine

  @FXML
  protected def initialize(): Unit = {
    //engine.load(getClass.getResource("/html/map.html").toExternalForm)
    engine.load(getClass.getResource("/html/yandex.map.html").toExternalForm)
    webView.getEngine.executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}")
    webView.getEngine.setOnAlert((t: WebEvent[String]) => System.out.println(t.toString))
    webView.getEngine.setOnError(new EventHandler[WebErrorEvent] {
      override def handle(t: WebErrorEvent): Unit = System.out.println(t.toString)
    })
  }
}
