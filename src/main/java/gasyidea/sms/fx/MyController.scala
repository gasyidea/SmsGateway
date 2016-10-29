package gasyidea.sms.fx

import java.io.File
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.{Button, TextArea, TextField}
import javafx.scene.layout.Pane
import javafx.stage.{DirectoryChooser, Stage}

import gasyidea.sms.utils.MyConnectionHelper
import gasyidea.sms.watcher.{MainWatcher, MyFileListenerImpl}

class MyController extends Pane {

  private val SELECTED_DIR = "Selection du dossier à écouter"
  private val DEFAULT_PATH = "D:/retros"
  private val NULL = "null"
  private val SERVER_ERROR = "Android server unavailable....\nPlease, start the server first"

  @FXML private var pathText: TextField = _
  @FXML private var chooseButton: Button = _
  @FXML private var startButton: Button = _
  @FXML private var myLogArea: TextArea = _

  def getText: String = pathText.getText

  def setMyLogArea(myLogArea: TextArea) = this.myLogArea = myLogArea

  def getMyLogArea = myLogArea

  @FXML private def chooseDirectory {
    val chooser = getChooser
    val myDirectory = chooser showDialog getStage
    setText(myDirectory)
  }

  @FXML private def runServer {
    val directory = getText
    if (!directory.trim.contains(NULL)) {
      try {
        initWatcher(directory)
        updateButtons
      } catch {
        case e: Exception => getMyLogArea.setText(SERVER_ERROR)
      }
    }
    else {
      getStage.close()
    }
  }

  @FXML private def stopServer = exitApp

  private def getStage = chooseButton.getScene.getWindow.asInstanceOf[Stage]

  private def setText(myDirectory: File) {
    if (!myDirectory.exists()) pathText setText NULL else pathText setText myDirectory.getAbsolutePath
  }

  private def getChooser = {
    val chooser = new DirectoryChooser
    chooser setTitle SELECTED_DIR
    chooser setInitialDirectory new File(DEFAULT_PATH)
    chooser
  }

  private def updateButtons {
    startButton setDisable true
    chooseButton setDisable true
    myLogArea setEditable false
    pathText setEditable false
  }

  private def exitApp {
    Platform.exit();
    System.exit(0)
  }

  private def initWatcher(directory: String) = {
    try {
      MyConnectionHelper.initConnection
      val mainWatcher = new MainWatcher(1, directory)
      mainWatcher setMyLogArea myLogArea
      val fileListener = new MyFileListenerImpl(myLogArea)
      MyConnectionHelper setTextArea myLogArea
      mainWatcher addListener fileListener
      mainWatcher.start
    } catch {
      case e: Exception => myLogArea.setText(SERVER_ERROR)
    }
  }
}