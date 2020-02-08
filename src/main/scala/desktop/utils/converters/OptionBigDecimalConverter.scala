package desktop.utils.converters

class OptionBigDecimalConverter extends OptionConverter[BigDecimal] {
  override def fromString(string: String): Option[BigDecimal] = {
    if (string.trim.isEmpty) None else Option(BigDecimal(string))
  }
}
