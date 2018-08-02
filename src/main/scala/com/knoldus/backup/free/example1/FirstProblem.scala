package com.knoldus.backup.free.example1

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object FirstProblem extends App {

    case class User(id: UUID, email: String, loyaltyPoints: Int)

    class UserRepository {
        def findUser(id: UUID): Future[Option[User]] = {
            // interact with database
            println("User find successfully")
            Future.successful(Some(User(id, "harmeet@knoldus.com", 13)))
        }

        def updatedUser(user: User): Future[Unit] = {
            // perform database operation
            println("Updated user successfully")
            Future.successful(())
        }
    }

    class UserService(userRepository: UserRepository) {
        def addPoints(userId: UUID, pointsToAdd: Int): Future[Either[String, Unit]] = {
            userRepository.findUser(userId).flatMap {
                case None => Future.successful(Left(s"User $userId not found"))
                case Some(user) =>
                    val updatedUser = user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
                    userRepository.updatedUser(updatedUser).map(Right(_))
            }
        }
    }

    val userService = new UserService(new UserRepository)
    val result = userService.addPoints(UUID.randomUUID(), 13)

    val output = Await.result(result, 1 seconds)
    println(output)
}
