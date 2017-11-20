package repository

import model.{Category, Kadastr, Region}
import registration.{CategoryReg, KadastrReg, RegionReg}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

case class KadastrRepository() extends IdRepository[Kadastr, KadastrReg] {
  val q = TableQuery[KadastrReg]

  def allJoined(): Future[Seq[(Kadastr, Option[Region], Option[Category])]] = {
    run(q
      .joinLeft(TableQuery[RegionReg]).on(_.regionid === _.id)
      .joinLeft(TableQuery[CategoryReg]).on(_._1.categoryid === _.id)
      .map { case ((k, r), c) => (k, r, c) }
    )
  }
}
