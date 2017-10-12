package registration

import slick.jdbc.PostgresProfile.api._
import models.{Category, PointType, Region, TrustLevel}


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

