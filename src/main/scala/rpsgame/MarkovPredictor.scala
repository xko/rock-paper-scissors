package rpsgame

case class Stats private (entries:Map[(Hand,Hand), Entry]) {

  def get(mine: Hand, their: Hand): Entry = entries(mine,their)

  def upd(mine: Hand, their: Hand, theirNext: Hand): Stats =
    new Stats(entries + ( (mine, their) -> entries(mine, their).upd(theirNext) ) )
}

object Stats {
  val empty: Stats = Stats(Map().withDefaultValue(Entry.empty))
}

case class Entry private(totals: Map[Hand, Int]) {
  def upd(hand:Hand):Entry = new Entry(totals + (hand -> (totals(hand)+1) ))

  lazy val chances: Map[Hand, Double] = totals.view.mapValues(v => v.toDouble / totals.values.sum).toMap
  lazy val mostLikely: Hand = chances.toList.maxBy(_._2)._1
}

object Entry {
  val empty: Entry = Entry(Map(Rock->0, Paper ->0, Scissors->0))
}


case class MarkovPredictor(prev:Option[(Hand,Hand)] = None, stats:Stats = Stats.empty) extends Player { me=>

  lazy val hand: Hand = prev.fold(Hand.random){
    case (prevMine:Hand, prevTheir:Hand) => me.stats.get(prevMine,prevTheir).mostLikely.winner
  }

  override def move(vs: Hand): MarkovPredictor = prev.fold( MarkovPredictor(Some(hand,vs)) ) {
    case (prevMine, prevTheir) => MarkovPredictor( Some(hand,vs), stats.upd(prevMine, prevTheir, vs) )
  }

  override def toString = "MarkovPredictor"
}
