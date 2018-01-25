package desktop

import com.typesafe.config.{Config, ConfigFactory}

case class Cfg (config: Config = ConfigFactory.load()) {
  def googleApiKey = config.getString("googleApiKey")
}
