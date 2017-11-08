package model

trait IDictEntity extends IEntity {
  val name: String
}

trait IDictEntityCompanion[T <: IDictEntity] extends ((Option[Int], String) => T){
  def apply(id: Int, name: String): T = apply(Option(id), name)
}