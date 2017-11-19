package desktop.utils

import java.net.URL
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, ButtonType}
import javafx.stage.{Modality, Stage}

object WindowUtils {
  def information(msg: String): Unit = {
    val alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK)
    alert.showAndWait()
  }

  def error(msg: String): Unit = {
    val alert = new Alert(AlertType.ERROR, msg, ButtonType.OK)
    alert.showAndWait()
  }

  def confirmation(msg: String): Boolean = {
    val alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.OK, ButtonType.CANCEL)
    alert.showAndWait()
    alert.getResult == ButtonType.OK
  }

  def createStage[T](stage: Stage, location: URL, modality: Modality = Modality.APPLICATION_MODAL): T  = {
    val popupStage: Stage = new Stage()
    popupStage.initOwner(stage)
    popupStage.initModality(modality)
    val loader = new FXMLLoader(location)
    val scene = new Scene(loader.load[javafx.scene.Parent])
    popupStage.setScene(scene)
    popupStage.show()
    loader.getController[T]
  }

}
