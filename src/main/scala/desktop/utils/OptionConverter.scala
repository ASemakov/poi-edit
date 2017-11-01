package desktop.utils

import javafx.util.StringConverter

abstract class OptionConverter[T] extends StringConverter[Option[T]] {
  override def toString(`object`: Option[T]) = {
    `object`.map(_.toString).getOrElse("")
  }

}
