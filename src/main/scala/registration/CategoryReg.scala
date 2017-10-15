package registration

import models.Category
import slick.jdbc.PostgresProfile.api._

class CategoryReg(tag: Tag) extends Table[Category](tag, "category") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name", O.Unique, O.Length(200, varying = true))

  def * = (id.?, name) <> (Category.tupled, Category.unapply)
}
