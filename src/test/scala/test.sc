import rpsgame._
import scala.language.postfixOps

Rock vs Scissors
Rock vs Rock
Scissors vs Paper
Paper vs Scissors

val wheel = new Wheel(List(
  Rock, Rock, Paper, Rock, Scissors, Paper, Paper, Scissors
))

val edward = Statue(Scissors)

Noise vs wheel
wheel vs Noise
wheel vs edward
Noise vs edward
edward vs Noise

