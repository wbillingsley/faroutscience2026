package ai4p.talks

import com.wbillingsley.veautiful.html.{<, SVG, DSvgContent, VHtmlContent, ^}

/**
 * A static box diagram of what sits around an LLM in a deployed chatbot: an input guardrail checking
 * the prompt, an output guardrail checking the reply (either can block and substitute a refusal), and
 * the agent harness underneath that spots tool calls in the LLM's output, runs them, and feeds the
 * results back in. Off to the side, a chain of thought monitor reads the model's reasoning and flags
 * anything suspicious for people to review. Purely illustrative layout.
 */
object GuardrailsDiagram {

  private case class Box(x: Double, y: Double, w: Double, h: Double, label: String, sub: Seq[String], fill: String) {
    def cx = x + w / 2
    def bottom = y + h
    def right = x + w
  }

  private val font = "'Lato', sans-serif"

  private val mainY = 100.0
  private val mainH = 120.0

  private val user = Box(10, mainY, 180, mainH, "User", Seq("types a prompt"), "#f3f4f6")
  private val inGuard = Box(270, mainY, 280, mainH, "Input guardrail", Seq("a classifier checks the prompt", "jailbreak? harmful? PII?"), "#fee2e2")
  private val llm = Box(630, mainY, 340, mainH, "LLM", Seq("predicts the next token,", "one token at a time"), "#dbeafe")
  private val outGuard = Box(1050, mainY, 280, mainH, "Output guardrail", Seq("checks the generated text", "harmful? leaking secrets?"), "#fee2e2")
  private val response = Box(1410, mainY, 180, mainH, "Response", Seq("shown to the user"), "#f3f4f6")

  private val agent = Box(580, 340, 440, 120, "Agent (the harness)", Seq("spots <|tool_call|>, checks permissions,", "runs the tool, pastes back <|tool_result|>"), "#fef3c7")

  private val tools = Vector(
    Box(450, 530, 220, 80, "Python", Seq("run code"), "#dcfce7"),
    Box(690, 530, 220, 80, "Web search", Seq("fetch pages"), "#dcfce7"),
    Box(930, 530, 220, 80, "Files & email", Seq("act in the world"), "#dcfce7"),
  )

  // Sits off the main path: it watches the reasoning and flags it for people, rather than blocking the reply
  private val cotMonitor = Box(1200, 470, 360, 100, "CoT monitor", Seq("reads the <think> tokens:", "planning to cheat or deceive?"), "#ede9fe")

  private val w = 1600
  private val h = 650

  /** A straight line from (x1, y1) with an arrowhead whose tip is at (x2, y2) */
  private def arrow(x1: Double, y1: Double, x2: Double, y2: Double, colour: String = "#999", dashed: Boolean = false): DSvgContent =
    val len = Math.hypot(x2 - x1, y2 - y1)
    val (ux, uy) = ((x2 - x1) / len, (y2 - y1) / len)
    val (bx, by) = (x2 - 12 * ux, y2 - 12 * uy)
    SVG.g(
      SVG.line(^.attr("x1") := x1, ^.attr("y1") := y1, ^.attr("x2") := bx, ^.attr("y2") := by,
        ^.attr("stroke") := colour, ^.attr("stroke-width") := "2.5",
        ^.attr("stroke-dasharray") := (if dashed then "8 6" else "none")),
      SVG.polygon(^.attr("points") := s"$x2,$y2 ${bx - 7 * uy},${by + 7 * ux} ${bx + 7 * uy},${by - 7 * ux}", ^.attr("fill") := colour)
    )

  private def label(x: Double, y: Double, text: String, anchor: String = "middle", colour: String = "#555", size: Int = 17): DSvgContent =
    SVG.text(^.attr("x") := x, ^.attr("y") := y, ^.attr("text-anchor") := anchor,
      ^.attr("font-size") := size, ^.attr("font-family") := font, ^.attr("fill") := colour, text)

  private def boxSvg(b: Box): DSvgContent =
    SVG.g(
      (Seq(
        SVG.rect(^.attr("x") := b.x, ^.attr("y") := b.y, ^.attr("width") := b.w, ^.attr("height") := b.h,
          ^.attr("rx") := 10, ^.attr("fill") := b.fill, ^.attr("stroke") := "#888", ^.attr("stroke-width") := "1.5"),
        label(b.cx, b.y + 38, b.label, colour = "#222", size = 24),
      ) ++ b.sub.zipWithIndex.map { (s, i) => label(b.cx, b.y + 66 + i * 22, s, colour = "#666") })*
    )

  /** Dashed red arrow dropping out of a guardrail to show it can stop the conversation */
  private def blocked(b: Box): DSvgContent =
    SVG.g(
      arrow(b.cx, b.bottom, b.cx, b.bottom + 55, colour = "#dc2626", dashed = true),
      label(b.cx, b.bottom + 82, "✗ blocked → canned refusal", colour = "#dc2626")
    )

  def svg: DSvgContent =
    val mid = mainY + mainH / 2
    SVG.svg(^.attr("width") := w, ^.attr("height") := h, ^.attr("viewBox") := s"0 0 $w $h",
      // The main path a message travels
      arrow(user.right, mid, inGuard.x, mid),
      arrow(inGuard.right, mid, llm.x, mid),
      arrow(llm.right, mid, outGuard.x, mid),
      arrow(outGuard.right, mid, response.x, mid),
      blocked(inGuard),
      blocked(outGuard),

      // The tool-call loop
      arrow(llm.cx - 40, llm.bottom, llm.cx - 40, agent.y),
      label(llm.cx - 52, (llm.bottom + agent.y) / 2 + 6, "tool_call", anchor = "end"),
      arrow(llm.cx + 40, agent.y, llm.cx + 40, llm.bottom),
      label(llm.cx + 52, (llm.bottom + agent.y) / 2 + 6, "tool_result", anchor = "start"),
      SVG.g(tools.map(t => arrow(agent.cx + (t.cx - agent.cx) / 3, agent.bottom, t.cx, t.y))*),

      // The chain of thought monitor, watching from the side
      arrow(llm.right - 30, llm.bottom, cotMonitor.x + 50, cotMonitor.y, colour = "#7c3aed", dashed = true),
      label(1125, 345, "reasoning", anchor = "start", colour = "#7c3aed"),
      arrow(cotMonitor.cx, cotMonitor.bottom, cotMonitor.cx, cotMonitor.bottom + 32, colour = "#7c3aed", dashed = true),
      label(cotMonitor.cx, cotMonitor.bottom + 58, "⚑ flag for human review", colour = "#7c3aed"),

      SVG.g((Seq(user, inGuard, llm, outGuard, response, agent, cotMonitor) ++ tools).map(boxSvg)*)
    )

  def render: VHtmlContent = <.div(svg)
}
