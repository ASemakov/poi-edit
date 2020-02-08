package model

case class PointSource(id: Option[Int], name: String) extends IDictEntity

object PointSource extends IDictEntityCompanion[PointSource]
