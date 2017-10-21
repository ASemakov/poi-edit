package repository

import model.Point
import registration.PointReg
import slick.lifted.TableQuery

case class PointRepository() extends IdRepository[Point, PointReg] {
  val q = TableQuery[PointReg]
}
