package gasyidea.sms.watcher

trait MyWatcher {
  def start

  def addListener(listener: MyListener)

  def removeListener(listener: MyListener)

  def stop
}
