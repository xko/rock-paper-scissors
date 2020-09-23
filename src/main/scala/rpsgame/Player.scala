package rpsgame

trait Player { player =>

  class Move(val opponent: Player) {
    lazy val hand = nextHand
    lazy val opHand = (opponent vs player).hand

    override def toString = {
      (hand.win(opHand).map(v => s"$player defeats $opponent: $v") orElse
        hand.loose(opHand).map(v => s"$player loses to $opponent: $v") orElse
        hand.draw(opHand).map(v => s"$player drawn to $opponent: $v")).get
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

