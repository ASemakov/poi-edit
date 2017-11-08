package model

case class Category(id: Option[Int], name: String) extends IDictEntity


object Category extends IDictEntityCompanion[Category]