package rpsgame

import scala.Console.{BLUE, RED, RESET, println}
import scala.util.Try

class HumanPlayer(val name: String, val color: String) extends Console with Player {
  override lazy val hand: Hand = readNextHand

  override def move(vs: Hand): HumanPlayer = new HumanPlayer(name, color)

  override def before(game: Game): Unit = {
    println()
    println(columns("", game.host, " VS ",  game.guest))
    printnn(columns())
  }


  override def afterEach(g: Game): Unit = {
    round(g)
  }

  private def readNextHand:Hand = {
    (Try {
      Hand.select(io.StdIn.readLine(s" -> Your hand, $color$name$RESET:"))
    } recover { case ex: Exception =>
      printnn(columns("","","","",s"FAILED:${ex.getMessage}"))
      readNextHand
    }).get
  }

  override def toString = s"Human($name)"
}