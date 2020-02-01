package model

import java.sql.Timestamp

case class TrackPoint
(
  id: Option[Int],
  trackId: Int,
  lat: BigDecimal,
  lon: BigDecimal,
  altitude: Option[BigDecimal],
  time: Timestamp
) extends IEntity
