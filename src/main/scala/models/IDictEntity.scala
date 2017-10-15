package models

trait IEntity{
  val id: Option[Int]
}


trait IDictEntity extends IEntity{
  val name: String
}
