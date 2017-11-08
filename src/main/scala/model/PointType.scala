package model

case class PointType(id: Option[Int], name: String) extends IDictEntity


object PointType extends IDictEntityCompanion[PointType]
