speed-date
==========

Version: 0.1-SNAPSHOT

This code is currently under development.  It is a very simple Date library written in Scala with
the aim of providing fast performance for very simple applications of Dates which do not require
any concept of time zone support or alternative calendars than Gregorian.  It is designed for
applications that generate large numbers of Date objects that represent whole days rather than
a specific time in a day.

The test cases in this library test the performance against Java Date, Gregorian Calendar and
Joda Time.  The dependency on Joda Time is only for testing.  These crude tests currently appear
to indicate that the library (with its very limited functionality) outperforms the best of the
other libraries by up to 10 times.

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

License
-------

This software is Copyright (c) 2011, Stuart Roebuck and licensed under a standard MIT license (more
specifically referred to as the Expat license). See the `LICENSE.md` file for details.

