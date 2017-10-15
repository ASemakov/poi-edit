package example
import registration._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait Greeting {
  lazy val greeting: String = "hello"
}

object Hello extends Greeting {

  def main(args: Array[String]) = {
    val db = Database.forConfig("mydb")
    println(Await.result(db.run(TableQuery[RegionReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[CategoryReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[TrustLevelReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[PointTypeReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[KadastrReg].result), Duration.Inf))
    println(Await.result(db.run(TableQuery[PointReg].result), Duration.Inf))
    println(greeting)
  }
}

