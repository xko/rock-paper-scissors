package rpsgame

object Main {
  def main(args: Array[String]): Unit = {
    Config.parse(args.toIndexedSeq: _*) match {
      case Some(config) => println(config)
      case None =>
    }
  }

}
