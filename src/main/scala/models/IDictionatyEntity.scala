package models


trait IDictionatyEntity{
  val id: Option[Int]
  val name: String
}

case class Region(id: Option[Int], name: String) extends IDictionatyEntity
case class PointType(id: Option[Int], name: String) extends IDictionatyEntity
case class TrustLevel(id: Option[Int], name: String) extends IDictionatyEntity
case class Category(id: Option[Int], name: String) extends IDictionatyEntity
