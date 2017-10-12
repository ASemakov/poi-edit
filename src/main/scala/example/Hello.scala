package example
import registration.{CategoryReg, PointTypeReg, RegionReg, TrustLevelReg}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait Greeting {
  lazy val greeting: String = "hello"
}

class Region(tag: Tag) extends Table[(Int, String)](tag, "region") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name", O.Unique, O.Length(200, true))

  override def * = (id, name)
}

object Hello extends Greeting {

  def main(args: Array[String]) = {
    val db = Database.forConfig("mydb")
    val regions = TableQuery[Region]
    val items = regions.map(_.name)
    println(Await.result(db.run(TableQuery[RegionReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[CategoryReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[TrustLevelReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[PointTypeReg].result), Duration.Inf))
    println(greeting)
  }
}

