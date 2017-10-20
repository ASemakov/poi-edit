package repository

import model.IDictEntity
import registration.IdNameTable
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait IdNameRepository[E <: IDictEntity, M <: IdNameTable[E]] extends IdRepository[E, M] {

  def getByName(name: String):Future[Option[E]] = run(q.filter(_.name === name)).map(_.headOption)

}
