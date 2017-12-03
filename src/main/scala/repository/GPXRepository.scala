package repository

import java.io.File

import model.{Point, Track, TrackPoint}

import scala.xml.XML
import scalaxb.fromXML
import scalaxb.generated.{GpxType, WptType}

case class GPXRepository(file: File) {
  private def readGpx = {
    val xml = XML.loadFile(file)
    fromXML[GpxType](xml)
  }

  def readWpt(): Seq[Point] = {
    val gpx = readGpx
    gpx.wpt.map(w => Point(None, w.name.getOrElse(""), w.lat, w.lon, w.ele, None, w.cmt, 0, 0, None))
  }

  def readTrk(): Seq[(Track, Seq[TrackPoint])] = {
    val gpx = readGpx
    gpx.trk.map(t => Track(None, t.name.getOrElse("No")) -> Nil)
  }
}
