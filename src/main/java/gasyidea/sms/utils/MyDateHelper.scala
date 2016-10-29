package gasyidea.sms.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

object MyDateHelper {
  private val FORMAT1 = "dd/MM/yyyy hh:mm:ss a"
  private val FORMAT2 = "dd/MM/yyyy"
  private val dateFormat1 = new SimpleDateFormat(FORMAT1)
  private val dateFormat2 = DateTimeFormatter ofPattern FORMAT2

  def getCurrentDate = dateFormat1 format (new Date())

  def createDate(str: String) = LocalDate.parse(str, dateFormat2)

}
