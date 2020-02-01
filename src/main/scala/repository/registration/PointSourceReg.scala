package repository.registration

import model.PointSource
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

class PointSourceReg(tag: Tag) extends IdTable[PointSource](tag, "pointsource"){

  def name = column[String]("name", O.Length(1024, varying = true)) // character varying(1024) NOT NULL,

  override def * = (id.?, name) <> (PointSource.tupled, PointSource.unapply)

}
