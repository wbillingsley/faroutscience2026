package ai4p.talks

import com.wbillingsley.veautiful.html.*
import com.wbillingsley.veautiful.doctacular.DeckBuilder

import <._
import ^._

import ai4p.{*, given}
import Common.*

import site.given


val underTheHood = DeckBuilder(1920, 1080)
  .markdownSlide(
    """
      |# AI under the hood
      |
      |""".stripMargin
  ).withClass("center middle")
  .markdownSlide(
    """
      |## Lorem ipsum
      |
      |Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
      |et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
      |aliquip ex ea commodo consequat.
      |
      |* Duis aute irure dolor in reprehenderit
      |* Excepteur sint occaecat cupidatat non proident
      |* Sunt in culpa qui officia deserunt mollit anim id est laborum
      |
      |""".stripMargin
  )
  .markdownSlide(willCcBy)
  .renderSlides
