package repository

import model.TrustLevel
import registration.TrustLevelReg
import slick.lifted.TableQuery

case class TrustLevelRepository() extends IdNameRepository[TrustLevel, TrustLevelReg]{
  val q = TableQuery[TrustLevelReg]
}
