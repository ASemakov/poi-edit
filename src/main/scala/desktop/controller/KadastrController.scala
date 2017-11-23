package desktop.controller

;

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.MenuBar
import javafx.stage.Stage

import desktop.controller.controls.KadastrTable
import desktop.model.UiKadastr
import repository.{CategoryRepository, KadastrRepository, RegionRepository}

import scala.concurrent.ExecutionContext.Implicits.global;

class KadastrController {
  private var associateEventHandler: Option[Option[Int] => Unit] = None
  def setOnAction(eventHandler: Option[Int] => Unit) = {
    associateEventHandler = Option(eventHandler)
  }


  @FXML private var menuBar: MenuBar = _
  @FXML private var tableView: KadastrTable[UiKadastr] = _
  private var selectedId: Option[Int] = None

  def stage: Stage = menuBar.getScene.getWindow.asInstanceOf[Stage]

  protected def updateSelection() = {
    tableView.getItems.toArray(new Array[UiKadastr](tableView.getItems.size())).toList
      .collectFirst {
        case x if x.idProperty.get() == selectedId => x
      }.foreach(item => {
        tableView.getSelectionModel.select(item)
        tableView.scrollTo(item)
      }
    )

  }

  def setSelectedId(value: Option[Int]): Unit = {
    selectedId = value
    updateSelection()
  }

  def getSelectedId: Option[Int] = selectedId

  def onMenuAssociateClick(): Unit = {
    selectedId = tableView.getSelectionModel.getSelectedItem.idProperty.get()
    associateEventHandler.foreach(handler => handler(selectedId))
    onMenuCloseClick()
  }

  def onMenuUnassociateClick(): Unit = {
    associateEventHandler.foreach(handler => handler(None))
    onMenuCloseClick()
  }

  def onMenuCloseClick(): Unit = {
    stage.close()
  }

  @FXML
  def initialize() = {
    CategoryRepository().all().map(tableView.setCategories)
    RegionRepository().all().map(tableView.setRegions)
    KadastrRepository().allJoined().map(x => {
      val data = FXCollections.observableArrayList(x.map { case (k, r, c) => UiKadastr(k, r, c) }: _*)
      tableView.setItems(data)
      updateSelection()
    })
  }
}
