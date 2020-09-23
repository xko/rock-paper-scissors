package rpsgame

trait Player { me =>

  class Move(val them: Player) {
    lazy val hand = nextHand
    lazy val theirHand = (them vs me).hand

    override def toString = {
      (hand.win(theirHand).map(v => s"$me defeats $them: $v") orElse
        hand.loose(theirHand).map(v => s"$me loses to $them: $v") orElse
        hand.draw(theirHand).map(v => s"$me drawn to $them: $v")).get
      //TODO: get is ugly - exhaustive matching would be nice
    }
  }

  def nextHand: Hand

  def vs(opponent: Player): Move = new Move(opponent)
}

case class Statue(hand: Hand) extends Player {
  override def nextHand: Hand = hand
}

class Wheel(hands: Seq[Hand]) extends Player {
  private var i = hands.iterator

  override def nextHand: Hand = {
    if(!i.hasNext) i = hands.iterator
    i.next()
  }
}

object Noise extends Player {
  override def nextHand: Hand = Hand.random
}

