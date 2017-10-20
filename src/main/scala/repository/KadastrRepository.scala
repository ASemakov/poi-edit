package repository

import model.Kadastr
import registration.KadastrReg
import slick.lifted.TableQuery

case class KadastrRepository() extends IdRepository[Kadastr, KadastrReg] {
  val q = TableQuery[KadastrReg]
}
