package repository

import java.io.{File, FileWriter}

import model._
import model.export.ExportObject
import util.JSON

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case class ExportRepository(file: File) {
  def backup(): Future[File] = {
    for {
      category <- CategoryRepository().all()
      region <- RegionRepository().all()
      pointType <- PointTypeRepository().all()
      trustLevel <- TrustLevelRepository().all()
      kadastr <- KadastrRepository().all()
      point <- PointRepository().all()
    } yield {
      val ex = ExportObject(category, region, pointType, trustLevel, kadastr, point)
      file.createNewFile()
      val writer = new FileWriter(file)
      writer.write(JSON.writePretty(ex))
      writer.close()
      file
    }
  }

  def restore(): Future[File] = {
    val export = JSON.read[ExportObject](file)
    for {
      _ <- CategoryRepository().insertAll(export.category)
      _ <- RegionRepository().insertAll(export.region)
      _ <- PointTypeRepository().insertAll(export.pointType)
      _ <- TrustLevelRepository().insertAll(export.trustLevel)
      _ <- KadastrRepository().insertAll(export.kadastr)
      _ <- PointRepository().insertAll(export.point)
    } yield file
  }
}
