package com.knoldus.backup.trampoline

object Problem1 extends App {

   def even[A](list: List[A]): Boolean = list match {
       case Nil => true
       case (h :: t) =>
           println(s"Even: $h")
           odd(t)
   }

    def odd[A](list: List[A]): Boolean = list match {
        case Nil => true
        case (h :: t) =>
            println(s"Odd: $h")
            even(t)
    }

    even((0 to 100000).toList)
}
