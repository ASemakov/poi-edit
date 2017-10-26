package runner

import registration._
import scopt.Read
import slick.jdbc.PostgresProfile.api._


object ExtractorActions extends Enumeration{
  type ExtractorActions = Value
  val create, drop, truncate = Value
}

case class SchemaExtractorParams(action: ExtractorActions.Value = ExtractorActions.create)

object SchemaExtractor {
  implicit val extractorActionsRead: Read[ExtractorActions.Value] = scopt.Read.reads(ExtractorActions.withName)

  def main(args: Array[String]) = {
    val parser = new scopt.OptionParser[SchemaExtractorParams]("SchemaExtractor"){
      head("SchemaExtractor")
      opt[ExtractorActions.Value]('a', "action").action((x, c) => c.copy(action = x))
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
      case Some(config) => config.action match {
        case ExtractorActions.create => tables.foreach(_.schema.create.statements.foreach(println))
        case ExtractorActions.drop => tables.foreach(_.schema.drop.statements.foreach(println))
        case ExtractorActions.truncate => tables.foreach(_.schema.truncate.statements.foreach(println))
      }

      case None =>
      // arguments are bad, error message will have been displayed
    }
  }
}

