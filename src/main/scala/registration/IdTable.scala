package registration

import models.IEntity
import slick.jdbc.PostgresProfile.api._

abstract class IdTable[T <: IEntity](_tableTag: Tag, _schemaName: Option[String], _tableName: String) extends Table[T](_tableTag: Tag, _schemaName: Option[String], _tableName: String){
  def this(_tableTag: Tag, _tableName: String) = this(_tableTag, None, _tableName)

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
}
