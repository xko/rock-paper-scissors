package rpsgame

trait Player { player =>

  class Move(val hand: Hand, opHand: =>Hand){
    lazy val opponentHand = opHand

    override def toString = {
      (hand.win(opHand).map(v => s"$player won: $v") orElse
        hand.loose(opHand).map(v => s"$player lost: $v") orElse
        hand.draw(opHand).map(v => s"draw: $v")).get
      //TODO: get is ugly - exhaustive matching would be nice
    }
  }

  def nextHand: Hand

  def move(opponentHand: =>Hand) = {
    new Move(nextHand,opponentHand)
  }

  def vs (opponent: Player) = {
    lazy val myMove:Move = move(opMove.hand)
    lazy val opMove:Player#Move = opponent.move(myMove.hand)
    myMove
  }
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

