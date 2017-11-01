package runner

import registration._
import scopt.Read
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object ExtractorActions extends Enumeration{
  type ExtractorActions = Value
  val create, drop, truncate = Value
}

case class SchemaExtractorParams(action: ExtractorActions.Value = ExtractorActions.create, execute: Boolean=false)

object SchemaExtractor {
  implicit val extractorActionsRead: Read[ExtractorActions.Value] = scopt.Read.reads(ExtractorActions.withName)

  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[SchemaExtractorParams]("SchemaExtractor"){
      head("SchemaExtractor")
      opt[ExtractorActions.Value]('a', "action").action((x, c) => c.copy(action = x))
      opt[Unit]('x', "execute").action((x, c) => c.copy(execute = true))
    }
    val tables = Seq(
      TableQuery[RegionReg],
      TableQuery[CategoryReg],
      TableQuery[TrustLevelReg],
      TableQuery[PointTypeReg],
      TableQuery[KadastrReg],
      TableQuery[PointReg]
    )
    parser.parse(args, SchemaExtractorParams()) match {
      case Some(config) =>
        val statements = config.action match {
          case ExtractorActions.create => tables.map(_.schema.create)
          case ExtractorActions.drop => tables.map(_.schema.drop)
          case ExtractorActions.truncate => tables.map(_.schema.truncate)
        }
        if (config.execute) {
          val db = Database.forConfig("mydb")
          Await.result(db.run(DBIO.seq(statements: _*)), Duration.Inf)
        } else {
          statements.flatMap(_.statements).foreach(println)
        }

      case None =>
      // arguments are bad, error message will have been displayed
    }
  }
}

