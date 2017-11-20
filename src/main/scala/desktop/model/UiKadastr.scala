package desktop.model

import javafx.beans.property.SimpleObjectProperty

import model.{Category, Region}

class UiKadastr(
                 val idProperty: SimpleObjectProperty[Option[Int]],
                 val numProperty: SimpleObjectProperty[Option[String]],
                 val num2Property: SimpleObjectProperty[Option[String]],
                 val nameProperty: SimpleObjectProperty[Option[String]],
                 val lProperty: SimpleObjectProperty[Option[Double]],
                 val aProperty: SimpleObjectProperty[Option[Double]],
                 val vProperty: SimpleObjectProperty[Option[Double]],
                 val regionProperty: SimpleObjectProperty[Option[Region]],
                 val categoryProperty: SimpleObjectProperty[Option[Category]],
                 val commentProperty: SimpleObjectProperty[Option[String]]
               )

object UiKadastr {
  def apply(id: Option[Int], num: Option[String], num2: Option[String], name: Option[String], l: Option[Double],
            a: Option[Double], v: Option[Double], region: Option[Region], category: Option[Category],
            comment: Option[String]): UiKadastr = new UiKadastr(
    new SimpleObjectProperty[Option[Int]](id),
    new SimpleObjectProperty[Option[String]](num),
    new SimpleObjectProperty[Option[String]](num2),
    new SimpleObjectProperty[Option[String]](name),
    new SimpleObjectProperty[Option[Double]](l),
    new SimpleObjectProperty[Option[Double]](a),
    new SimpleObjectProperty[Option[Double]](v),
    new SimpleObjectProperty[Option[Region]](region),
    new SimpleObjectProperty[Option[Category]](category),
    new SimpleObjectProperty[Option[String]](comment)
  )
}