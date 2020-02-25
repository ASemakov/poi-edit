package desktop.utils.converters

class OptionStringConverter extends OptionConverter[String] {
  override def fromString(string: String): Option[String] = {
    if (string.trim.isEmpty) None else Option(string)
  }
}
