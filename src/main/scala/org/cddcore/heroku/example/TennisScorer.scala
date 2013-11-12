package org.cddcore.heroku.example


import org.junit.runner.RunWith

import org.cddcore.engine.tests.CddRunner
import org.cddcore.engine.Engine
import org.corecdd.website.CddPathHandler
import org.cddcore.engine.Engine2
import org.corecdd.website.HandlerContext

case class Score(item: String)
object Score {
  val love = Score("love")
  val s15 = Score("fifteen")
  val s30 = Score("thirty")
  val s40 = Score("forty")
  val deuce = Score("deuce")
  val advantage = Score("advantage")
  val noScore = Score("noScore")
  val error = Score("error")
  val won = Score("won")
  val lost = Score("lost")
}

@RunWith(classOf[CddRunner])
object TennisScorer {
  import Score._
  val lookup = Map(0 -> love, 1 -> s15, 2 -> s30, 3 -> s40)

  val leftWon = "left won"
  val rightWon = "right won"

  val scorer = Engine[Int, Int, String]().
//    withDescription("Tennis Kata specified by http://codingdojo.org/cgi-bin/wiki.pl?KataTennis").
    useCase("A game is won by the first player to have won at least four points in total and at least two points more than the opponent.").
    scenario(4, 0).expected(leftWon).because((l: Int, r: Int) => (l - r) >= 2 && l >= 4).
    scenario(4, 1).expected(leftWon).
    scenario(4, 2).expected(leftWon).
    scenario(5, 3).expected(leftWon).

    scenario(0, 4).expected(rightWon).because((l: Int, r: Int) => (r - l) >= 2 && r >= 4).
    scenario(1, 4).expected(rightWon).
    scenario(2, 4).expected(rightWon).
    scenario(3, 5).expected(rightWon).
    scenario(40, 42).expected(rightWon).

    useCase("The running score of each game is described in a manner peculiar to tennis: scores from zero to three points are described as 'love', 'fifteen', 'thirty', and 'forty' respectively.").
    scenario(2, 3).expected("thirty, forty").because((l: Int, r: Int) => l < 4 && r < 4).code((l: Int, r: Int) => s"${lookup(l).item}, ${lookup(r).item}").
    scenario(2, 1).expected("thirty, fifteen").

    useCase("The running score, if both scores are the same, is called xx all").
    scenario(0, 0).expected("love all").because((l: Int, r: Int) => l == r && l < 3).code((l: Int, r: Int) => s"${lookup(l).item} all").
    scenario(2, 2).expected("thirty all").

    useCase("If at least three points have been scored by each player, and the scores are equal, the score is 'deuce'.").
    scenario(3, 3).expected("deuce").because((l: Int, r: Int) => l >= 3 && r >= 3 && l == r).
    scenario(4, 4).expected("deuce").because((l: Int, r: Int) => l >= 3 && r >= 3 && l == r).
    scenario(6, 6).expected("deuce").

    useCase("If at least three points have been scored by each side and a player has one more point than his opponent, the score of the game is 'advantage' for the player in the lead.").
    scenario(5, 4).expected("advantage left").because((l: Int, r: Int) => l >= 3 && r >= 3 && l == r + 1).
    scenario(6, 5).expected("advantage left").
    scenario(4, 3).expected("advantage left").

    scenario(4, 5).expected("advantage right").because((l: Int, r: Int) => l >= 3 && r >= 3 && r == l + 1).
    scenario(5, 6).expected("advantage right").
    scenario(3, 4).expected("advantage right").

    build
}

class TennisPathHandler(scorer: Engine2[Int, Int, String]) extends CddPathHandler {
  def willHandle(uri: String): Boolean = uri.equalsIgnoreCase("/")
  override def paramsINeed(context: HandlerContext) = List("leftScore", "rightScore", "score")
  def leftScore(params: List[(String, String)]) = getParam(params, "leftScore").toInt
  def rightScore(params: List[(String, String)]) = getParam(params, "rightScore").toInt
  def html(context: HandlerContext, params: List[(String, String)]): String = context.method match {
    case "GET" => html(context, 0, 0);
    case "POST" => getParam(params, "score") match {
      case "Left" => html(context, leftScore(params) + 1, rightScore(params))
      case "Right" => html(context, leftScore(params), rightScore(params) + 1)
    }
    case _ => ""
  }
  def html(context: HandlerContext, leftScore: Int, rightScore: Int) = {
    import context._
    val action = fullUri
    val requirements = urlMap.toUrl(scorer)
    <html>
      <body>
        <h1>Tennis Game</h1>
        <a href={ requirements }> Requirements </a>
        <p>Score:{ scorer(leftScore, rightScore) }</p>
        <form method='post' action={ action }>
          <input type='hidden' name='leftScore' value={ leftScore.toString }/>
          <input type='hidden' name='rightScore' value={ rightScore.toString }/>
          <input type='submit' name='score' value='Left'/>
          <input type='submit' name='score' value='Right'/>
        </form>
      </body>
    </html>.toString
  }
}