package com.knoldus.knolx.tagless.example1.solution

import java.util.UUID
import cats.free.Free

case class User(id: UUID, emai: String, loyaltyPoints: Int)

sealed trait UserRepositoryAlg[T]
case class FindUser(userId: UUID) extends UserRepositoryAlg[Option[User]]
case class UpdateUser(user: User) extends UserRepositoryAlg[Unit]

object UserRepositoryAlg {

    type UserRepository[T] = Free[UserRepositoryAlg, T]

    def findUser(userId: UUID): UserRepository[Option[User]] = Free.liftF(FindUser(userId))

    def updateUser(user: User): UserRepository[Unit] = Free.liftF(UpdateUser(user))
}