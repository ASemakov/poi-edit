package registration

import model.IDictEntity
import slick.jdbc.PostgresProfile.api._

abstract class IdNameTable[T <: IDictEntity](_tableTag: Tag, _schemaName: Option[String], _tableName: String) extends IdTable[T](_tableTag: Tag, _schemaName: Option[String], _tableName: String){
  def this(_tableTag: Tag, _tableName: String) = this(_tableTag, None, _tableName)

  override def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name", O.Unique, O.Length(200, varying = true))
}
