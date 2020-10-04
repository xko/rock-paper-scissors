package rpsgame

import org.scalatest._
import matchers.should._
import org.scalatest.funspec.AnyFunSpec



class HandSpec extends AnyFunSpec with Matchers {

  describe("string parser"){
    it ("recognizes Rock mnemonics")  {
      Hand.select("r") should equal(Rock)
      Hand.select("RO") should equal(Rock)
      Hand.select("Rock") should equal(Rock)
      Hand.select("roc") should equal(Rock)
    }

    it("recognizes Paper mnemonics") {
      Hand.select("p") should equal(Paper)
      Hand.select("PA") should equal(Paper)
      Hand.select("pap") should equal(Paper)
      Hand.select("papeR") should equal(Paper)
      Hand.select("pape") should equal(Paper)
    }

    it("recognize Scissors mnemonics") {
      Hand.select("s") should equal(Scissors)
      Hand.select("scissors") should equal(Scissors)
      Hand.select("sciSors") should equal(Scissors)
      Hand.select("sc") should equal(Scissors)
    }

    it("throw at unknown mnemonic") {
      the [Exception] thrownBy {
        Hand.select("lizard")
      } should have message "No such hand 'lizard'"
    }

    it ("throw at empty mnemonic") {
      the [Exception] thrownBy {
        Hand.select("")
      } should have message "No hand specified"
    }
  }

  describe("Rock"){
    it("crushes Scissors"){
      Rock vs Scissors shouldBe Rock.Victory
      (Rock vs Scissors).toString shouldBe "Rock crushes Scissors"
    }

    it("is wrapped by Paper"){
      Rock vs Paper shouldBe Rock.Defeat
      (Rock vs Paper).toString shouldBe "Paper wraps Rock"
    }

    it("is Rock"){
      Rock vs Rock shouldBe Rock.Draw
      (Rock vs Rock).toString shouldBe "Rock is Rock"
    }
  }

  describe("Paper"){
    it("wraps Rock"){
      Paper vs Rock shouldBe Paper.Victory
      (Paper vs Rock).toString shouldBe "Paper wraps Rock"
    }

    it("is cut by Scissors"){
      Paper vs Scissors shouldBe Paper.Defeat
      (Paper vs Scissors).toString shouldBe "Scissors cut Paper"
    }
  }



}
