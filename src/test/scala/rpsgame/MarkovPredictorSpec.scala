package rpsgame

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MarkovPredictorSpec extends AnyFlatSpec with Matchers{
  it should "predict constant" in {
    val m = (1 to 100).foldLeft(new MarkovPredictor)( (m,_) => m.move(Scissors) )
    m.stats.get(Rock,Scissors).mostLikely shouldBe Scissors
    m.stats.get(Rock,Scissors).chances(Scissors) shouldBe 1
  }
  it should "predict blinker" in {
    val m = (1 to 100).foldLeft(new MarkovPredictor) ( (m,_) => m.move(Scissors).move(Paper) )
    m.stats.get(Rock,Scissors).chances(Paper) shouldBe 1
    m.stats.get(Scissors,Paper).chances(Scissors) shouldBe 1
  }

}
