package model.export

import model._

case class ExportObject
(
  category: Seq[Category],
  region: Seq[Region],
  pointType: Seq[PointType],
  trustLevel: Seq[TrustLevel],
  kadastr: Seq[Kadastr],
  point: Seq[Point]
)
