import com.typesafe.startscript.StartScriptPlugin

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

name := "hello"

version := "1.0"

scalaVersion := "2.10.1"

resolvers += "twitter-repo" at "http://maven.twttr.com"

libraryDependencies ++= Seq(
	"org.cddcore" %% "website" % "1.7.1")