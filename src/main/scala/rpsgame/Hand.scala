package rpsgame

import scala.util.Random

sealed abstract class Hand(val winVerb:String, lsr: =>Hand,  wnr: =>Hand){ me =>
  lazy val loser = lsr
  lazy val winner = wnr

  sealed trait Outcome

  case object Victory extends Outcome  {
    override def toString: String = s"$me ${winVerb} $loser"
  }
  case object Defeat extends Outcome  {
    override def toString: String = s"$winner ${winner.winVerb} $me"
  }
  case object Draw extends Outcome {
    override def toString: String = s"$me is $me"
  }

  def vs(op: Hand): Outcome = if(op==winner) Defeat else if(op==loser) Victory else Draw

}

object Hand {
  val all: Seq[Hand] = List(Rock, Paper, Scissors)
  def random = all(Random.nextInt(all.size))
  def select(mnemonic: String):Hand = mnemonic.toLowerCase match {
    case Rock.pattern(_*) => Rock
    case Paper.pattern(_*) => Paper
    case Scissors.pattern(_*) =>Scissors
    case s => throw new IllegalArgumentException(s"No such hand: $s")
  }
}

case object Rock extends Hand("crushes", Scissors, Paper) {
  val pattern = "(?i)(r|ro|roc|rock)".r
}

case object Paper extends Hand("wraps", Rock, Scissors ) {
  val pattern = "(?i)(p|pa|pap|pape|paper)".r
}

case object Scissors extends Hand("cut", Paper, Rock) {
  val pattern = "(?i)(s|sc|sci|scissors|scisors)".r
}


