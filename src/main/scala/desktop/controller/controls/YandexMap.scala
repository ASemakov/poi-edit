package desktop.controller.controls

import java.io.InputStreamReader
import javafx.application.Platform
import javafx.scene.layout.StackPane
import javafx.scene.web.WebView

import desktop.Cfg
import desktop.model.UiPoint
import netscape.javascript.JSObject
import sun.misc.IOUtils

import scala.io.Source
import scala.runtime.RichInt
import org.fusesource.scalate._
import org.fusesource.scalate.util.{FileResourceLoader, Resource}


trait MapContract {
  def addPoint(uiPoint: UiPoint): Unit
}


class YandexMap extends StackPane with MapContract {
  protected var webView: WebView = _
  protected def webEngine = webView.getEngine
  if (Platform.isFxApplicationThread) {
    initialize()
  } else {
    // Intended for SceneBuilder
    Platform.runLater(() => initialize())
  }

  private def initialize() = {
    webView = new WebView()
    val template = (new TemplateEngine).layout("/html/googleMap.jade")
    webEngine.loadContent(template)
    getChildren.add(webView)
    webEngine.setOnAlert(
      (event) => println(event.getData)
    )

  }

  override def addPoint(uiPoint: UiPoint): Unit = {
    if (Platform.isFxApplicationThread) {
      val jsobj = webEngine.executeScript("window").asInstanceOf[JSObject]
      jsobj.call("addPoint", uiPoint.idProperty.get.get.asInstanceOf[AnyRef], uiPoint.latProperty.get(), uiPoint.lonProperty.get(),
        uiPoint.nameProperty.get(), uiPoint.descriptionProperty.get())
    } else {
      Platform.runLater(() => addPoint(uiPoint))
    }
  }
}

