package desktop.utils


class OptionComparator[T] (implicit conv: T => Ordered[T]) extends Ordering[Option[T]] {
  override def compare(o1: Option[T], o2: Option[T]): Int = {
    o1 match {
      case None => o2 match {
        case None => 0
        case Some(_) => -1
      }
      case Some(v1) => o2 match {
        case None => 1
          case Some(v2) => v1.compareTo(v2)
      }
    }
  }
}

