package ai4p.talks

import com.wbillingsley.veautiful.html.{<, SVG, ^}

/** Line chart: how often LLMs vs humans vs true randomness pick each number from 1 to `max` */
object RandomNumberChart {

  case class Series(name:String, colour:String, values:Seq[Double])

  val oneToTen = Seq(
    Series("True Random",     "#d63fae", Seq(10.0, 10, 10, 10, 10, 10, 10, 10, 10, 10)),
    Series("Human Selection", "#3b6fe0", Seq(2.0, 5, 15, 10, 12, 8, 28, 10, 7, 3)),
    Series("LLM Selection",   "#f08c00", Seq(0.5, 1.5, 6, 4, 2, 1, 81, 1.5, 2, 0.5)),
  )

  /**
   * Illustrative only, normalised to 100%. Each number starts at a baseline weighted by its last digit
   * (odd endings, especially 7 and 3, are favoured), round numbers sit in valleys, and then peaks
   * at commonly reported favourites override the result.
   */
  private def illustrative(baseline:Double, lastDigit:Int => Double, round:Double, peaks:Map[Int, Double]) =
    val raw = for n <- 1 to 100 yield peaks.getOrElse(n,
      baseline * lastDigit(n % 10) * (if n % 10 == 0 then round else if n % 5 == 0 then Math.sqrt(round) else 1)
    )
    raw.map(_ * 100 / raw.sum)

  val oneToHundred = Seq(
    Series("Human Selection", "#3b6fe0", illustrative(1,
      Map(7 -> 1.8, 3 -> 1.5, 1 -> 1.2, 9 -> 1.2, 5 -> 1.1).withDefaultValue(0.8), round = 0.3,
      Map(37 -> 4, 42 -> 3.2, 67 -> 3, 73 -> 2.7, 47 -> 2.4, 57 -> 2.2, 27 -> 2.1, 17 -> 1.9, 77 -> 1.9)
    )),
    Series("LLM Selection",   "#f08c00", illustrative(0.3,
      Map(7 -> 3.0, 3 -> 2.0, 1 -> 1.3, 9 -> 1.3, 5 -> 1.0).withDefaultValue(0.6), round = 0.1,
      Map(37 -> 22, 42 -> 14, 67 -> 12, 73 -> 10, 47 -> 8, 57 -> 7, 27 -> 5)
    )),
  )

  val (w, h) = (1200, 680)
  val (left, right, top, bottom) = (100, 40, 70, 90)
  val (pw, ph) = (w - left - right, h - top - bottom)

  def apply(
    series:Seq[Series] = oneToTen, yMax:Int = 100, yStep:Int = 10, xStep:Int = 1,
    note:Option[(Int, String)] = Some(7 -> "LLM mode collapse peak (7, 81%)")
  ) = {
    val max = series.head.values.length
    def x(n:Int) = left + (n - 0.5) * pw / max
    def y(v:Double) = top + ph * (1 - v / yMax)
    val dotSize = if max > 20 then 4 else 8

    <.svg(
      ^.attr("width") := w, ^.attr("height") := h, ^.attr("viewBox") := s"0 0 $w $h",
      ^.attr("font-family") := "sans-serif", ^.attr("font-size") := 24, ^.attr("fill") := "#555",

      // Grid and axis labels
      for v <- 0 to yMax by yStep yield SVG.g(
        SVG.line(^.attr("x1") := left, ^.attr("x2") := w - right, ^.attr("y1") := y(v), ^.attr("y2") := y(v),
          ^.attr("stroke") := (if v == 0 then "#888" else "#e4e4e4")),
        SVG.text(^.attr("x") := left - 12, ^.attr("y") := y(v) + 8, ^.attr("text-anchor") := "end", v.toString)
      ),
      for n <- 1 to max if n == 1 || n % xStep == 0 yield
        SVG.text(^.attr("x") := x(n), ^.attr("y") := h - bottom + 34, ^.attr("text-anchor") := "middle", n.toString),
      SVG.text(^.attr("x") := left + pw / 2, ^.attr("y") := h - 10, ^.attr("text-anchor") := "middle", s"Number selected (1 to $max)"),
      SVG.text(^.attr("transform") := s"translate(30, ${top + ph / 2}) rotate(-90)", ^.attr("text-anchor") := "middle", "Selection probability (%)"),

      // Lines and points
      for Series(_, colour, values) <- series yield SVG.g(
        SVG.polyline(^.attr("fill") := "none", ^.attr("stroke") := colour, ^.attr("stroke-width") := (if max > 20 then 2 else 4),
          ^.attr("points") := values.zipWithIndex.map((v, i) => s"${x(i + 1)},${y(v)}").mkString(" ")),
        for (v, i) <- values.zipWithIndex yield
          SVG.circle(^.attr("cx") := x(i + 1), ^.attr("cy") := y(v), ^.attr("r") := dotSize,
            ^.attr("fill") := colour, ^.attr("stroke") := "white", ^.attr("stroke-width") := 2)
      ),
      for (n, label) <- note.toSeq yield
        SVG.text(^.attr("x") := x(n) + 18, ^.attr("y") := y(series.last.values(n - 1)) - 10, label),

      // Legend
      for (s, i) <- series.zipWithIndex yield SVG.g(
        ^.attr("transform") := s"translate(${left + i * 260}, 30)",
        SVG.circle(^.attr("cx") := 10, ^.attr("cy") := 0, ^.attr("r") := 10, ^.attr("fill") := s.colour),
        SVG.text(^.attr("x") := 28, ^.attr("y") := 8, s.name)
      ),
    )
  }
}
