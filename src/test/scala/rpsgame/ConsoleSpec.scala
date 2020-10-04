package rpsgame

import java.io.{ByteArrayOutputStream, PrintStream}

import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers


class ConsoleSpec extends AnyFunSpec with BeforeAndAfterEach with Matchers {
  def stdErrOf[T](trunk: => T) = {
    val buf = new ByteArrayOutputStream()
    scala.Console.withErr(buf)(trunk)
    buf.toString
  }

  describe("command line parser") {
    it("parses wheel player with different hand mnemonics") {
      stdErrOf {
        val cfg = Config.parse("-wh", "r,p,sc,ROCK,ro,s,scisors,pap", "-st", "s").get
        cfg.host should equal(Wheel(Rock, Paper, Scissors, Rock, Rock, Scissors, Scissors, Paper))
        cfg.guest should equal(Statue(Scissors))
      } shouldBe empty
    }

    it("parses # of rounds") {
        Config.parse("-wh", "r,p,sc","-st","s","42").get.games should equal (42)
    }

    it("fails on <2 players") {
      stdErrOf{
        Config.parse("-st","pa") should equal (None)
      } should include ("List(Statue(Paper)) is not enough, we need exactly 2 players")
    }

    it("fails on >2 players") {
      stdErrOf{
        Config.parse("-st","pa", "-n", "-n") should equal (None)
      } should include ("List(Statue(Paper), Noise(), Noise()) is too much, we need exactly 2 players")
    }

    it("fails on unknown mnemonic") {
      stdErrOf(Config.parse("-st", "lizard")) should include("No such hand: lizard")
    }

    it("fails if wheel gets not enough hands") {
      stdErrOf {
        assertResult(None)(Config.parse("-wh", "s"))
      } should include ("Wheel needs at least 2 hands")
    }
  }

}