package desktop.utils

import javafx.util.StringConverter

class OptionConverter[T] extends StringConverter[Option[T]] {
  override def toString(`object`: Option[T]) = {
    `object`.map(_.toString).getOrElse("")
  }

  override def fromString(string: String) = ???
}
