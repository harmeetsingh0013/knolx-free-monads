package com.knoldus.backup.tagless.example1.solution

import java.util.UUID
import cats.free.Free
import com.knoldus.backup.tagless.example1.solution.Interpreters._
import com.knoldus.backup.tagless.example1.solution.UserRepositoryAlg._

object UserService extends App {

    def addPoints(userId: UUID, pointsToAdd: Int): UserRepository[Either[String, Unit]] = {
        findUser(userId).flatMap {
            case None => Free.pure(Left(s"User $userId not found"))
            case Some(user) =>
                val updatedUser = user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
                updateUser(updatedUser).map(Right(_))
        }
    }

    val result = addPoints(UUID.randomUUID(), 13).foldMap(IdUserInterpreters)

//    val output = Await.result(result, 1 seconds)
    println(result)
}
