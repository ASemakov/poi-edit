package registration

import models.TrustLevel
import slick.jdbc.PostgresProfile.api._

class TrustLevelReg(tag: Tag) extends Table[TrustLevel](tag, "trustlevel") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name", O.Unique, O.Length(200, varying = true))

  def * = (id.?, name) <> (TrustLevel.tupled, TrustLevel.unapply)
}
