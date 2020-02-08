package desktop.utils.converters

import javafx.util.StringConverter

class DoubleConverter extends StringConverter[Double] {
  override def toString(`object`: Double) = `object`.toString()

  override def fromString(string: String) = string.toDouble
}
