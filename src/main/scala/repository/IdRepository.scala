package repository

import model._
import registration._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait IdRepository[E <: IEntity, M <: IdTable[E]] {
  protected val db = Database.forConfig("mydb")
  protected def q:TableQuery[M]
  protected def run[M1, E1](query: => Query[M1, E1, Seq]): Future[Seq[E1]] = db.run(query.result)

  def all(): Future[Seq[E]] = run(q)

  def getById(id: Int):Future[Option[E]] = run(q.filter(_.id === id)).map(_.headOption)
}














