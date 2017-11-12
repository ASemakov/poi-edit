package repository

import java.io.File

import model.Point

import scala.xml.XML
import scalaxb.fromXML
import scalaxb.generated.{GpxType, WptType}

case class GPXRepository(file: File) {
  def readWpt(): Seq[Point] = {
    val xml = XML.loadFile(file)
    val gpx = fromXML[GpxType](xml)
    gpx.wpt.map(w => Point(None, w.name.getOrElse(""), w.lat, w.lon, w.ele, None, w.desc, 0, 0, None))
  }
}
