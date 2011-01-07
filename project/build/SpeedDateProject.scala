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

import sbt._

class SpeedDateProject(info: ProjectInfo) extends DefaultProject(info) with posterous.Publish {

  // Logging...
  val sl4fs = "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.2"
  val logback = "ch.qos.logback" % "logback-classic" % "0.9.27"

  // Testing...
  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
  val jodaTime = "joda-time" % "joda-time" % "1.6" % "test"

  // postereous.Publish configuration...
  override def extension = ".md"
  // override def extraTags = "template" :: super.extraTags

  // Maven publishing configuration...
  override def managedStyle = ManagedStyle.Maven
  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/snapshots/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

}
