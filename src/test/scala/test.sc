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

noise.hand vs edward.hand
noise.move(wheel8.hand).hand vs wheel8.hand