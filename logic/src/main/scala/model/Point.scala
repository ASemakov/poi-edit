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
  sourceid : Option[Int],
  dataid: Option[Int]
) extends IEntity
