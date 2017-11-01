package registration

import model.Point
import slick.jdbc.PostgresProfile.api._

class PointReg(tag: Tag) extends IdTable[Point](tag, "point") {
  //Foreign keys
  def pointtype_fk = foreignKey("fk_point_pointtypeid", pointtypeid, TableQuery[PointTypeReg])(_.id)

  def pointtypeid = column[Int]("pointtypeid") // integer NOT NULL,

  def trustlevel = foreignKey("fk_point_trustlevelid", trustlevelid, TableQuery[TrustLevelReg])(_.id)

  def trustlevelid = column[Int]("trustlevelid") // integer NOT NULL,

  def * = (id.?, name, lat, lon, altitude, precision, description, pointtypeid, trustlevelid, dataid) <> (Point.tupled, Point.unapply)

  def name = column[String]("name", O.Length(14, varying = true)) // character varying(14) NOT NULL,

  def lat = column[BigDecimal]("lat", O.SqlType("NUMERIC(11, 8)")) // numeric(11,8) NOT NULL,

  def lon = column[BigDecimal]("lon", O.SqlType("NUMERIC(11, 8)")) // numeric(11,8) NOT NULL,

  def altitude = column[Option[BigDecimal]]("altitude", O.SqlType("NUMERIC(7, 2)")) // numeric(7,2),

  def precision = column[Option[BigDecimal]]("precision", O.SqlType("NUMERIC(3, 1)")) // numeric(3,1),

  def description = column[Option[String]]("description", O.Length(50, varying = true)) // character varying(50),

  //  def pointtype = TableQuery[PointTypeReg].filter(_.id === pointtypeid)
  //  def kadastr = foreignKey("", dataid, TableQuery[KadastrReg])(_.id)

  def dataid = column[Option[Int]]("dataid") // integer,
}
