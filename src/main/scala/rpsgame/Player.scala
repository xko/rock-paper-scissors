package rpsgame


trait Player  {
  val hand: Hand
  def move(vs: Hand): Player
}

case class Statue(hand: Hand) extends Player {
  override def move(vs: Hand): Statue = this
}

case class Wheel(hands: Hand*) extends Player {
  override val hand: Hand = hands.head

  override def move(vs: Hand): Wheel = {
      val turned = hands.tail :+ hands.head
      Wheel(turned:_*)
  }

  override def toString: String = {
    s"Wheel(${hands.head}+${hands.tail.size}..)"
  }
}

case class Noise() extends Player {
  override val hand: Hand = Hand.random
  override def move(vs: Hand): Noise = Noise()
}

