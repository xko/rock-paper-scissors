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
        Config.parse("-wh", "r,p,sc,ROCK,ro,s,scisors,pap","-st","s") should equal (
          Some(Config(Seq(
            Wheel(Rock, Paper, Scissors, Rock, Rock, Scissors, Scissors, Paper),
            Statue(Scissors)
          ), 1)) )
      } shouldBe empty
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