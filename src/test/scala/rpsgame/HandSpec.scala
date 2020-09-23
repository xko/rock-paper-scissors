package rpsgame

import org.scalatest._
import matchers.should._
import org.scalatest.flatspec.AnyFlatSpec



class HandSpec extends AnyFlatSpec with Matchers {
  it should "recognize Rock mnemonics" in {
    Hand.select("r") should equal(Rock)
    Hand.select("RO") should equal(Rock)
    Hand.select("Rock") should equal(Rock)
    Hand.select("roc") should equal(Rock)
  }

  it should "recognize Paper mnemonics" in {
    Hand.select("p") should equal(Paper)
    Hand.select("PA") should equal(Paper)
    Hand.select("pap") should equal(Paper)
    Hand.select("papeR") should equal(Paper)
    Hand.select("pape") should equal(Paper)
  }

  it should "recognize Scissors mnemonics" in {
    Hand.select("s") should equal(Scissors)
    Hand.select("scissors") should equal(Scissors)
    Hand.select("sciSors") should equal(Scissors)
    Hand.select("sc") should equal(Scissors)
  }

  it should "throw at unknown mnemonic" in {
    the [Exception] thrownBy {
      Hand.select("lizard")
    } should have message "No such hand: lizard"
  }

  it should "throw at empty mnemonic" in {
    the [Exception] thrownBy {
      Hand.select("")
    } should have message "No such hand: "
  }


}
