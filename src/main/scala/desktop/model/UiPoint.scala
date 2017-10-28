package desktop.model

import javafx.beans.property.{SimpleIntegerProperty, SimpleObjectProperty, SimpleStringProperty}

import model.Point

case class UiPoint
(
  idProperty: SimpleObjectProperty[Option[Int]],
  nameProperty: SimpleStringProperty,
  latProperty: SimpleObjectProperty[BigDecimal],
  lonProperty: SimpleObjectProperty[BigDecimal],
  altitudeProperty: SimpleObjectProperty[Option[BigDecimal]],
  precisionProperty: SimpleObjectProperty[Option[BigDecimal]],
  descriptionProperty: SimpleObjectProperty[Option[String]],
  pointtypeidProperty: SimpleIntegerProperty,
  trustlevelidProperty: SimpleIntegerProperty,
  dataidProperty: SimpleObjectProperty[Option[Int]]

){

}

object UiPoint{
  def apply
  (
    id: Option[Int],
    name: String,
    lat: BigDecimal,
    lon: BigDecimal,
    altitude: Option[BigDecimal],
    precision: Option[BigDecimal],
    description: Option[String],
    pointtypeid: Int,
    trustlevelid: Int,
    dataid: Option[Int]

  ): UiPoint = UiPoint(
    new SimpleObjectProperty[Option[Int]](id),
    new SimpleStringProperty(name),
    new SimpleObjectProperty[BigDecimal](lat),
    new SimpleObjectProperty[BigDecimal](lon),
    new SimpleObjectProperty[Option[BigDecimal]](altitude),
    new SimpleObjectProperty[Option[BigDecimal]](precision),
    new SimpleObjectProperty[Option[String]](description),
    new SimpleIntegerProperty(pointtypeid),
    new SimpleIntegerProperty(trustlevelid),
    new SimpleObjectProperty[Option[Int]](dataid)
  )

  def apply(p: Point): UiPoint = UiPoint(
    p.id, p.name, p.lat, p.lon, p.altitude, p.precision, p.description, p.pointtypeid, p.trustlevelid, p.dataid
  )
}