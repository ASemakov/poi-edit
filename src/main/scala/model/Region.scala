package model

case class Region(id: Option[Int], name: String) extends IDictEntity

object Region extends IDictEntityCompanion[Region]
