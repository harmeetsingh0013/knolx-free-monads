package com.knoldus.backup.free.example2.solution

import java.util.UUID
import cats.free.Free
import cats.implicits._
import com.knoldus.backup.free.example2.solution.Interpreters._
import com.knoldus.backup.free.example2.solution.RepositoryAlg.{UserEmailAlg, _}
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object UserService extends App {

    def addPoints(userId: UUID, pointsToAdd: Int)
                 (implicit ur: Users[UserEmailAlg], es: Emails[UserEmailAlg]):
    Free[UserEmailAlg, Either[String, Unit]] = {

        ur.findUser(userId).flatMap {
            case None => Free.pure(Left(s"Unable to find $userId user"))
            case Some(user) =>
                val updatedUser = user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
                for {
                    _ <- ur.updateUser(updatedUser).map(Right(_))
                    _ <- es.sendEmail(updatedUser.email, "Points Winner",
                        s"you have won $pointsToAdd points")
                } yield Right(())
        }
    }

    val userEmailInterpreters = FutureUserInterpreter or FutureEmailInterpreter

    val result = addPoints(UUID.randomUUID(), 13).foldMap(userEmailInterpreters)

    val output = Await.result(result, 1 seconds)
    println(result)
}
