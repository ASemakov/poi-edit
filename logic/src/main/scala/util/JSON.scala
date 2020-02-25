package util

import java.io.{File, Reader, Writer}

import org.json4s._
import org.json4s.native.Serialization

import scala.reflect.Manifest

object JSON {
  implicit val formats: Formats = DefaultFormats

  def write[T <: AnyRef](obj: T): String = {
    Serialization.write[T](obj)
  }

  def write[T <: AnyRef, W <: Writer](obj: T, out: W): W = {
    Serialization.write[T, W](obj, out)
  }

  def writePretty[T <: AnyRef](obj: T): String = {
    Serialization.writePretty[T](obj)
  }

  def writePretty[T <: AnyRef, W <: Writer](obj: T, out: W): W = {
    Serialization.writePretty[T, W](obj, out)
  }

  def read[T](string: String)(implicit mf: Manifest[T]): T = {
    Serialization.read(string)
  }

  def read[T](in: Reader)(implicit mf: Manifest[T]): T = {
    Serialization.read(in)
  }
}
