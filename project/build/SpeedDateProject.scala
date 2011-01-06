/*
 * Copyright (c) ProInnovate Limited, 2010
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
  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

}
