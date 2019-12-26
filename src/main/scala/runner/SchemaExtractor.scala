package runner

import model.Category
import repository.registration._
import scopt.Read
import slick.jdbc.PostgresProfile.api._
import slick.lifted

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object SchemaExtractor {

  object ExtractorActions extends Enumeration {
    type ExtractorActions = Value
    val create, drop, truncate = Value
  }

  case class SchemaExtractorParams(action: ExtractorActions.Value = ExtractorActions.create, execute: Boolean = false)

  implicit val extractorActionsRead: Read[ExtractorActions.Value] = scopt.Read.reads(ExtractorActions.withName)

  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[SchemaExtractorParams]("SchemaExtractor") {
      head("SchemaExtractor")
      opt[ExtractorActions.Value]('a', "action").action((x, c) => c.copy(action = x))
      opt[Unit]('x', "execute").action((x, c) => c.copy(execute = true))
    }
    parser.parse(args, SchemaExtractorParams()) match {
      case Some(config) => main(config)
      case None => // arguments are bad, error message will have been displayed
    }
  }

  protected def main(config: SchemaExtractorParams):Unit = {
    val tables = Seq(
      TableQuery[RegionReg],
      TableQuery[CategoryReg],
      TableQuery[TrustLevelReg],
      TableQuery[PointTypeReg],
      TableQuery[KadastrReg],
      TableQuery[PointReg],
      TableQuery[TrackReg],
      TableQuery[TrackPointReg]
    )
    val statements = config.action match {
      case ExtractorActions.create => tables.map(_.schema.create)
      case ExtractorActions.drop => tables.map(_.schema.drop)
      case ExtractorActions.truncate => tables.map(_.schema.truncate)
    }
//    val populates = config.action match  {
//      case ExtractorActions.create => {
//          Seq(
//            TableQuery[CategoryReg] ++= Seq(
//              Category(1, "1"),
//              Category(2, "2"),
//              Category(3, "3"),
//              Category(4, "4"),
//            )
//          )
//      }

      case _ => Seq()
    }

    if (config.execute) {
      val db = Database.forConfig("mydb")
      Await.result(db.run(DBIO.seq(statements ++ populates: _*)), Duration.Inf)
    } else {
      (statements ++ populates).flatMap(_.statements).foreach(println)
    }
  }
}

