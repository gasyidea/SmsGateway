package gasyidea.sms.watcher

import java.io.File

trait MyListener {

  def onStart(monitoredResource: File)

  def onStop(notMonitoredResource: File)

  def onAdd(newResource: File)

  def onChange(changedResource: File)

  def onDelete(deletedResource: File)
}