package desktop

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class Start extends Application {
  override def start(primaryStage: Stage): Unit = {
     val url = getClass.getResource("/fxml/scenes/main.fxml")
    // val url = getClass.getResource("/fxml/scenes/kadastr.fxml")
//    val url = getClass.getResource("/fxml/scenes/browser.fxml")
    val layout = new FXMLLoader(url).load[javafx.scene.Parent]
    primaryStage.setScene(new Scene(layout))
    primaryStage.show()
  }
}
