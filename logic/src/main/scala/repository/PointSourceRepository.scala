package repository

import model.PointSource
import repository.registration.PointSourceReg
import slick.lifted.TableQuery

case class PointSourceRepository() extends IdRepository[PointSource, PointSourceReg] {
  val q = TableQuery[PointSourceReg]
}
