package repository

import java.io._
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import model.PointSource
import model.export.ExportObject
import util.JSON

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case class ExportRepository(file: File) {
  protected def getReader: Reader = {
    val input = new FileInputStream(file)
    new InputStreamReader(
      if (file.getName.endsWith(".gz")) {
        new GZIPInputStream(input)
      } else {
        input
      }
    )
  }

  protected def getWriter: Writer = {
    val out = new FileOutputStream(file)
    new OutputStreamWriter(
      if (file.getName.endsWith(".gz")) {
        new GZIPOutputStream(out)
      } else {
        out
      }
    )
  }

  def backup(): Future[File] = {
    for {
      category <- CategoryRepository().all()
      region <- RegionRepository().all()
      pointType <- PointTypeRepository().all()
      trustLevel <- TrustLevelRepository().all()
      pointSource <- PointSourceRepository().all()
      kadastr <- KadastrRepository().all()
      point <- PointRepository().all()
    } yield {
      val ex = ExportObject(category, region, pointType, trustLevel, pointSource, kadastr, point)
      file.createNewFile()
      val writer = getWriter
      JSON.writePretty(ex, writer)
      writer.close()
      file
    }
  }

  def restore(): Future[File] = {
    val reader = getReader
    val export = JSON.read[ExportObject](reader)
    reader.close()

    for {
      // Truncate tables
      _ <- PointRepository().truncate()
      _ <- KadastrRepository().truncate()
      _ <- PointSourceRepository().truncate()
      _ <- CategoryRepository().truncate()
      _ <- RegionRepository().truncate()
      _ <- PointTypeRepository().truncate()
      _ <- TrustLevelRepository().truncate()

      // Insert values
      _ <- CategoryRepository().insertAll(export.category)
      _ <- RegionRepository().insertAll(export.region)
      _ <- PointTypeRepository().insertAll(export.pointType)
      _ <- TrustLevelRepository().insertAll(export.trustLevel)
      _ <- PointSourceRepository().insertAll(export.pointSource)
      _ <- KadastrRepository().insertAll(export.kadastr)
      _ <- PointRepository().insertAll(export.point)
    } yield file
  }
}
