package repository

import model.PointType
import registration.PointTypeReg
import slick.lifted.TableQuery

case class PointTypeRepository() extends IdNameRepository[PointType, PointTypeReg]{
  val q = TableQuery[PointTypeReg]
}
