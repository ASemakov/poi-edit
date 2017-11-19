package repository

import java.io.{File, FileReader, FileWriter}

import model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import util.JSON

case class ExportObject(
                         category: Seq[Category], region: Seq[Region], pointType: Seq[PointType],
                         trustLevel: Seq[TrustLevel], kadastr: Seq[Kadastr], point: Seq[Point]
                       )


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
      writer.write(JSON.write(ex))
      writer.close()
      file
    }
  }

  def restore(): Future[File] = {
    val export = JSON.read[ExportObject](file)
    for {
      _ <- CategoryRepository().saveAll(export.category)
      _ <- RegionRepository().saveAll(export.region)
      _ <- PointTypeRepository().saveAll(export.pointType)
      _ <- TrustLevelRepository().saveAll(export.trustLevel)
      _ <- KadastrRepository().saveAll(export.kadastr)
      _ <- PointRepository().saveAll(export.point)
    } yield file
  }
}
