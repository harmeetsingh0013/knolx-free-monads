
name := "knolx-free-monad"

version := "0.1"

scalaVersion := "2.12.6"

val catsVersion = "1.1.0"

// https://mvnrepository.com/artifact/org.typelevel/cats-core
libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion

// https://mvnrepository.com/artifact/org.typelevel/cats-free
libraryDependencies += "org.typelevel" %% "cats-free" % catsVersion