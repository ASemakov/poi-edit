package desktop.utils.converters

import javafx.util.StringConverter

import model.IDictEntity

class IdNameStringConverter[T <: IDictEntity] extends StringConverter[T] {
  override def toString(`object`: T) = `object`.name

  override def fromString(string: String) = ???
}
