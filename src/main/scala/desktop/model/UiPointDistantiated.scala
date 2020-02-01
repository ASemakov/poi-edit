package desktop.model

import javafx.beans.property.{DoubleProperty, SimpleDoubleProperty, SimpleObjectProperty, SimpleStringProperty}
import model.{Point, PointSource, PointType, TrustLevel}

class UiPointDistantiated
(
  idProperty: SimpleObjectProperty[Option[Int]],
  nameProperty: SimpleStringProperty,
  latProperty: SimpleObjectProperty[BigDecimal],
  lonProperty: SimpleObjectProperty[BigDecimal],
  altitudeProperty: SimpleObjectProperty[Option[BigDecimal]],
  precisionProperty: SimpleObjectProperty[Option[BigDecimal]],
  descriptionProperty: SimpleObjectProperty[Option[String]],
  pointtypeProperty: SimpleObjectProperty[PointType],
  trustlevelProperty: SimpleObjectProperty[TrustLevel],
  sourceProperty: SimpleObjectProperty[Option[PointSource]],
  dataidProperty: SimpleObjectProperty[Option[Int]],
  val distanceProperty: SimpleObjectProperty[Double]
) extends UiPoint(idProperty, nameProperty, latProperty, lonProperty, altitudeProperty, precisionProperty, descriptionProperty, pointtypeProperty,
  trustlevelProperty, sourceProperty, dataidProperty){
}


object UiPointDistantiated {
  def apply(p: Point, t: PointType, l: TrustLevel, s: Option[PointSource], distance: Double): UiPointDistantiated = UiPointDistantiated(
    p.id, p.name, p.lat, p.lon, p.altitude, p.precision, p.description, t, l, s, p.dataid, distance
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
    dataid: Option[Int],
    distance: Double

  ): UiPointDistantiated = new UiPointDistantiated(
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
    new SimpleObjectProperty[Option[Int]](dataid),
    new SimpleObjectProperty[Double](distance)
  )
}
