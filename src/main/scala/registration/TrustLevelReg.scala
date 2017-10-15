package registration

import models.TrustLevel
import slick.jdbc.PostgresProfile.api._

class TrustLevelReg(tag: Tag) extends IdNameTable[TrustLevel](tag, "trustlevel") {
  def * = (id.?, name) <> (TrustLevel.tupled, TrustLevel.unapply)
}
