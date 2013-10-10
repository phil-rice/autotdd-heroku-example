import com.typesafe.startscript.StartScriptPlugin

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

name := "hello"

version := "1.0"

scalaVersion := "2.10.1"

resolvers += "twitter-repo" at "http://maven.twttr.com"

libraryDependencies ++= Seq(
	"org.autotdd" %% "engine" % "1.1.0",
    "com.sun.jersey" % "jersey-server" % "1.2",
    "com.sun.jersey" % "jersey-json" % "1.2",
    "org.eclipse.jetty" % "jetty-server" % "8.0.0.M0",
    "org.eclipse.jetty" % "jetty-servlet" % "8.0.0.M0"
)