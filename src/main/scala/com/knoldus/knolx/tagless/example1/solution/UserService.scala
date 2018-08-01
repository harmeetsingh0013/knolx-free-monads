package com.knoldus.knolx.tagless.example1.solution

import java.util.UUID
import cats.free.Free
import com.knoldus.backup.tagless.example1.solution.Interpreters._
import com.knoldus.backup.tagless.example1.solution.UserRepositoryAlg._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import cats.implicits._

object UserService extends App {

    def addLoyalty(userId: UUID, pointToAdd: Int): UserRepository[Either[String, Unit]] = {
        findUser(userId).flatMap {
            case None => Free.pure(Left(s"Sorry, user $userId not found"))
            case Some(user) =>
                val updatedUser = user.copy(loyaltyPoints = user.loyaltyPoints + pointToAdd)
                updateUser(updatedUser).map(Right(_))
        }
    }


    val result = addLoyalty(UUID.randomUUID(), 13).foldMap(FutureUserInterpreter)
    val output = Await.result(result, 1 seconds)
    println(s"Output $output")
}
