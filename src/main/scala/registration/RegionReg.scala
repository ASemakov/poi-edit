package registration

import models.Region
import slick.jdbc.PostgresProfile.api._

class RegionReg(tag: Tag) extends IdNameTable[Region](tag, "region") {
  def * = (id.?, name) <> (Region.tupled, Region.unapply)
}
