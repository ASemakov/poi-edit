package desktop.controller;

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.MenuBar
import javafx.stage.Stage
import scala.concurrent.ExecutionContext.Implicits.global

import desktop.controller.controls.KadastrTable
import desktop.model.UiKadastr
import repository.{CategoryRepository, KadastrRepository, RegionRepository};

class KadastrController {
    @FXML private var menuBar: MenuBar = _
    @FXML private var tableView: KadastrTable[UiKadastr] = _

    def stage: Stage = menuBar.getScene.getWindow.asInstanceOf[Stage]

    def onMenuAssociateClick():Unit = {

    }

    def onMenuCloseClick():Unit = {
        stage.close()
    }

    @FXML
    def initialize() = {
        CategoryRepository().all().map(tableView.setCategories)
        RegionRepository().all().map(tableView.setRegions)
        KadastrRepository().allJoined().map(x => {
            val data = FXCollections.observableArrayList(x.map { case (k, r, c) => UiKadastr(k, r, c) }: _*)
            tableView.setItems(data)
        })
    }
}
