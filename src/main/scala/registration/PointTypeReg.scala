package registration

import models.PointType
import slick.jdbc.PostgresProfile.api._

class PointTypeReg(tag: Tag) extends IdNameTable[PointType](tag, "pointtype") {
  def * = (id.?, name) <> (PointType.tupled, PointType.unapply)
}
