package com.knoldus.backup.free.example2.solution

import cats.~>
import scala.concurrent.Future

object Interpreters {

    val FutureUserInterpreter = new (UserRepositoryAlg ~> Future) {
        override def apply[A](fa : UserRepositoryAlg[A]): Future[A] = fa match {
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

    val FutureEmailInterpreter = new (EmailRepoAlg ~> Future) {
        override def apply[A](fa : EmailRepoAlg[A]): Future[A] = fa match {
            case SendEmail(email: String, subject: String, body: String) =>
                // sent an email using any third arty service
                println(s"Sending to .... $email")
                Future.successful(())
        }
    }
}
