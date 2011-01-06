/*
 * Copyright (c) ProInnovate Limited, 2011
 */

package com.proinnovate.speed

import java.util.Date
import collection.mutable.WeakHashMap

final class SDate(val year: Int, val monthOfYear: Int, val dayOfMonth: Int) {

  final override lazy val hashCode = year * 1000 + monthOfYear * 100 + dayOfMonth

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

  final def apply(year: Int, monthOfYear: Int, dayOfMonth: Int): SDate = {
    val x = new SDate(year, monthOfYear, dayOfMonth)
    collection.getOrElseUpdate(x.hashCode, x)
  }

  final def apply(): SDate = {
    val d = new Date
    new SDate(d.getYear, d.getMonth, d.getDay)
  }

}
 
