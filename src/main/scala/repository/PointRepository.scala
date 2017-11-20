package repository

import model.{Point, PointType, TrustLevel}
import repository.registration.{PointReg, PointTypeReg, TrustLevelReg}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

case class PointRepository() extends IdRepository[Point, PointReg] {
  val q = TableQuery[PointReg]

  def allJoined(): Future[Seq[(Point, PointType, TrustLevel)]] = {
    val eventualPoints: Future[Seq[(Point, PointType, TrustLevel)]] = run(q
      .join(TableQuery[PointTypeReg])
      .on(_.pointtypeid === _.id)
      .join(TableQuery[TrustLevelReg])
      .on(_._1.trustlevelid === _.id)
      .map { case ((p, pt), t) => (p, pt, t) }
    )
    eventualPoints
  }

  def topNByDistance(lat: BigDecimal, lon: BigDecimal, count: Int): Future[Seq[(Point, PointType, TrustLevel, Double)]] = {
    val r: Future[Seq[(Point, PointType, TrustLevel, Double)]] = run(q
      .join(TableQuery[PointTypeReg])
      .on(_.pointtypeid === _.id)
      .join(TableQuery[TrustLevelReg])
      .on(_._1.trustlevelid === _.id)
      .map { case ((p, pt), t) => (p, pt, t) }
    )
      .map(_
        .map { case (p, t, l) => (
          p, t, l, calcDistance(lat, lon, p.lat, p.lon))
        }
        .sortBy { case (p, t, l, d) => d }
        .take(count)
      )
    r
  }

  private def calcDistance(lat: BigDecimal, lon: BigDecimal, lat2: BigDecimal, lon2: BigDecimal) = {
    Math.acos(
      Math.sin((lat * Math.PI / 180).toDouble) * Math.sin((lat2 * Math.PI / 180.0).toDouble)
        + Math.cos((lat * Math.PI / 180).toDouble) * Math.cos((lat2 * Math.PI / 180.0).toDouble) * Math.cos(((lon - lon2) * Math.PI / 180).toDouble)
    ) * 6371000.0
  }
}
