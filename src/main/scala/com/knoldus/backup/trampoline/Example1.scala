package com.knoldus.backup.trampoline

import scala.annotation.tailrec

object Example1 extends App {

    @tailrec
    def rec[A](list: List[A]): Boolean = list match {
        case Nil => true
        case (h :: t) =>
            println(s"Elements: $h")
            rec(t)
    }

    rec((0 to 100000).toList)
}
