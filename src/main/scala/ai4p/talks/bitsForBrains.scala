package ai4p.talks

import com.wbillingsley.veautiful.html.*
import com.wbillingsley.veautiful.doctacular.DeckBuilder

import <._
import ^._

import ai4p.{*, given}
import Common.*

import site.given

import statespace.CardsOfDoom
import statespace.widgets.*
import ai4p.fromexamples.HappyGame
import coderunner.JSCodable
import canvasland.CanvasLand
import canvasland.Turtle
import ai4p.uncertainty.scilly
import ai4p.neuralnets.widgets.RiemannWidget
import ai4p.neuralnets.widgets.PulseNetworkWidget
import ai4p.fromexamples.stabilityStyling
import coderunner.PrefabCodable
import canvasland.LineTurtle
import ai4p.fromexamples.widgets.GradientDescent
import ai4p.embeddings.widgets.RelationshipRepeaterWidget
import ai4p.embeddings.widgets.NearestNeighboursWidget
import ai4p.embeddings.widgets.WordAlgebraWidget
import ai4p.embeddings.widgets.PeterRabbitWidget
import ai4p.embeddings.widgets.LineVectorWidget
import ai4p.neuralnets.widgets.NeuronChainWidget


val basicTable = Styling("""
|border-collapse: collapse;
|padding: 5px;
|""".stripMargin).modifiedBy(
  " th" -> "font-weight: bold; border: 1px solid black; padding: 0.6em;",
  " td" -> "border: 1px solid black;  padding: 0.6em;"
).register()



/** Shared styling for the "Fifteen" widgets: number tiles in a line or a 3x3 grid */
val tttStyling = Styling("""
|font-size: 48px;
|font-family: monospace;
|display: inline-flex;
|flex-direction: column;
|align-items: center;
|gap: 30px;
|""".stripMargin).modifiedBy(
  " .line" -> "display: flex; gap: 16px;",
  " .grid" -> "display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px;",
  " button" -> "font-family: inherit; font-size: 48px; font-weight: bold; border-radius: 16px; transition: transform 0.1s, box-shadow 0.1s;",
  " .tile" -> "width: 120px; height: 120px; border: 3px solid #b0b8c4; background-color: white; color: #333; box-shadow: 0 4px 0 #b0b8c4;",
  " .ready" -> "cursor: pointer;",
  " .ready:hover" -> "transform: translateY(-3px); box-shadow: 0 7px 0 #b0b8c4; background-color: #f6f8fb;",
  " .ready:active" -> "transform: translateY(3px); box-shadow: 0 1px 0 #b0b8c4;",
  " .tile.nought" -> "background-color: lightskyblue; border-color: #3a8fd0; color: #0b3d66; box-shadow: 0 4px 0 #3a8fd0; cursor: default;",
  " .tile.cross" -> "background-color: pink; border-color: #d0607a; color: #6b1028; box-shadow: 0 4px 0 #d0607a; cursor: default;",
  " .reset" -> "font-size: 32px; padding: 10px 32px; border: none; background-color: #555; color: white; cursor: pointer; box-shadow: 0 4px 0 #222;",
  " .reset:hover" -> "background-color: #666;",
  " .reset:active" -> "transform: translateY(3px); box-shadow: 0 1px 0 #222;",
).register()

case class TTTWidget() extends DHtmlComponent {

  val styling = tttStyling

  val st = stateVariable(Map.empty[Int, Boolean])
  val turn = stateVariable(false)

  def play(n:Int) = {
    st.value = st.value.updated(n, turn.value)
    turn.value = !turn.value
  }

  override protected def render = <.div(^.cls := styling,
    <.div(^.cls := "line", for n <- 1 to 9 yield 
        if st.value.contains(n) then 
            if st.value(n) then 
                <.button(^.cls := "tile cross", n.toString)
            else <.button(^.cls := "tile nought", n.toString)
        else <.button(^.cls := "tile ready", ^.on.click --> play(n), n.toString)
    ),
    <.div(<.button(^.cls := "reset", "Reset", ^.on.click --> { st.value = Map.empty[Int, Boolean]; turn.value = false}))

  )
}


case class TTTWidget2() extends DHtmlComponent {

  val styling = tttStyling

  val st = stateVariable(Map.empty[Int, Boolean])
  val turn = stateVariable(false)

  def play(n:Int) = {
    st.value = st.value.updated(n, turn.value)
    turn.value = !turn.value
  }

  override protected def render = 
    def button(n:Int) = 
        if st.value.contains(n) then 
            if st.value(n) then 
                <.button(^.cls := "tile cross", n.toString)
            else <.button(^.cls := "tile nought", n.toString)
        else <.button(^.cls := "tile ready", ^.on.click --> play(n), n.toString)
    
    <.div(^.cls := styling,
        <.div(^.cls := "grid",
          button(8), button(1), button(6),
          button(3), button(5), button(7),
          button(4), button(9), button(2),
        ),
        <.div(<.button(^.cls := "reset", "Reset", ^.on.click --> { st.value = Map.empty[Int, Boolean]; turn.value = false}))
    )

}


val bitsForBrains = DeckBuilder(1920, 1080)
  .markdownSlide(
    """
      |# Bits for Brains
      |(Far Out Science, November 2026)
      |""".stripMargin
  ).withClass("center middle")
  .veautifulSlide(<.div(
    <.h2("Let's meet your AI-generated co-host, Farrow!"),
    <.img(^.src := "//turing.une.edu.au/~wbilling/faroutscience/farrow.png", ^.style := "float: left; margin-right: 50px;"),            
  ))
  .markdownSlides(
    """
      |## Machine learning
      |
      |Which of my cue phrases did that sound like?
      |
      |> Nearly useless?
      |
      |> I'm the host
      |
      |> Introduce the first exercise
      |
      |That's a *classification task*. Which does it sound like, and is it close enough to any of them?
      |
      |---
      |
      |## Programming
      |
      |Farrow's program is very simple.
      |
      |It's a script, so I want her to give me the next line.
      |
      |
      |> If you receive <code>NearlyUseless</code>
      |> then say "You pressed the unlock button on your phone very well..."
      |
      |
      |We could have her *generate* a funny next line but then how would I know how to respond? 
      |
      |""".stripMargin
  )
  .markdownSlides(
    """
    |## Small Games and lookahead
    |
    |""".stripMargin
  ).withClass("center middle")
  .veautifulSlide(<.div(
    <.h2("Cards of Doom"),
    <.p("You can take 1, 2, or 3 cards on your turn. Whoever takes the last card LOSES"),
    CardsOfDoom(13)
  ))
  .veautifulSlide(<.div(
    markdown.div("""
    |## But the computer can just run the whole game and remember every combination!
    |""".stripMargin),
    {
      val miniTabStyle = Styling("""
      | border-collapse: collapse;
      |
      |""".stripMargin)
      .modifiedBy(
         " td,th" -> "padding-right: 5px;  vertical-align: top; ",
        " .take" -> "font-style: italic; font-size: 75%; color: darkgrey; border-right: 1px solid black; ",
        " .win" -> "color: green; font-weight: bold; font-size: 75%;",
        " .lose" -> "color: red; font-weight: bold; font-size: 75%;"
      ).register()

      def renderC(cards:Int, turn:Boolean):VHtmlBlueprint = 
        <.td(
          <.td(
            if cards > 1 then 
              s"$cards cards"
            else if cards > 0 then 
              "1 card"
            else if turn then <.span(^.cls := "win", "1") else <.span(^.cls := "lose", "0")
          ),
          <.td(<.table(
            for i <- 1 to Math.min(cards, 3) yield <.tr(
              <.td(^.cls := "take", s"${if turn then "I" else "they"} take $i"), 
              renderC(cards - i, !turn)
            )
          ))
        )

      <.table(^.cls := miniTabStyle, renderC(13, true))
    },
  ))
  .veautifulSlide(<.div(
    <.h2("Let's look at it differently and learn a heuristic"),
    CardsOfDoom(12, false, true)
  ))
  .veautifulSlide(<.div(
    <.h2("Now we can win games with 27 cards!"),
    CardsOfDoom(27, false, true)
  ))
  .veautifulSlide(<.div(
    <.h2("Fifteen!"),
    TTTWidget()
  ))
  .veautifulSlide(<.div(
    <.h2("Fifteen!"),
    TTTWidget2()
  ))

  .markdownSlides("""
  |## Noughts and Crosses
  |
  |Noughts and crosses is probably big enough you don't think of trying to remember every possible state. 
  |
  |It's still pretty small though:
  |
  |* 5,478 possible unique positions
  |* 765 unique positions once you've taken accounted for symmetry, but
  |* 255,168 possible sequnces of moves
  |
  |Those are bigger than you and I can remember, but it's peanuts to a computer!
  |
  |""".stripMargin)
  .veautifulSlide(<.div(
    <.h2("Noughts and Crosses vs minimax"),
    NoughtsAndCrossesAI(Player.Nought)
  ))
  .markdownSlide("""
  |## This much code can never lose at noughts and crosses!
  |
  |<pre>
  |  // Minimax: returns (score, bestIndex)
  |  // score is from the perspective of `maximiser`
  |  def minimax(b: Board, current: Player, maximiser: Player, depth: Int): (Int, Int) =
  |    winner(b) match
  |      case Some(p) =>
  |        val score = if p == maximiser then 10 - depth else depth - 10
  |        (score, -1)
  |      case None if isDraw(b) => (0, -1)
  |      case None =>
  |        val moves = legalMoves(b)
  |        val scored = moves.map { idx =>
  |          val next = play(b, idx, current)
  |          val nextPlayer = if current == Player.Nought then Player.Cross else Player.Nought
  |          val (s, _) = minimax(next, nextPlayer, maximiser, depth + 1)
  |          (s, idx)
  |        }
  |        if current == maximiser then scored.maxBy(_._1)
  |        else scored.minBy(_._1)
  |</pre>
  |""".stripMargin)
  .imageSlide("https://lichess.org/p0Dt5kvE/black#92", "images/stockfish.png")
  .markdownSlides(
    """
    |## Machine learning 
    |
    |""".stripMargin
  ).withClass("center middle")
  .veautifulSlide(<.div(
    <.h2("Human Learning"),
    <.p(HappyGame())
  ))
  .markdownSlides(
    """
    |
    |## Teachable Machine
    |
    |Now let's try one Google made earlier, that runs in a web-browser...
    |
    |[Teachable Machine, version 1](https://teachablemachine.withgoogle.com/v1/)
    |
    |If you click the link, you can try this at home as well. We're going to teach the comptuer to recognise a small number of items - in my case, road signs.
    |
    |
    |---
    |
    |## Confusion matrices
    |
    |Let's try out another version of the teachable machine. Again, you can do this at home, but what I want to show you is
    |in its advanced tab
    |
    |[Teachable Machine, image task](https://teachablemachine.withgoogle.com/train/image)
    |
    |If we have two classes that we're classifying items into, then if we take a sample where we know what the result should be, we can test the model against them.
    |That gives us a *confusion matrix* with four quadrants
    |
    |* True positives (or items in class A that were classified in class A)
    |* False positives (or items in class B that were classified in class A)
    |* False negatives (or items in class A that were classified in class B)
    |* True negatives (or items in class B that were classified in class B)
    |
    |""".stripMargin)
  .veautifulSlide(<.div(
    <.h2("Successive approximations"),
    markdown.div(
      """
        |Suppose we want to work out the area under a curve, and we're lazy...
        |""".stripMargin
    ),
    <.p(RiemannWidget())
  ))
  .veautifulSlide(<.div(
    <.h2("Neurons in a neural network do approximations"),
    markdown.div(
      """Note that we're *not doing any training here*. We're just showing how simple nodes using step functions can
        |give us approximately the right output if we know what weights to give them.
        |""".stripMargin
    ),
    <.p(PulseNetworkWidget())
  ))
  .veautifulSlide(<.div(
    <.h2("One neuron"),
    markdown.div(
      """
        |A solitary neuron's really dull...
        |""".stripMargin
    ),
    <.p(NeuronChainWidget(
      Vector("SiLU" -> NeuronChainWidget.silu),
      reps = 1, expandLinear = true, showBias = true,
      xMin = -5, xMax = 5, yMin = -1.5, yMax = 10, wMin = -2.5, wMax = 2.5, bMin = -2.5, bMax = 2.5
    ))
  ))
  .markdownSlide("## Let's play blind skittles...").withClass("center middle")
  .veautifulSlide(<.div(
    <.h2("Gradient descent"),   
    <.p(GradientDescent())
  ))
  .imageSlide("Engraving of the Scilly Naval Disaster 1707", "https://upload.wikimedia.org/wikipedia/commons/d/d5/HMS_Association_%281697%29.jpg")
  .veautifulSlide(<.div(
      <.h2("Sailing into the Channel"),
      markdown.div(
        """We should be able to sail into the Channel with
          |
          |```js
          |left(30); forward(600); right(20); forward(300);
          |```
          |
          |But what if storms, winds, or currents means our first leg was a little bit wrong?
          |""".stripMargin),
      JSCodable(CanvasLand()(
        viewSize = 920 -> 640,
        fieldSize = 1280 -> 720,        
        r = Turtle(50, 600),
        setup = (c) => {
          c.drawImage(scilly, 0, 0,1280, 720, 0,0,1280, 720)
        }
      ))(tilesMode = false, fontSize = 20)
    ))
   .veautifulSlide(<.div(^.cls := stabilityStyling.className,
      <.h2("Sensor under the robot"),
      // markdown.div(
      //   """A closed loop control system can become unstable. A quick demonstration of this is what happens if we
      //     |*move the line sensor behind the robot*. Fortunately, having the line sensor *in front* of the robot gives
      //     |us some "damping" in our control system (the oscillations get smaller).
      //     |""".stripMargin),
      PrefabCodable(
        """addLineSensor(0, 0, 255, 255, 0) // one sensor, under the robot
          |
          |setColour("blue")
          |setCompositeMode("lighten")
          |
          |while (true) {
          |    let l = readSensor(0); // how bright is our sensor?
          |    
          |    if (l < 0.5) {
          |        // dark. We're right of the edge, so steer left
          |        left(5);   
          |    } else  {
          |        // light. We're left of the edge, so steer right
          |        right(5);  
          |    }
          |
          |    forward(1);
          |}
          |""".stripMargin,
      CanvasLand()(
        fieldSize=(920 -> 640),
        viewSize=(920 -> 640),
        r = LineTurtle(120, 90) { r => },
        setup = { (c:CanvasLand) =>
          c.fillCanvas("rgb(200,180,0)")
          c.drawGrid("rgb(200,240,240)", 25, 1)
          c.withCanvasContext { ctx =>
            ctx.strokeStyle = "rgb(60,60,60)"
            ctx.lineWidth = 40
            ctx.beginPath()
            ctx.moveTo(100, 100)
            ctx.lineTo(770, 100)
            ctx.lineTo(770, 540)
            ctx.bezierCurveTo(670, 540, 150, 200, 150, 100)
            ctx.stroke()
          }
        }
      ),
      codeStyle = Some(
        "margin: 0; width: 500px; max-height: 640px; overflow: auto; " +
        "font-size: 0.8rem; line-height: 1.4; background: #1e1e1e; color: #f1f1f1; " +
        "padding: 12px; border-radius: 6px; white-space: pre-wrap;"
      ))
    ))
    .veautifulSlide(<.div(^.cls := stabilityStyling.className,
      <.h2("Sensor 1px in front of the robot - stable and damped"),
      // markdown.div(
      //   """A closed loop control system can become unstable. A quick demonstration of this is what happens if we
      //     |*move the line sensor behind the robot*. Fortunately, having the line sensor *in front* of the robot gives
      //     |us some "damping" in our control system (the oscillations get smaller).
      //     |""".stripMargin),
      PrefabCodable(
        """addLineSensor(1, 0, 255, 255, 0) // move the sensor 1px in front
          |
          |setColour("blue")
          |setCompositeMode("lighten")
          |
          |while (true) {
          |    let l = readSensor(0); // how bright is our sensor?
          |    
          |    if (l < 0.5) {
          |        // dark. We're right of the edge, so steer left
          |        left(5);   
          |    } else  {
          |        // light. We're left of the edge, so steer right
          |        right(5);  
          |    }
          |
          |    forward(1);
          |}
          |""".stripMargin,
      CanvasLand()(
        fieldSize=(920 -> 640),
        viewSize=(920 -> 640),
        r = LineTurtle(120, 90) { r => },
        setup = { (c:CanvasLand) =>
          c.fillCanvas("rgb(200,180,0)")
          c.drawGrid("rgb(200,240,240)", 25, 1)
          c.withCanvasContext { ctx =>
            ctx.strokeStyle = "rgb(60,60,60)"
            ctx.lineWidth = 40
            ctx.beginPath()
            ctx.moveTo(100, 100)
            ctx.lineTo(770, 100)
            ctx.lineTo(770, 540)
            ctx.bezierCurveTo(670, 540, 150, 200, 150, 100)
            ctx.stroke()
          }
        }
      ),
      codeStyle = Some(
        "margin: 0; width: 500px; max-height: 640px; overflow: auto; " +
        "font-size: 0.8rem; line-height: 1.4; background: #1e1e1e; color: #f1f1f1; " +
        "padding: 12px; border-radius: 6px; white-space: pre-wrap;"
      ))
    ))
    .veautifulSlide(<.div(^.cls := stabilityStyling.className,
      <.h2("Sensor 1px behind the robot - unstable"),
      // markdown.div(
      //   """A closed loop control system can become unstable. A quick demonstration of this is what happens if we
      //     |*move the line sensor behind the robot*. Fortunately, having the line sensor *in front* of the robot gives
      //     |us some "damping" in our control system (the oscillations get smaller).
      //     |""".stripMargin),
      PrefabCodable(
        """addLineSensor(-1, 0, 255, 255, 0) // move the sensor 1px behind
          |
          |setColour("blue")
          |setCompositeMode("lighten")
          |
          |while (true) {
          |    let l = readSensor(0); // how bright is our sensor?
          |    
          |    if (l < 0.5) {
          |        // dark. We're right of the edge, so steer left
          |        left(5);   
          |    } else  {
          |        // light. We're left of the edge, so steer right
          |        right(5);  
          |    }
          |
          |    forward(1);
          |}
          |""".stripMargin,
      CanvasLand()(
        fieldSize=(920 -> 640),
        viewSize=(920 -> 640),
        r = LineTurtle(120, 80) { r => },
        setup = { (c:CanvasLand) =>
          c.fillCanvas("rgb(200,180,0)")
          c.drawGrid("rgb(200,240,240)", 25, 1)
          c.withCanvasContext { ctx =>
            ctx.strokeStyle = "rgb(60,60,60)"
            ctx.lineWidth = 40
            ctx.beginPath()
            ctx.moveTo(100, 100)
            ctx.lineTo(770, 100)
            ctx.lineTo(770, 540)
            ctx.bezierCurveTo(670, 540, 150, 200, 150, 100)
            ctx.stroke()
          }
        }
      ),
      codeStyle = Some(
        "margin: 0; width: 500px; max-height: 640px; overflow: auto; " +
        "font-size: 0.8rem; line-height: 1.4; background: #1e1e1e; color: #f1f1f1; " +
        "padding: 12px; border-radius: 6px; white-space: pre-wrap;"
      ))
    ))
  .veautifulSlide(<.div(
    markdown.div("""|## Let's turn words into numbers based on how they're used
                    |""".stripMargin),
    <.p(PeterRabbitWidget()),
  ))
  .veautifulSlide(<.div(
    markdown.div("""|## Ok, but that doesn't really work...
                    |""".stripMargin),
    <.p(LineVectorWidget()),
  ))
  .veautifulSlide(<.div(
    markdown.div("""|## Instead, train a network to predict the next word...
                    |
                    |https://nlp.stanford.edu/projects/glove/
                    |
                    |""".stripMargin),
    <.p(NearestNeighboursWidget())
  ))
  .veautifulSlide(<.div(
    markdown.div(
      """|## If words become numbers... what if we add or subtract them?
         |
         |""".stripMargin
    ),
    <.p(WordAlgebraWidget())
  ))
  .veautifulSlide(<.div(
    <.h2("It's word prediction, but..."),
    <.p(RelationshipRepeaterWidget()),    
  ))
  .veautifulSlide(<.div(
    <.h2("Pick a number from 1 to 10..."),
    RandomNumberChart()
  ))
  .veautifulSlide(<.div(
    <.h2("Pick a number from 1 to 100..."),
    RandomNumberChart(
      RandomNumberChart.oneToHundred, yMax = 25, yStep = 5, xStep = 10,
      note = Some(37 -> "LLM favourite: 37")
    ),
    <.p(^.style := "font-size: 24px; color: #888;", "Illustrative: shape based on commonly reported favourites, not measured data")
  ))
  .markdownSlide(willCcBy)
  .renderSlides
