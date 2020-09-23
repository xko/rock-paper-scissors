import rpsgame._
import scala.language.postfixOps

Rock vs Scissors
Rock vs Rock
Scissors vs Paper
Paper vs Scissors

object Wheel8 extends Wheel(List(
  Rock, Rock, Paper, Rock, Scissors, Paper, Paper, Scissors
))

val edward = Statue(Scissors)

Noise vs Wheel8
Wheel8 vs Noise
Wheel8 vs edward
Noise vs edward
edward vs Noise

Hand.select("s")
Hand.select("PA")
Hand.select("RO")
