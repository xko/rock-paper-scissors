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
      head("\nRock-Paper-Scissors game\n")
      opt[Seq[Hand]]("wheel").abbr("w").action{
        case (hands, _) if hands.size < 2 =>  throw new IllegalArgumentException("Wheel needs at least 2 hands")
        case (hands, g) => g.withPlayer(Wheel(hands.head, hands.tail:_*))
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
        .text("number of games to play")

      note("\nExample: ./rps -w s,r,p,r,r -s s 10\n" +
        "  will play 10 games between player circling Scissors,Rock,Paper,Rock,Rock and " +
        "  player always throwing Scissors\n")
      note("\nExample: ./rps -m -h Bob 10\n" +
        "  will play 10 games between predictor and human named Bob" +
        "  (Try this! Predictor wins about 70%!)\n")
      note("<hand>s can be specified as (case insensitive):\n" +
        "  Rock:     r,ro,rock,1\n" +
        "  Paper:    p,pa,paper,2\n" +
        "  Scissors: s,sc,scissors,3\n" +
        "  .. also when playing interactively as human player")

      checkConfig{
        case Config(Nil,_,_) => Left("No players specified")
        case Config(p,_,_) if p.size<2 => Left(s"$p is not enough, we need exactly 2 players")
        case Config(p,_,_) if p.size>2 => Left(s"$p is too much, we need exactly 2 players")
        case Config(_,_,_) => Right(())
      }
    }.parse(cmdline, Config())
  }
}



