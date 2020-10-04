import rpsgame._
import scala.language.postfixOps

Rock vs Scissors
Rock vs Rock
Scissors vs Paper
Paper vs Scissors
Hand.select("s")
Hand.select("PA")
Hand.select("RO")

var wheel8 = Wheel(Rock, Rock, Paper, Rock, Scissors, Paper, Paper, Scissors)
var noise = Noise()

var edward = Statue(Scissors)

val disp = new Display {
  override def before(game: Game): Unit = println(">>>")
  override def afterEach(game: Game): Unit = println(game.outcome)
  override def after(game: Game): Unit = println("<<<")
}

Game.play(wheel8, noise, disp, 5)
Game.play(edward, wheel8, disp, 6)