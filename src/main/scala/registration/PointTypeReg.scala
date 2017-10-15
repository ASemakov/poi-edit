package registration

import models.PointType
import slick.jdbc.PostgresProfile.api._

class PointTypeReg(tag: Tag) extends Table[PointType](tag, "pointtype") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name", O.Unique, O.Length(200, varying = true))

  def * = (id.?, name) <> (PointType.tupled, PointType.unapply)
}
