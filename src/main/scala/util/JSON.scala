package util

import java.io.File

import org.json4s._
import org.json4s.native.Serialization

import scala.reflect.Manifest

object JSON {
  implicit val formats: Formats = DefaultFormats

  def write[T <: AnyRef](obj: T): String = {
    Serialization.write[T](obj)
  }

  def writePretty[T <: AnyRef](obj: T): String = {
    Serialization.writePretty[T](obj)
  }

  def read[T](string: String)(implicit mf: Manifest[T]): T = {
    Serialization.read(string)
  }

  def read[T](file: File)(implicit mf: Manifest[T]): T = {
    Serialization.read(FileInput(file))
  }
}
