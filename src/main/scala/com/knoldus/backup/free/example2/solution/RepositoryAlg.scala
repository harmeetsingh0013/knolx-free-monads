package com.knoldus.backup.free.example2.solution

import java.util.UUID
import cats.InjectK
import cats.data.EitherK
import cats.free.Free

case class User(id: UUID, email: String, loyaltyPoints: Int)

sealed trait UserRepositoryAlg[T]
case class FindUser(id: UUID) extends UserRepositoryAlg[Option[User]]
case class UpdateUser(user: User) extends UserRepositoryAlg[Unit]

sealed trait EmailRepoAlg[T]
case class SendEmail(email: String, subject: String, body: String) extends EmailRepoAlg[Unit]

object RepositoryAlg {
    type UserEmailAlg[T] = EitherK[UserRepositoryAlg, EmailRepoAlg, T]

    class Users[F[_]](implicit i: InjectK[UserRepositoryAlg, F]) {
        def findUser(id: UUID): Free[F, Option[User]] = Free.inject(FindUser(id))
        def updateUser(user: User): Free[F, Unit] = Free.inject(UpdateUser(user))
    }

    object Users {
        implicit def users[F[_]](implicit i: InjectK[UserRepositoryAlg, F]) = new Users()
    }

    class Emails[F[_]](implicit i: InjectK[EmailRepoAlg, F]) {
        def sendEmail(email: String, subject: String, body: String): Free[F, Unit] =
            Free.inject(SendEmail(email, subject, body))
    }

    object Emails {
        implicit def emails[F[_]](implicit i: InjectK[EmailRepoAlg, F]) = new Emails()
    }
}