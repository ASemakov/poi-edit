package desktop.model

import javafx.beans.property.{SimpleObjectProperty, SimpleStringProperty}
import model.{Point, PointSource, PointType, TrustLevel}


class UiPoint
(
  val idProperty: SimpleObjectProperty[Option[Int]],
  val nameProperty: SimpleStringProperty,
  val latProperty: SimpleObjectProperty[BigDecimal],
  val lonProperty: SimpleObjectProperty[BigDecimal],
  val altitudeProperty: SimpleObjectProperty[Option[BigDecimal]],
  val precisionProperty: SimpleObjectProperty[Option[BigDecimal]],
  val descriptionProperty: SimpleObjectProperty[Option[String]],
  val pointtypeProperty: SimpleObjectProperty[PointType],
  val trustlevelProperty: SimpleObjectProperty[TrustLevel],
  val source: SimpleObjectProperty[Option[PointSource]],
  val dataidProperty: SimpleObjectProperty[Option[Int]]
) {

  private def currentValues = (
    nameProperty.get(), latProperty.get(), lonProperty.get(), altitudeProperty.get(), precisionProperty.get(),
    descriptionProperty.get(), pointtypeProperty.get(), trustlevelProperty.get(), source.get(), dataidProperty.get()
  )

  private val original = currentValues

  def isChanged: Boolean = currentValues != original

  def isNew: Boolean = idProperty.get().isEmpty

  def asPoint: Point = Point(idProperty.get(), nameProperty.get(), latProperty.get, lonProperty.get(),
    altitudeProperty.get(), precisionProperty.get(), descriptionProperty.get(), pointtypeProperty.get().id.get,
    trustlevelProperty.get().id.get, dataidProperty.get(), None)
}

object UiPoint {
  def apply(p: Point, t: PointType, l: TrustLevel, s: Option[PointSource]): UiPoint = UiPoint(
    p.id, p.name, p.lat, p.lon, p.altitude, p.precision, p.description, t, l, s, p.dataid
  )

  def apply
  (
    id: Option[Int],
    name: String,
    lat: BigDecimal,
    lon: BigDecimal,
    altitude: Option[BigDecimal],
    precision: Option[BigDecimal],
    description: Option[String],
    pointtype: PointType,
    trustlevel: TrustLevel,
    source: Option[PointSource],
    dataid: Option[Int]

  ): UiPoint = new UiPoint(
    new SimpleObjectProperty[Option[Int]](id),
    new SimpleStringProperty(name),
    new SimpleObjectProperty[BigDecimal](lat),
    new SimpleObjectProperty[BigDecimal](lon),
    new SimpleObjectProperty[Option[BigDecimal]](altitude),
    new SimpleObjectProperty[Option[BigDecimal]](precision),
    new SimpleObjectProperty[Option[String]](description),
    new SimpleObjectProperty[PointType](pointtype),
    new SimpleObjectProperty[TrustLevel](trustlevel),
    new SimpleObjectProperty[Option[PointSource]](source),
    new SimpleObjectProperty[Option[Int]](dataid)
  )
}


