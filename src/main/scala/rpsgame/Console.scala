package rpsgame


import scopt._

case class Config(players: Seq[Player] = Nil, games: Int = 1){
  def withPlayer(p: Player) = copy(players = players :+ p)
}

object Config {
  def parse(cmdline:String*): Option[Config] ={
    implicit val handRead: Read[Hand] = Read.reads(Hand.select)
    new scopt.OptionParser[Config]("sbt run"){
      opt[Seq[Hand]]("wheel").abbr("wh").action{
        case (hands, _) if hands.size < 2 =>  throw new IllegalArgumentException("Wheel needs at least 2 hands")
        case (hands, g) => g.withPlayer(Wheel(hands:_*))
      }.valueName("<hand>,<hand>,..").unbounded()

      opt[Hand]("statue").abbr("st").action{
        case (hand,g) => g.withPlayer(Statue(hand))
      }.valueName("<hand>").unbounded()

      opt[Unit]("noise").abbr("n").action{  (_,g) =>
        g.withPlayer(Noise())
      }.unbounded()

      arg[Int]("#games").action { (no,g) => g.copy(games = no) }
        .optional().withFallback(() =>1)

    }.parse(cmdline, Config())
  }
}



object Console {
  def main(args: Array[String]): Unit = {
    Config.parse(args.toIndexedSeq: _*) match {
      case Some(config) => println(config)
      case None =>
    }
  }

}
