package desktop.utils.converters

import javafx.util.StringConverter

class BigDecimalConverter extends StringConverter[BigDecimal] {
  override def toString(`object`: BigDecimal) = `object`.toString()

  override def fromString(string: String) = BigDecimal(string)
}
