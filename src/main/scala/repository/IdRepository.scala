package repository

import model._
import registration._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait IdRepository[E <: IEntity, M <: IdTable[E]] {
  protected val db = Database.forConfig("mydb")

  def all(): Future[Seq[E]] = run(q)

  protected def run[M1, E1](query: => Query[M1, E1, Seq]): Future[Seq[E1]] = db.run(query.result)

  def getById(id: Int): Future[Option[E]] = run(q.filter(_.id === id)).map(_.headOption)

  def save(entity: E): Future[E] = {
    val value = (q returning q).insertOrUpdate(entity)
    val eventualSeq: Future[Option[E]] = db.run(value)
    eventualSeq.map(_.getOrElse(entity))
  }

  protected def q: TableQuery[M]
}














