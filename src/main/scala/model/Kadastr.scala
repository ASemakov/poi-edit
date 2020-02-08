package model

case class Kadastr(
                    id: Option[Int],
                    num: Option[String],
                    num2: Option[String],
                    name: Option[String],
                    l: Option[Double],
                    a: Option[Double],
                    v: Option[Double],
                    regionid: Option[Int],
                    categoryid: Option[Int],
                    comment: Option[String]
                  ) extends IEntity
