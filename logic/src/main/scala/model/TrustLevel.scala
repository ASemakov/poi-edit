package model

case class TrustLevel(id: Option[Int], name: String) extends IDictEntity

object TrustLevel extends IDictEntityCompanion[TrustLevel]
