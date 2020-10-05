package rpsgame

import scala.Console.{out, RED, BLUE, RESET, print, println}

class Console extends Display {

  override def afterEach(g: Game): Unit = {
    println()
    round(g)
  }

  protected def round(g: Game): Unit = {
    g.onRound(
      printnn(columns("", g.host.hand, " ", g.guest.hand, s"-> ${g.outcome}", RED)),
      printnn(columns("", g.host.hand, " ", g.guest.hand, s"-> ${g.outcome}", BLUE)),
      printnn(columns("", g.host.hand, " ", g.guest.hand, s"-> ${g.outcome}")),
    )
  }

  override def before(game: Game): Unit = {
    println()
    println(columns("", game.host, " VS ",  game.guest))
  }

  override def after(game: Game): Unit = {
    println()
    println()
    game.onGame(
      println(columns("Final score:",game.hostScore," : ",game.guestScore,s"-> ${game.host} WINS!",RED)),
      println(columns("Final score:",game.hostScore," : ",game.guestScore,s"-> ${game.guest} WINS!",BLUE)),
      println(columns("Final score:",game.hostScore," : ",game.guestScore,"-> DRAW!"))
    )
    println()
  }

  def printnn(s: String): Unit = print(s); out.flush()

  def columns(intro:Any="", red: Any="", mid:Any="", blue:Any="", result:Any="", resultColor:String = ""): String =
    if(!intro.toString.isBlank) s"%-14s$RED%8s$RESET%-4s$BLUE%-18s$RESET$resultColor%-25s$RESET".format(intro,red,mid,blue,result)
    else s"$RED%22s$RESET%-4s$BLUE%-18s$RESET$resultColor%-25s$RESET".format(red,mid,blue,result)

}


