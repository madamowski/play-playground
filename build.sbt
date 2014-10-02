name := """play-playground"""

version := "1.0"
    
scalaVersion := "2.11.2"
    
resolvers in ThisBuild += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"   
    
resolvers in ThisBuild += "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases/"

lazy val root = (project in file(".")).enablePlugins(PlayScala)        
    
libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "angularjs" % "1.2.19",
  "org.reactivemongo" %% "reactivemongo" % "0.11.0-SNAPSHOT",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.0-SNAPSHOT",
  "com.github.t3hnar" %% "scala-bcrypt" % "2.4"
//  "org.webjars" % "dygraphs" % "1.0.1-1",
//  jdbc,
//  anorm,
//  cache,
//  "mysql" % "mysql-connector-java" % "5.1.18",
//  "org.xerial" % "sqlite-jdbc" % "3.7.2",
//  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
//  "com.typesafe.slick" %% "slick" % "2.1.0-M2",
//  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
//  "com.typesafe.slick" %% "slick" % "2.1.0",
//  "org.slf4j" % "slf4j-nop" % "1.6.4",
//  "org.xerial" % "sqlite-jdbc" % "3.7.2",
//  "org.scalikejdbc" %% "scalikejdbc" % "2.1.2",
//  "ch.qos.logback"  %  "logback-classic" % "1.1.2"
//  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
//  "org.reactivemongo" %% "reactivemongo" % "0.11.0-SNAPSHOT"
)
