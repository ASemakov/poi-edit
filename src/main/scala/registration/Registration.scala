package registration

import javax.swing.plaf.OptionPaneUI

import slick.jdbc.PostgresProfile.api._
import models._


class RegionReg(tag: Tag) extends Table[Region](tag, "region") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name", O.Unique, O.Length(200, varying = true))
  def * = (id.?, name) <> (Region.tupled, Region.unapply)
}

class PointTypeReg(tag: Tag) extends Table[PointType](tag, "pointtype") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name", O.Unique, O.Length(200, varying = true))
  def * = (id.?, name) <> (PointType.tupled, PointType.unapply)
}

class TrustLevelReg(tag: Tag) extends Table[TrustLevel](tag, "trustlevel") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name", O.Unique, O.Length(200, varying = true))
  def * = (id.?, name) <> (TrustLevel.tupled, TrustLevel.unapply)
}

class CategoryReg(tag: Tag) extends Table[Category](tag, "category") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name", O.Unique, O.Length(200, varying = true))
  def * = (id.?, name) <> (Category.tupled, Category.unapply)
}

class KadastrReg(tag: Tag) extends Table[Kadastr](tag, "kadastr") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def num = column[Option[String]]("num", O.Length(7, varying = true))  //character varying(7),
  def num2 = column[Option[String]]("num2", O.Length(10, varying = true))  //character varying(10),
  def name = column[Option[String]]("name", O.Length(50, varying = true))  //character varying(50),
  def l = column[Option[Double]]("l")  //double precision,
  def a = column[Option[Double]]("a")  //double precision,
  def v = column[Option[Double]]("v")  //double precision,
  def regionid = column[Option[Int]]("regionid")  //integer,
  def categoryid = column[Option[Int]]("categoryid")  //integer,
  def comment = column[Option[String]]("comment", O.Length(150, varying = true))  //character varying(150),

  //Foreign keys
  def region = foreignKey("fk_kadastr_regionid", regionid, TableQuery[RegionReg])(_.id)
  def category = foreignKey("fk_kadastr_categoryid", categoryid, TableQuery[CategoryReg])(_.id)

  def * = (id.?, num, num2, name, l, a, v, regionid, categoryid, comment) <> (Kadastr.tupled, Kadastr.unapply)
}

class PointReg(tag: Tag) extends Table[Point](tag, "point") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)  // integer NOT NULL DEFAULT nextval('point_id_seq'::regclass),
  def name = column[String]("name", O.Length(14, varying = true))  // character varying(14) NOT NULL,
  def lat = column[BigDecimal]("lat", O.SqlType("NUMERIC(11, 8)"))  // numeric(11,8) NOT NULL,
  def lon = column[BigDecimal]("lon", O.SqlType("NUMERIC(11, 8)"))  // numeric(11,8) NOT NULL,
  def altitude = column[Option[BigDecimal]]("altitude", O.SqlType("NUMERIC(7, 2)"))  // numeric(7,2),
  def precision = column[Option[BigDecimal]]("precision", O.SqlType("NUMERIC(3, 1)"))  // numeric(3,1),
  def description = column[Option[String]]("description", O.Length(50, varying = true))  // character varying(50),
  def pointtypeid = column[Int]("pointtypeid")  // integer NOT NULL,
  def trustlevelid = column[Int]("trustlevelid")  // integer NOT NULL,
  def dataid = column[Option[Int]]("dataid")  // integer,

  //Foreign keys
  def pointtype = foreignKey("fk_point_pointtypeid", pointtypeid, TableQuery[PointReg])(_.id)
  def trustlevel = foreignKey("fk_point_trustlevelid", trustlevelid, TableQuery[TrustLevelReg])(_.id)
//  def kadastr = foreignKey("", dataid, TableQuery[KadastrReg])(_.id)

  def * = (id.?, name, lat, lon, altitude, precision, description, pointtypeid, trustlevelid, dataid) <> (Point.tupled, Point.unapply)
}
