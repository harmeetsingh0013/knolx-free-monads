// Sum type

sealed trait Direction
case object North extends Direction
case object South extends Direction
case object East extends Direction
case object West extends Direction

sealed trait Bool
case object True
case object False

//Product type
case class Person(
                 name: String,
                 age: Int,
                 contact: String
                 )

// Hybrid types

sealed trait Shape
final case class Circle(radius: Double) extends Shape
final case class Rectangle(width: Double, height: Double) extends Shape