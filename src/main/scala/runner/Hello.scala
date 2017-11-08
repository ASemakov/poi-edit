package runner

import model.{Category, PointType, Region, TrustLevel}
import repository._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object Hello {

  def main(args: Array[String]) = {
    val db = Database.forConfig("mydb")

    Await.result(Future.sequence(Seq(
      PointType(Option(0), "Неизвестно"),
      PointType(Option(1), "Пещера"),
      PointType(Option(2), "Лесные ягоды, плоды и грибы"),
      PointType(Option(3), "Родники и источники воды"),
      PointType(Option(4), "Стоянки"),
      PointType(Option(5), "Организации")
    ).map(e => PointTypeRepository().save(e))), Duration.Inf).foreach(println)

    Await.result(Future.sequence(Seq(
      TrustLevel(-1, "Не найдено"),
      TrustLevel(0, "Недоверенный источник"),
      TrustLevel(1, "Доверенный источник"),
      TrustLevel(2, "Был 1 раз"),
      TrustLevel(3, "Был 2 и более раз")
    ).map(e => TrustLevelRepository().save(e))), Duration.Inf).foreach(println)

    Await.result(Future.sequence(Seq(
      Region(1, "Агармышский массив"),
      Region(2, "Ай-Петринский массив"),
      Region(3, "Бабуганский массив"),
      Region(4, "Внутренняя Гряда"),
      Region(5, "Демерджийский массив"),
      Region(6, "Долгоруковский массив"),
      Region(7, "Карабийский массив"),
      Region(8, "Морское побережье"),
      Region(9, "Никитский массив"),
      Region(10, "Чатырдагский массив"),
      Region(11, "Ялтинский массив")
    ).map(e => RegionRepository().save(e))), Duration.Inf).foreach(println)

    Await.result(Future.sequence(Seq(
      Category(1, "1"),
      Category(2, "2-А"),
      Category(3, "2-Б"),
      Category(4, "3-А"),
      Category(5, "3-Б"),
      Category(6, "4-А"),
      Category(7, "б/к")
    ).map(e => CategoryRepository().save(e))), Duration.Inf).foreach(println)

  }
}

