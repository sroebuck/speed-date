/*
 * Copyright (c) Stuart Roebuck, 2011
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sub-license, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the
 * warranties of merchantability, fitness for a particular purpose and non-infringement. in no event shall the authors
 * or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or
 * otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.proinnovate.speed

import collection.mutable.WeakHashMap
import java.util.{Calendar, GregorianCalendar, Date}

final class SDate(val year: Int, val monthOfYear: Int, val dayOfMonth: Int) {

  final override lazy val hashCode: Int = daysIsh

  final lazy val milliseconds = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTimeInMillis

  private final lazy val daysIsh = (year << 9) | (monthOfYear << 5) | dayOfMonth
  
  final def >(that: SDate) = this.hashCode > that.hashCode
  final def <(that: SDate) = this.hashCode < that.hashCode
  final def minusDays(days: Int) = copy(dayOfMonth = dayOfMonth - days)
  final def minusMonths(months: Int) = copy(monthOfYear = monthOfYear - months)
  final def minusYears(years: Int) = copy(year = year - years)
  final def plusDays(days: Int) = copy(dayOfMonth = dayOfMonth + days)
  final def plusMonths(months: Int) = copy(monthOfYear = monthOfYear + months)
  final def plusYears(years: Int) = copy(year = year + years)

  def copy(year: Int = year, monthOfYear: Int = monthOfYear, dayOfMonth: Int = dayOfMonth) =
    new SDate(year, monthOfYear, dayOfMonth)

}

final object SDate {

  private val collection: WeakHashMap[Int, SDate] = WeakHashMap()

  private var todayPair: Option[(Long, SDate)] = None

  final def apply(year: Int, monthOfYear: Int, dayOfMonth: Int): SDate = {
    val x = new SDate(year, monthOfYear, dayOfMonth)
    collection.getOrElseUpdate(x.hashCode, x)
  }

  final def apply(): SDate = {
    val millis = System.currentTimeMillis
    if (todayPair.isDefined && todayPair.get._1 > millis) {
      todayPair.get._2
    } else {
      val c = new GregorianCalendar
      c.set(Calendar.HOUR_OF_DAY, 23)
      c.set(Calendar.MINUTE, 59)
      c.set(Calendar.SECOND, 59)
      c.set(Calendar.MILLISECOND, 99)
      todayPair = Some((c.getTimeInMillis,
              new SDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))))
      todayPair.get._2
    }
  }

}
 
