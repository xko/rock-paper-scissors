package rpsgame


import scopt._

import scala.Console.{BLUE, RED}

case class Config(players: Seq[Player] = Nil, games: Int = 1, console: Console = new Console){
  def withPlayer(p: Player) = copy(players = players :+ p)
  def host: Player = players.head
  def guest: Player = players.last
}

object Config {
  def parse(cmdline:String*): Option[Config] ={
    implicit val handRead: Read[Hand] = Read.reads(Hand.select)
    new scopt.OptionParser[Config]("rps[.cmd]"){
      opt[Seq[Hand]]("wheel").abbr("w").action{
        case (hands, _) if hands.size < 2 =>  throw new IllegalArgumentException("Wheel needs at least 2 hands")
        case (hands, g) => g.withPlayer(Wheel(hands:_*))
      }.valueName("<hand>,<hand>,..").unbounded()
        .text("adds a wheel player to the game. It throws specified hands in loop")

      opt[Hand]("statue").abbr("s").action{
        case (hand,g) => g.withPlayer(Statue(hand))
      }.valueName("<hand>").unbounded()
        .text("a statue player. Always throws one hand")

      opt[Unit]("noise").abbr("n").action{  (_,g) =>
        g.withPlayer(Noise())
      }.unbounded()
        .text("a random player")

      opt[String]("human").abbr("h").action { (name,c) =>
        val p = new HumanPlayer(name, if(c.players.isEmpty) RED else BLUE)
        c.withPlayer(p).copy(console = p)
      }.maxOccurs(1).valueName("<your name>")
        .text("a human player, specify to play against computer")

      opt[Unit]("markov").abbr("m").action { (name,c) =>
        c.withPlayer(new MarkovPredictor)
      }.unbounded()
        .text("smarter player, trying to predict your moves using Markov chains")

      arg[Int]("#games").action { (no,g) => g.copy(games = no) }
        .optional().withFallback(() =>1)

      checkConfig{
        case Config(Nil,_,_) => Left("No players specified")
        case Config(p,_,_) if p.size<2 => Left(s"$p is not enough, we need exactly 2 players")
        case Config(p,_,_) if p.size>2 => Left(s"$p is too much, we need exactly 2 players")
        case Config(_,_,_) => Right(())
      }
    }.parse(cmdline, Config())
  }
}



