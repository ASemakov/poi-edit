package desktop.controller

import javafx.fxml.FXML
import javafx.scene.control.{MenuBar, MenuItem}
import javafx.stage.Stage

class MainController(){
  @FXML private var menuItemClose: MenuItem = _
  @FXML private var menuBar: MenuBar = _

  def onMenuCloseClick(): Unit ={
    val stage = menuBar.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }
}
