package rpsgame


case class Game(host:Player, guest:Player, hostScore: Int = 0, guestScore: Int = 0) {

  def next:Game = Game( host.move(guest.hand), guest.move(host.hand),
                        if(outcome == host.hand.Victory) hostScore + 1  else hostScore,
                        if(outcome == host.hand.Defeat)  guestScore + 1 else guestScore )

  def outcome: host.hand.Outcome = host.hand vs guest.hand

  def onRound(hostWins: =>Unit, guestWins: =>Unit, draw: =>Unit): Unit = outcome match {
    case host.hand.Victory => hostWins
    case host.hand.Defeat  => guestWins
    case _                 => draw
  }

  def onGame(hostWins: =>Unit, guestWins: =>Unit, draw: =>Unit): Unit ={
    if     (hostScore  > guestScore) hostWins
    else if(guestScore > hostScore)  guestWins
    else                             draw
  }

}

object Game {

  def play(host:Player, guest:Player, display: Display, rounds: Int = 1): Game = {
    val newGame = Game(host,guest)
    display.before(newGame)
    val finGame = (1 to rounds).foldLeft(newGame) { (game, _) =>
      display.afterEach(game)
      val nextGame = game.next
      nextGame
    }
    display.after(finGame)
    finGame
  }

}

trait Display {
  def before(game: Game):Unit
  def afterEach(game: Game):Unit
  def after(game: Game):Unit
}
