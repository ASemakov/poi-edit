package registration

import models.Category
import slick.jdbc.PostgresProfile.api._

class CategoryReg(tag: Tag) extends IdNameTable[Category](tag, "category"){
  def * = (id.?, name) <> (Category.tupled, Category.unapply)
}