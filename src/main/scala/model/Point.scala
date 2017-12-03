package model

import java.sql.Timestamp

case class Point
(
  id: Option[Int],
  name: String,
  lat: BigDecimal,
  lon: BigDecimal,
  altitude: Option[BigDecimal],
  precision: Option[BigDecimal],
  description: Option[String],
  pointtypeid: Int,
  trustlevelid: Int,
  dataid: Option[Int]
) extends IEntity

case class Track
(
  id: Option[Int],
  name: String,
) extends IEntity

case class TrackPoint
(
  id: Option[Int],
  trackId: Int,
  lat: BigDecimal,
  lon: BigDecimal,
  altitude: Option[BigDecimal],
  time: Timestamp
) extends IEntity