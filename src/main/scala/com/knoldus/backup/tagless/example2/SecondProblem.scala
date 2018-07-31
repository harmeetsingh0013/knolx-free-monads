package com.knoldus.backup.tagless.example2

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object SecondProblem extends App {

    case class User(id: UUID, email: String, loyaltyPoints: Int)

    class EmailService {
        def sendEmail(email: String, subject: String, body: String): Future[Unit] = {
            // send an email using third party library
            println(s"Sending to .... $email")
            Future.successful(())
        }
    }

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

    class UserService(userRepository: UserRepository, emailService: EmailService) {
        def addPoints(userId: UUID, pointsToAdd: Int): Future[Either[String, Unit]] = {
            userRepository.findUser(userId).flatMap {
                case None => Future.successful(Left(s"User $userId not found"))
                case Some(user) =>
                    val updatedUser = user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
                    for {
                        _ <- userRepository.updatedUser(updatedUser).map(Right(_))
                        _ <- emailService.sendEmail(updatedUser.email, "Points Winner",
                            s"you have won $pointsToAdd points")
                    } yield Right(())
            }
        }
    }

    val userService = new UserService(new UserRepository, new EmailService)
    val result = userService.addPoints(UUID.randomUUID(), 13)

    val output = Await.result(result, 1 seconds)
    println(output)
}
