package com.knoldus.backup.tagless.example1.solution

import cats.{Id, ~>}
import scala.concurrent.Future

object Interpreters {

    val FutureUserInterpreter = new (UserRepositoryAlg ~> Future) {
        override def apply[A](fa : UserRepositoryAlg[A]) : Future[A] = fa match {
            case FindUser(userId) =>
                // perform database operations
                println("Future User find successfully")
                Future.successful(Some(User(userId, "harmeet@knoldus.com", 13)))
            case UpdateUser(user) =>
                // perform database operations
                println("Future Updated user successfully")
                Future.successful(())
        }
    }

    val IdUserInterpreters = new (UserRepositoryAlg ~> Id) {
        override def apply[A](fa : UserRepositoryAlg[A]) : Id[A] = fa match {
            case FindUser(userId) =>
                // perform database operations
                println("Id User find successfully")
                Some(User(userId, "harmeet@knoldus.com", 13))
            case UpdateUser(user) =>
                // perform database operations
                println("Id Updated user successfully")
                ()
        }
    }
}
