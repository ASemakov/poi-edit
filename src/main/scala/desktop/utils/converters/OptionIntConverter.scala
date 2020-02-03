package desktop.utils.converters

class OptionIntConverter extends OptionConverter[Int] {
  override def fromString(string: String): Option[Int] = {
    if (string.trim.isEmpty) None else Option(string.toInt)
  }
}
