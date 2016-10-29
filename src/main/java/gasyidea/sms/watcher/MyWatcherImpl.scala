package gasyidea.sms.watcher

import java.io.File
import java.util.LinkedList

abstract class MyWatcherImpl(interval: Int, path: String) extends MyThread(interval, path) with MyWatcher {

  private val listeners = new LinkedList[MyListener]

  def removeAllListeners() = listeners.clear

  def addListener(listener: MyListener) = listeners add listener

  def removeListener(listener: MyListener) = listeners remove listener

  protected def resourceAdded(newResource: File) {
    val listIt = listeners.iterator
    while (listIt.hasNext) {
      listIt next() onAdd(newResource)
    }
  }

  protected def resourceChanged(changedResource: File) {
    val listIt = listeners.iterator
    while (listIt.hasNext) {
      listIt next() onChange(changedResource)
    }
  }

  protected def resourceDeleted(deletedResource: File) {
    val listIt = listeners.iterator
    while (listIt.hasNext) {
      listIt next() onDelete(deletedResource)
    }
  }

  protected def monitoringStarted(monitoredResource: File) {
    val listIt = listeners.iterator
    while (listIt.hasNext) {
      listIt next() onStart(monitoredResource)
    }
  }

  protected def monitoringStopped(notMonitoredResource: File) {
    val listIt = listeners.iterator
    while (listIt.hasNext) {
      listIt next() onStop (notMonitoredResource)
    }
  }

  def doJob()
}
