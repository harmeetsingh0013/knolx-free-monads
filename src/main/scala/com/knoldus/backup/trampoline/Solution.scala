package com.knoldus.backup.trampoline

object Solution extends App {

    sealed trait Trampoline[A]
    case class Done[A](value: A) extends Trampoline[A]
    case class More[A](call: () => Trampoline[A]) extends Trampoline[A]

    def even[A](list: List[A]): Trampoline[Boolean] = list match {
        case Nil => Done(true)
        case (h :: t) =>
            println(s"Even: $h")
            More(() => odd(t))
    }

    def odd[A](list: List[A]): Trampoline[Boolean] = list match {
        case Nil => Done(true)
        case (h :: t) =>
            println(s"Odd: $h")
            More(() => even(t))
    }

    def run[A](trampoline: Trampoline[A]): A =  trampoline match {
        case Done(value) => value
        case More(r) => run(r())
    }

    val trampoline = even((0 to 100000).toList)
    run(trampoline)
 }
