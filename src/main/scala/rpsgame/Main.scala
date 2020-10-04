package rpsgame

object Main {
  def main(args: Array[String]): Unit = {
    Config.parse(args.toIndexedSeq: _*) match {
      case Some(config) => Game.play(config.host, config.guest, Console, config.games)
      case None =>
    }
  }

}
