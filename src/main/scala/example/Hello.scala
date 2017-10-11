package example
import slick.jdbc.PostgresProfile.api._

object Hello extends Greeting with App {
  val db = Database.forConfig("mydb")

  println(greeting)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
