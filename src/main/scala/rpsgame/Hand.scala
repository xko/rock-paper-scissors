package rpsgame

import scala.util.Random

sealed trait Hand {
  def victory: Victory
  def defeat: Victory
  private val _draw = Draw(this, this)

  def win(op: Hand) = Option.when(op == victory.looser)(victory)
  def loose(op: Hand) = Option.when(op == defeat.winner)(defeat)
  def draw(op: Hand) = (win(op) orElse loose(op)).fold(Option(_draw))(_ =>None)

  def vs(op: Hand): Outcome = win(op) orElse loose(op) getOrElse _draw
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

case object Rock extends Hand {
  val victory = new Victory(this,"crushes", Scissors)
  val defeat = Paper.victory
  val pattern = "(?i)(r|ro|roc|rock)".r
}
case object Paper extends Hand {
  val victory = new Victory(this,"wraps", Rock)
  val defeat = Scissors.victory
  val pattern = "(?i)(p|pa|pap|pape|paper)".r
}
case object Scissors extends Hand {
  val victory = new Victory(this,"cut", Paper)
  val defeat = Rock.victory
  val pattern = "(?i)(s|sc|sci|scissors|scisors)".r
}

trait Outcome {
  def left: Hand
  def right: Hand
}

class Victory (_winner: => Hand, val verb: String, _looser: =>Hand) extends Outcome {
  lazy val left = _looser
  lazy val right = _winner
  def looser = left
  def winner: Hand = right

  override def toString = s"$winner $verb $looser"
}

case class Draw(left: Hand, right: Hand) extends Outcome {
    override def toString = s"$left is $right"
}

