package registration

import models.Region
import slick.jdbc.PostgresProfile.api._

class RegionReg(tag: Tag) extends Table[Region](tag, "region") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name", O.Unique, O.Length(200, varying = true))

  def * = (id.?, name) <> (Region.tupled, Region.unapply)
}
