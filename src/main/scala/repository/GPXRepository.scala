package repository

import java.io.{File, FileOutputStream}
import java.time.LocalDate

import generated._
import javax.print.attribute.DateTimeSyntax
import javax.xml.datatype.{DatatypeFactory, XMLGregorianCalendar}
import model.{Point, Track, TrackPoint}

import scala.xml.XML
import scalaxb._


case class GPXRepository(file: File) {
  private def readGpx = {
    val xml = XML.loadFile(file)
    fromXML[GpxType](xml)
  }

  def readWpt(): Seq[Point] = {
    val gpx = readGpx
    gpx.wpt.map(w => Point(None, w.name.getOrElse(""), w.lat, w.lon, w.ele, None, w.cmt, 0, 0, None))
  }

  def writeWpt(points: Seq[Point]): Unit = {
    val gpx = GpxType(
      metadata = Some(MetadataType(
        time = Some(DatatypeFactory.newInstance.newXMLGregorianCalendar(LocalDate.now.toString)),
        bounds = Some(BoundsType(attributes = Map[String, scalaxb.DataRecord[Any]](
          "@minlat" -> DataRecord(points.map(_.lat).min),
          "@minlon" -> DataRecord(points.map(_.lon).min),
          "@maxlat" -> DataRecord(points.map(_.lat).max),
          "@maxlon" -> DataRecord(points.map(_.lon).max)
        )))
      )),
      wpt = points.map(x => WptType(
        ele = x.altitude,
        name = Some(s"${x.trustlevelid}${x.pointtypeid}-${x.name}"),
        attributes = Map[String, scalaxb.DataRecord[Any]](
          "@lat" -> DataRecord(x.lat),
          "@lon" -> DataRecord(x.lon)
        )
      ))
    )
    val xml = toXML[GpxType](gpx, "gpx", defaultScope)
    XML.save(file.getAbsolutePath, xml.head, "utf-8", xmlDecl = true, null)
  }

  def readTrk(): Seq[(Track, Seq[TrackPoint])] = {
    val gpx = readGpx
    gpx.trk.map(t => Track(None, t.name.getOrElse("No")) -> Nil)
  }
}
