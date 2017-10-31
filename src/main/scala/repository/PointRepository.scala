package repository

import model.{Point, PointType, TrustLevel}
import registration.{PointReg, PointTypeReg, TrustLevelReg}
import slick.lifted.{QueryBase, TableQuery}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class PointRepository() extends IdRepository[Point, PointReg] {
  val q = TableQuery[PointReg]

  def allJoined(): Future[Seq[(Point, PointType, TrustLevel)]] = {
    val eventualPoints: Future[Seq[(Point, PointType, TrustLevel)]] = run(q
      .join(TableQuery[PointTypeReg])
      .on(_.pointtypeid === _.id)
      .join(TableQuery[TrustLevelReg])
      .on(_._1.trustlevelid === _.id)
      .map{case ((p, pt), t) => (p, pt, t)}
    )
    eventualPoints
  }
}
