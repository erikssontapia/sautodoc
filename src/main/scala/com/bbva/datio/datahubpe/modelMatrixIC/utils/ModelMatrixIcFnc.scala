package com.bbva.datio.datahubpe.modelMatrixIC.utils

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar

/** Class ModelMatrixIcFnc for process data of model Matrix IC.
 *
 *  @constructor create a new ModelMatrixIcFnc with parameters:
 */
class ModelMatrixIcFnc {

  /** Method for null To Empty String with a given value
   *
   *  @param value
   *  @return a new String with a given value
   */
  def nullToEmptyString( value : String) :String = { if (value == null) "" else value }

  /** Method for get Prev Month Last Date with a given processPeriod
   *
   *  @param processPeriod
   *  @return a new Date with a given processPeriod
   */
  def getPrevMonthLastDate(processPeriod: String): Date = {
    var calendar = Calendar.getInstance()
    calendar.setTime(new SimpleDateFormat("yyyyMM").parse(processPeriod))
    calendar.add(Calendar.MONTH, -1)
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    new Date(calendar.getTimeInMillis)
  }

}
