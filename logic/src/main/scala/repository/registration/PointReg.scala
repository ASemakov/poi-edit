package repository.registration

import java.sql.Timestamp

import model.{Point, Track, TrackPoint}
import slick.jdbc.PostgresProfile.api._

class PointReg(tag: Tag) extends IdTable[Point](tag, "point") {
  def name = column[String]("name", O.Length(14, varying = true)) // character varying(14) NOT NULL,
  def lat = column[BigDecimal]("lat", O.SqlType("NUMERIC(11, 8)")) // numeric(11,8) NOT NULL,
  def lon = column[BigDecimal]("lon", O.SqlType("NUMERIC(11, 8)")) // numeric(11,8) NOT NULL,
  def altitude = column[Option[BigDecimal]]("altitude", O.SqlType("NUMERIC(7, 2)")) // numeric(7,2),
  def precision = column[Option[BigDecimal]]("precision", O.SqlType("NUMERIC(3, 1)")) // numeric(3,1),
  def description = column[Option[String]]("description", O.Length(50, varying = true)) // character varying(50),
  def pointtypeid = column[Int]("pointtypeid") // integer NOT NULL,
  def trustlevelid = column[Int]("trustlevelid") // integer NOT NULL,
  def sourceid = column[Option[Int]]("sourceid") // integer
  def dataid = column[Option[Int]]("dataid") // integer,

  //Foreign keys
  def pointtype_fk = foreignKey("fk_point_pointtypeid", pointtypeid, TableQuery[PointTypeReg])(_.id)
  def trustlevel = foreignKey("fk_point_trustlevelid", trustlevelid, TableQuery[TrustLevelReg])(_.id)
  def source = foreignKey("fk_point_pointsource", sourceid, TableQuery[PointSourceReg])(_.id.?)



  def * = (id.?, name, lat, lon, altitude, precision, description, pointtypeid, trustlevelid, sourceid, dataid) <> (Point.tupled, Point.unapply)

}

class TrackReg(tag: Tag) extends IdTable[Track](tag, "track") {
  def name = column[String]("name", O.Length(250, varying = true)) // character varying(14) NOT NULL,
  def * = (id.?, name) <> (Track.tupled, Track.unapply)
}

class TrackPointReg(tag: Tag) extends IdTable[TrackPoint](tag, "track_point") {
  def trackid = column[Int]("track_id")
  def lat = column[BigDecimal]("lat", O.SqlType("NUMERIC(11, 8)")) // numeric(11,8) NOT NULL,
  def lon = column[BigDecimal]("lon", O.SqlType("NUMERIC(11, 8)")) // numeric(11,8) NOT NULL,
  def altitude = column[Option[BigDecimal]]("altitude", O.SqlType("NUMERIC(7, 2)")) // numeric(7,2),
  def time = column[Timestamp]("time")

  //Foreign keys
  def track = foreignKey("fk_track_point_track_id", trackid, TableQuery[TrackReg])(_.id)

  override def * = (id.?, trackid, lat, lon, altitude, time) <> (TrackPoint.tupled, TrackPoint.unapply)
}