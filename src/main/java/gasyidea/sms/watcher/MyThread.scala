package gasyidea.sms.watcher

abstract class MyThread extends Runnable {

  private var active: Boolean = _
  private var interval: Int = _
  private var path: String = _
  private var thread: Thread = _

  def this(interval: Int, path: String) {
    this
    this.interval = interval * 1000
    this.path = path
  }

  def start {
    active = true
    if (thread == null && interval > 0) {
      thread = new Thread(this)
      thread.start
    }
  }

  def stop = active = false

  def run {
    Thread.currentThread setPriority Thread.MIN_PRIORITY
    while (active) {
      try {
        doJob
        Thread sleep interval
      }
      catch {
        case e: InterruptedException => {}
      }
    }
  }

  override def toString = path

  protected def doJob
}