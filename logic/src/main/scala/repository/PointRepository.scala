package repository

import model._
import repository.registration._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class PointRepository() extends IdRepository[Point, PointReg] {
  val q = TableQuery[PointReg]

  def allJoined(): Future[Seq[(Point, PointType, TrustLevel, Option[PointSource])]] = {
    val eventualPoints: Future[Seq[(Point, PointType, TrustLevel, Option[PointSource])]] = run(q
      .join(TableQuery[PointTypeReg])
        .on(_.pointtypeid === _.id)
      .join(TableQuery[TrustLevelReg])
        .on(_._1.trustlevelid === _.id)
      .joinLeft(TableQuery[PointSourceReg])
        .on(_._1._1.sourceid === _.id)
      .map { case (((p, pt), t), s) => (p, pt, t, s) }
    )
    eventualPoints
  }

  def topNByDistance(lat: BigDecimal, lon: BigDecimal, count: Int): Future[Seq[(Point, PointType, TrustLevel, Option[PointSource], Double)]] = {
    val r: Future[Seq[(Point, PointType, TrustLevel, Option[PointSource], Double)]] = run(q
      .join(TableQuery[PointTypeReg])
        .on(_.pointtypeid === _.id)
      .join(TableQuery[TrustLevelReg])
        .on(_._1.trustlevelid === _.id)
      .joinLeft(TableQuery[PointSourceReg])
        .on(_._1._1.sourceid === _.id)
      .map { case (((p, pt), t), s) => (p, pt, t, s) }
    )
      .map(_
        .map { case (p, t, l, s) => (
          p, t, l, s, calcDistance(lat, lon, p.lat, p.lon))
        }
        .sortBy { case (p, t, l, s, d) => d }
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


case class TrackRepository() extends IdRepository[Track, TrackReg] {
  override protected def q = TableQuery[TrackReg]
}


case class TrackPointRepository() extends IdRepository[TrackPoint, TrackPointReg] {
  override protected def q = TableQuery[TrackPointReg]
}