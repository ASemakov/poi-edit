package desktop.utils.converters

class OptionDoubleConverter extends OptionConverter[Double] {
  override def fromString(string: String): Option[Double] = {
    if (string.trim.isEmpty) None else Option(string.toDouble)
  }
}
