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
        val cfg = Config.parse("-w", "r,p,sc,ROCK,ro,s,scisors,pap,1,2,3", "-s", "s").get
        cfg.host should equal(Wheel(Rock, Paper, Scissors, Rock, Rock, Scissors, Scissors, Paper,
                                    Rock, Paper, Scissors))
        cfg.guest should equal(Statue(Scissors))
      } shouldBe empty
    }

    it("parses # of rounds") {
        Config.parse("-w", "r,p,sc","-s","s","42").get.games should equal (42)
    }

    it("fails on <2 players") {
      stdErrOf{
        Config.parse("-s","pa") should equal (None)
      } should include ("List(Statue(Paper)) is not enough, we need exactly 2 players")
    }

    it("fails on >2 players") {
      stdErrOf{
        Config.parse("-s","pa", "-n", "-n") should equal (None)
      } should include ("List(Statue(Paper), Noise(), Noise()) is too much, we need exactly 2 players")
    }

    it("fails on unknown mnemonic") {
      stdErrOf(Config.parse("-s", "lizard")) should include("No such hand 'lizard'")
    }

    it("fails if wheel gets not enough hands") {
      stdErrOf {
        assertResult(None)(Config.parse("-w", "s"))
      } should include ("Wheel needs at least 2 hands")
    }
  }

}