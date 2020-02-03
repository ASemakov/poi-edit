package repository

import model._
import repository.registration._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait IdRepository[E <: IEntity, M <: IdTable[E]] {
  protected val db = Database.forConfig("mydb")

  def all(): Future[Seq[E]] = run(q.sortBy(_.id))

  protected def run[M1, E1](query: => Query[M1, E1, Seq]): Future[Seq[E1]] = db.run(query.result)

  def getById(id: Int): Future[Option[E]] = run(q.filter(_.id === id)).map(_.headOption)


  def save(entity: E): Future[E] = {
    db.run((q returning q).insertOrUpdate(entity)).map(_.getOrElse(entity))
  }

  def saveAll(entities: Seq[E]): Future[Seq[E]] = Future.sequence(entities.map(save))

  def insertAll(entities: Seq[E]): Future[Seq[E]] = {
    db.run((q returning q).forceInsertAll(entities)).map(x => {
      seqReinit()
      x
    })
  }

  def delete(entity: E): Future[Boolean] = {
    // Returns number of affected rows
    val eventualInt: Future[Int] = db.run(q.filter(_.id === entity.id).delete)
    eventualInt.map(_ > 0)
  }

  def truncate(): Future[Int] = {
    db.run(q.delete)
  }

  protected def seqReinit(): Unit = {
    db.run(q.map(_.id).max.result).map(x => {
      db.run(sql"SELECT setval('#${q.baseTableRow.tableName}_id_seq', ${x})".asUpdate)
    })
  }

  protected def q: TableQuery[M]
}
