package com.knoldus.backup.tagless.example1.solution

import java.util.UUID
import cats.free.Free

case class User(id: UUID, email: String, loyaltyPoints: Int)

sealed trait UserRepositoryAlg[T]
case class FindUser(id: UUID) extends UserRepositoryAlg[Option[User]]
case class UpdateUser(user: User) extends UserRepositoryAlg[Unit]

object UserRepositoryAlg {

    type UserRepository[T] = Free[UserRepositoryAlg, T]

    def findUser(id: UUID): UserRepository[Option[User]] = Free.liftF(FindUser(id))
    def updateUser(user: User): UserRepository[Unit] = Free.liftF(UpdateUser(user))
}
