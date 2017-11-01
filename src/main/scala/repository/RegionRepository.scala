package repository

import model.Region
import registration.RegionReg
import slick.lifted.TableQuery

case class RegionRepository() extends IdNameRepository[Region, RegionReg] {
  val q = TableQuery[RegionReg]
}
