package rpsgame


trait Player  {
  val hand: Hand
  def move(vs: Hand): Player
}

case class Statue(hand: Hand) extends Player {
  override def move(vs: Hand): Statue = this
}

case class Wheel(hand:Hand, more: Hand*) extends Player {

  override def move(vs: Hand): Wheel = {
      val turned = more :+ hand
      Wheel(turned.head, turned.tail:_*)
  }

  override def toString: String = s"Wheel($hand+$more..)"
}

case class Noise() extends Player {
  override val hand: Hand = Hand.random
  override def move(vs: Hand): Noise = Noise()
}

