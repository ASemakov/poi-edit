package repository

import model.Category
import registration.CategoryReg
import slick.lifted.TableQuery

case class CategoryRepository() extends IdNameRepository[Category, CategoryReg] {
  val q = TableQuery[CategoryReg]
}
