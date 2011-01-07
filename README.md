speed-date
==========

Version: 0.1-SNAPSHOT

This code is currently under development. It is a very simple Date library
written in Scala with the current aims:

 * Speed - it should be fast at doing very simple and standard things with
   dates like date comparisons and passing dates around.
 * Storage efficiency - it should optimise storage of large numbers of date
   objects by producing immutable dates and only ever creating one date
   object for any unique date.
 * Lazy evaluation - it should only evaluate information when it is needed
   including validation of dates.  In-other-words, it should allow dates to
   be created that are invalid and only check when you ask it to or when
   you ask it to manipulate the date in a way that is only possible with a
   valid date.
 * Scala integration - it should look like any other standard Scala type
   allowing, for example, sorting and normal comparison operators.

Without compromising these aims it will also try, over time, to provide as
much of the standard functionality of the Java Date and Joda Time date
libraries.

The test cases in this library test the performance against Java Date,
Gregorian Calendar and Joda Time. The dependency on Joda Time is only for
testing. These crude tests currently appear to indicate that the library (with
its very limited functionality) outperforms the best of the other libraries by
up to 10 times.

Note that the priority of lazy valuation and storage efficiency should ideally
be reflected in which tests perform best.

    Create dates 
      846220 (java)   446960 (greg)   365910 (joda)    44130 (speed) -   8.29x Faster 
    Get year, month and date 
       66580 (java)    50070 (greg)   349840 (joda)    90360 (speed) -   1.80x Slower ! 
    Dates are equal 
       55140 (java)  1084680 (greg)    77380 (joda)    20470 (speed) -   2.69x Faster 
    Date is after and is before 
       50300 (java)  2181560 (greg)    61530 (joda)    91040 (speed) -   1.81x Slower ! 
    Get the current date 
       66420 (java)   559380 (greg)   324660 (joda)   142930 (speed) -   2.15x Slower ! 
    Take a number of years from a date and give a new date 
      406910 (java)   529420 (greg)   439900 (joda)    40480 (speed) -  10.05x Faster 

Including this library
----------------------

This library has not been released yet but is available as a snapshot. To
include this library in an sbt project enter the following project definition:

    val speedDate = "com.proinnovate" %% "speed-date" % "0.1-SNAPSHOT"

License
-------

This software is Copyright © 2011, Stuart Roebuck and licensed under a
standard MIT license (more specifically referred to as the Expat license). See
the `LICENSE.md` file for details.

