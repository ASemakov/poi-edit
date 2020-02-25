package desktop.utils.converters

import javafx.util.StringConverter

import model.IDictEntity

class OptionIdNameStringConverter[T <: IDictEntity] extends StringConverter[Option[T]] {
  override def toString(`object`: Option[T]) = `object`.map(_.name).getOrElse("")

  override def fromString(string: String) = ???
}
