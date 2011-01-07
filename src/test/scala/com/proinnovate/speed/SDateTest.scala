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

import org.scalatest.FunSuite
import com.weiglewilczek.slf4s.Logging
import org.joda.time.{LocalDate, DateMidnight}
import java.util.{GregorianCalendar, Calendar, Date}

class SDateTest extends FunSuite with Logging {

  test("Create a date") {
    val d = SDate(2011,1,6)
    assert(d != null)
  }

  test("Speed tests") {
    val (jad, gre, jod, spd) =     (new Date(2011,1,16), new GregorianCalendar(2011,1,16),
            new LocalDate(2011,1,16), new SDate(2011,1,16))
    val (jad1, gre1, jod1, spd1) = (new Date(2010,5,4),  new GregorianCalendar(2010,5,4),
            new LocalDate(2010,5,4),  new SDate(2010,5,4))
    val (jad2, gre2, jod2, spd2) = (new Date(2011,1,16), new GregorianCalendar(2011,1,16),
            new LocalDate(2011,1,16), new SDate(2011,1,16))

    logger.debug("Create dates")
    val s1 = speedUp(
      new Date(2011,1,16),
      new GregorianCalendar(2011,1,16),
      new LocalDate(2011,1,16),
      new SDate(2011,1,16)
    )

    logger.debug("Get year, month and date")
    val s2 = speedUp(
      jad.getYear + jad.getMonth + jad.getDate,
      gre.get(Calendar.YEAR) + gre.get(Calendar.MONTH) + gre.get(Calendar.DAY_OF_MONTH),
      jod.getYear + jod.getMonthOfYear + jod.getDayOfMonth,
      spd.year + spd.monthOfYear + spd.dayOfMonth
    )

    logger.debug("Dates are equal")
    val s3 = speedUp(
      jad.equals(jad2) && jad2.equals(jad),
      gre.equals(gre2) && gre2.equals(gre),
      jod.isEqual(jod2) && jod2.isEqual(jod),
      spd == spd2 && spd2 == spd
    )

    logger.debug("Date is after and is before")
    val s4 = speedUp(
      jad1.before(jad2) && jad2.after(jad1),
      gre1.before(gre2) && gre2.after(gre1),
      jod1.isBefore(jod2) && jod2.isAfter(jod1),
      spd1 < spd2 && spd2 > spd1
    )

    logger.debug("Get the current date")
    val s5 = speedUp(
      new Date(),
      new GregorianCalendar(),
      new LocalDate(),
      SDate()
    )

    logger.debug("Take a number of years from a date and give a new date")
    val s6 = speedUp(
      jad.setYear(jad.getYear - 17),
      gre.set(Calendar.YEAR, gre.get(Calendar.YEAR) - 17),
      jod.minusYears(17),
      spd.minusYears(17)
    )

    val overall = s1 * s2 * s3 * s4 * s5 * s6
    logger.debug("Overall speedup is %.2f times".format(overall))
    assert(overall > 2, "Expecting an overall speedup of at least 2 times")
  }


  // Support methods

  private def timeTakenTo(f: => Unit) = timeTakenToRepeat(1)(f)

  private def timeTakenToRepeat(times: Int)(f: => Unit): Long = {
    val start = System.nanoTime
    (0 until times).foreach{ _ => f }
    (System.nanoTime - start) / 100
  }

  private def speedUp(java: => Unit, greg: => Unit, joda: => Unit, speed: => Unit): Float = {
    val iterations = 100000

    val javaTime = timeTakenToRepeat(iterations){ java }
    val gregTime = timeTakenToRepeat(iterations){ greg }
    val jodaTime = timeTakenToRepeat(iterations){ joda }
    val speedTime = timeTakenToRepeat(iterations){ speed }

    val fastestCompetitor = javaTime min gregTime min jodaTime
    val result = fastestCompetitor.toFloat / speedTime
    val differenceText = if (result >= 1) "%6.2fx Faster".format(result) else
        "%6.2fx Slower !".format(1/result)
    logger.debug("%8d (java) %8d (greg) %8d (joda) %8d (speed) - %s".
            format(javaTime, gregTime, jodaTime, speedTime, differenceText))

    result
  }

}

