package gasyidea.sms.watcher

import java.io.File
import javafx.application.Platform
import javafx.scene.control.TextArea
import javafx.scene.paint.Color
import javafx.scene.text.Text

import gasyidea.sms.interfaces.MyLog
import gasyidea.sms.utils.{MyConnectionHelper, MyDateHelper}

import scala.io.Source

class MyFileListenerImpl(var textArea: TextArea) extends MyAbstractListener with MyFileListener {

  private val INITIAL_RESOURCE = "Listening : "
  private val NEW_RESOURCE = "Adding : "
  private val SEPARATOR = ": ";
  private val REGEX = "-"
  private val END = "-fin"
  private val BLANK = ""
  private val DOUBLE_QUOTES = "\"";
  private val NEW_LINE = "\n";

  def onStart(monitoredResource: File) = {
    Platform.runLater(new Runnable() {
      override def run = if (monitoredResource.isDirectory) updateTextArea(monitoredResource)
    })
  }

  private def updateTextArea(file: File) = {
    val myText = new Text(file.getAbsolutePath)
    textArea setText textArea.getText + NEW_LINE + MyDateHelper.getCurrentDate + SEPARATOR + INITIAL_RESOURCE
    textArea setText textArea.getText + myText.getText
  }

  def onAdd(newResource: File) = {
    Platform.runLater(new Runnable() {
      override def run = if (newResource.isFile) doJobFor(newResource)
    })
  }

  private def doJobFor(file: File) = {
    getDataFrom(file)
  }

  private def getDataFrom(file: File) = {
    val myText = new Text(file.getAbsolutePath)
    myText setFill Color.BLUE
    textArea setText textArea.getText + NEW_LINE + MyDateHelper.getCurrentDate + SEPARATOR + NEW_RESOURCE + myText.getText
    val source = Source.fromFile(file.getAbsolutePath).getLines().mkString("@").replace(DOUBLE_QUOTES, BLANK);
    MyConnectionHelper.getWriter.println(source)
  }

  override def onStop(notMonitoredResource: File) = {}

  override def onChange(changedResource: File) ={}

  override def onDelete(deletedResource: File) = {}
}
