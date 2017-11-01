package registration

import model.Kadastr
import slick.jdbc.PostgresProfile.api._

class KadastrReg(tag: Tag) extends IdTable[Kadastr](tag, "kadastr") {

  //Foreign keys
  def region = foreignKey("fk_kadastr_regionid", regionid, TableQuery[RegionReg])(_.id.?)

  def regionid = column[Option[Int]]("regionid") //integer,

  def category = foreignKey("fk_kadastr_categoryid", categoryid, TableQuery[CategoryReg])(_.id.?)

  def categoryid = column[Option[Int]]("categoryid") //integer,

  def * = (id.?, num, num2, name, l, a, v, regionid, categoryid, comment) <> (Kadastr.tupled, Kadastr.unapply)

  def num = column[Option[String]]("num", O.Length(7, varying = true)) //character varying(7),

  def num2 = column[Option[String]]("num2", O.Length(10, varying = true)) //character varying(10),

  def name = column[Option[String]]("name", O.Length(50, varying = true)) //character varying(50),

  def l = column[Option[Double]]("l") //double precision,

  def a = column[Option[Double]]("a") //double precision,

  def v = column[Option[Double]]("v") //double precision,

  def comment = column[Option[String]]("comment", O.Length(150, varying = true)) //character varying(150),
}
