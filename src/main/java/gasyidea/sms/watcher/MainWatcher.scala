package gasyidea.sms.watcher

import java.io.File
import java.util.HashMap
import javafx.scene.control.TextArea

import gasyidea.sms.interfaces.MyLog
import gasyidea.sms.utils.MyDateHelper

class MainWatcher extends MyWatcherImpl(1, "") with MyLog {

  private val currentFiles = new HashMap[String, String]
  private val prevFiles = new HashMap[String, String]
  private var directory: String = _
  private var textArea: TextArea = _

  private val INVALID_DIR = " n'est pas un dossier valide"

  def this(interval: Int, directoryPath: String) {
    this
    val theDirectory = new File(directoryPath)
    if (theDirectory.exists() && !theDirectory.isDirectory) {
      val message = directory + INVALID_DIR
      val date = MyDateHelper.getCurrentDate
      textArea appendText textArea.getText + "\n" +date + ": " + message
    }
    directory = directoryPath
  }

  override def start() {
    takeSnapshot
    super.start
    val theDirectory = new File(directory)
    monitoringStarted(theDirectory)
  }

  override def stop() {
    super.stop
    val theDirectory = new File(directory)
    monitoringStopped(theDirectory)
  }

  private def takeSnapshot() {
    prevFiles.clear
    prevFiles.putAll(currentFiles)
    currentFiles.clear
    val theDirectory = new File(directory)
    val children = theDirectory.listFiles
    for (i <- 0 until children.length) {
      val file = children(i)
      currentFiles put (file.getAbsolutePath, file.lastModified().toString)
    }
  }

  def doJob() {
    takeSnapshot
    val currentIt = currentFiles.keySet.iterator
    while (currentIt.hasNext) {
      val fileName = currentIt.next
      val lastModified = currentFiles get fileName
      if (!(prevFiles containsKey fileName)) {
        resourceAdded(new File(fileName))
      } else if (prevFiles containsKey fileName) {
        val prevModified = prevFiles.get(fileName)
        if ((prevModified compareTo lastModified) != 0) {
          resourceChanged(new File(fileName))
        }
      }
    }
    val prevIt = prevFiles.keySet.iterator
    while (prevIt.hasNext) {
      val fileName = prevIt.next
      if (!(currentFiles containsKey fileName)) {
        resourceDeleted(new File(fileName))
      }
    }
  }

  override def setMyLogArea(area: TextArea) = this.textArea = textArea
}
