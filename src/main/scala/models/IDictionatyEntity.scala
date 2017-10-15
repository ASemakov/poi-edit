package models

trait IEntity{
  val id: Option[Int]
}


trait IDictionatyEntity extends IEntity{
  val name: String
}
