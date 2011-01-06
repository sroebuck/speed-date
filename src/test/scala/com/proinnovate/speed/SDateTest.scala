/*
 * Copyright (c) ProInnovate Limited, 2011
 */

package com.proinnovate.speed

import org.scalatest.FunSuite
import java.util.Date
import org.joda.time.DateMidnight
import com.weiglewilczek.slf4s.Logging

class SDateTest extends FunSuite with Logging {

  test("Create a date") {
    val d = SDate(2011,1,6)
    assert(d != null)
  }

  test("Create dates") {
    val s = speedUp(
      new Date(2011,1,16),
      new DateMidnight(2011,1,16),
      new SDate(2011,1,16)
    )
    assert(s > 24, "Expecting at least 24x speedup")
  }

  test("Get year, month and date") {
    val (jad, jod, spd) = (new Date(2011,1,16), new DateMidnight(2011,1,16), new SDate(2011,1,16))
    val s = speedUp(
      jad.getYear + jad.getMonth + jad.getDate,
      jod.getYear + jod.getMonthOfYear + jod.getDayOfMonth,
      spd.year + spd.monthOfYear + spd.dayOfMonth
    )
    assert(s > 2, "Expecting at least 2x speedup")
  }

  test("Dates are equal") {
    val (jad1, jod1, spd1) = (new Date(2011,1,16), new DateMidnight(2011,1,16), new SDate(2011,1,16))
    val (jad2, jod2, spd2) = (new Date(2011,1,16), new DateMidnight(2011,1,16), new SDate(2011,1,16))
    val s = speedUp(
      jad1.equals(jad2) && jad2.equals(jad1),
      jod1.isEqual(jod2) && jod2.isEqual(jod1),
      spd1 == spd2 && spd2 == spd1
    )
    assert(s > 0.5, "Expecting no less than 2x slow down")
  }

  test("Date is after and is before") {
    val (jad1, jod1, spd1) = (new Date(2010,5,4), new DateMidnight(2010,5,4), new SDate(2010,5,4))
    val (jad2, jod2, spd2) = (new Date(2011,1,16), new DateMidnight(2011,1,16), new SDate(2011,1,16))
    val s = speedUp(
      jad1.before(jad2) && jad2.after(jad1),
      jod1.isBefore(jod2) && jod2.isAfter(jod1),
      spd1 < spd2 && spd2 > spd1
    )
    assert(s > 0.5, "Expecting no less than 2x slow down")
  }

  test("Get the current date") {
    val s = speedUp(
      new Date(),
      new DateMidnight(),
      SDate()
    )
    assert(s > 0.10, "Expecting no less than 10x slow down")
  }

  test("Take a number of years from a date and give a new date") {
    val (jad, jod, spd) = (new Date(2011,1,16), new DateMidnight(2011,1,16), new SDate(2011,1,16))
    val value = 17
    val s = speedUp(
      jad.setYear(jad.getYear - value),
      jod.minusYears(value),
      spd.minusYears(value)
    )
    assert(s > 3, "Expecting at least 3x speedup")
  }


  // Support methods

  private def timeTakenTo(f: => Unit) = timeTakenToRepeat(1)(f)

  private def timeTakenToRepeat(times: Int)(f: => Unit): Long = {
    val start = System.nanoTime
    (0 until times).foreach{ _ => f }
    (System.nanoTime - start) / 100
  }

  private def speedUp(java: => Unit, joda: => Unit, speed: => Unit): Float = {
    val iterations = 100000

    val javaTime = timeTakenToRepeat(iterations){ java }
    val jodaTime = timeTakenToRepeat(iterations){ joda }
    val speedTime = timeTakenToRepeat(iterations){ speed }

    val fastestCompetitor = javaTime min jodaTime
    val result = fastestCompetitor.toFloat / speedTime
    val differenceText = if (result >= 1) "%6.2fx Faster".format(result) else
        "%6.2fx Slower !".format(1/result)
    logger.debug("java:%8d joda:%8d speed:%8d - %s".format(javaTime, jodaTime, speedTime, differenceText))

    result
  }

}

