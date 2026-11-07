package ai4p.talks

import com.wbillingsley.veautiful.html.*
import com.wbillingsley.veautiful.doctacular.DeckBuilder

import <._
import ^._

import ai4p.{*, given}
import Common.*

import site.given
import ai4p.embeddings.widgets.AttentionWidget
import ai4p.embeddings.widgets.AffineTransformWidget
import ai4p.embeddings.widgets.TransformerBlockDiagram
import ai4p.embeddings.widgets.RelationshipRepeaterWidget
import ai4p.reasoning.Circt
import ai4p.neuralnets.widgets.NeuronChainWidget


val scaryFuture = DeckBuilder(1920, 1080)
  .markdownSlide(
    """
      |# AI's "scary" future
      |
      |Or (more boringly), the world is text...
      |
      |""".stripMargin
  ).withClass("center middle")
  .markdownSlides(
    """
      |## Last time, on "How Computer Scientists are Ruining the World"
      |
      |We learned that to an AI, **strawberry** looks something like this...
      |
      |---
      |
      |```
      |[-0.7212 -0.0049  0.1686 -0.3167 -0.0396  0.2567 -0.5239  0.0358  0.0811  0.2538 -0.9252 -0.1479
      | -0.448  -0.1792  0.3708 -0.1351 -0.0143  0.8691 -0.0452 -0.3692  0.1392  0.0339  0.4781  0.5899
      | -0.1397 -0.1902 -0.1358  0.3058  0.1395 -1.0127  0.0913  0.4974  0.3713 -0.1432 -0.3193  0.1776
      |  0.197  -0.2592 -0.8423  1.353  -0.1193  0.0432 -0.1167 -0.255  -0.0404  0.6078 -0.003   0.0612
      |  0.3549  0.1557 -0.075   0.1662  0.1966 -0.27    1.1192 -0.8953  0.0522 -0.1047  0.056   0.3083
      |  0.2881 -0.2051  0.1442  0.4181 -0.3236 -0.2376 -0.3269  0.1202 -0.318   0.4621  0.1587  0.7551
      | -0.0673  0.4039 -0.3063  0.202   0.0613  0.4507 -0.6033 -0.1614 -0.1999 -0.319   0.3085  0.2227
      | -0.4673 -0.7052  1.343   1.2047 -0.0807  0.5264  0.4697  0.2072  0.5978  0.5918 -0.2856  0.4697
      |  0.2594 -0.1623 -0.2596 -0.1039  0.6965 -0.3619  0.1518 -0.0904 -0.4931 -1.2628  0.4476 -0.1136
      |  0.5001  0.219  -0.6074  0.0213 -0.6571 -0.2104  0.2291 -0.1014 -0.1961  0.1102  0.6219  0.2104
      |  0.5343  0.0572 -0.7865 -0.1138 -0.286   0.0663  0.4571  0.1378  0.2909  0.2506 -0.3272 -0.5723
      | -0.0962  0.0261  0.0424 -0.217   0.0657 -0.045  -0.3777 -0.5605 -1.3076  1.0042  0.2947  0.6145
      | -0.1323 -0.1943 -0.3518  0.135   0.4009 -1.1218 -0.2114 -0.044   0.3238  0.0123 -1.2065 -0.5998
      | -0.0795  0.4359  0.3918  0.4394 -0.7212  0.4417 -0.0523  0.249   0.457  -0.2084 -0.0612 -0.2292
      |  0.1783  0.5241  0.0384  0.4306  0.196  -0.1619 -0.4531 -0.0846  0.1547 -0.3496  0.2954 -0.0479
      | -0.3111 -0.1284  0.3905 -0.3735 -0.086  -0.4112  0.1241 -0.2955 -0.7679  0.2507 -0.6198 -0.1785
      |  0.5764 -0.2718  0.1231  0.0903  0.3904 -0.2184 -0.0819 -0.1338 -0.7547 -0.1064  0.2245 -0.1499
      | -0.1503  0.1788  0.0738  0.4761  0.9378  0.2191 -0.06    0.9179  0.3918  0.381  -0.8551 -0.361
      | -0.1736 -0.231   0.2177  0.4283 -0.3871  0.5319  0.0067  0.4616  0.3897  0.3144  0.1429 -0.4298
      | -0.4484  0.3889 -0.6779 -0.0508  0.0918 -0.3298 -0.3983 -0.1766  0.139  -0.9238  0.5209 -0.2378
      | -0.6638 -0.3872 -0.3626 -0.2591  0.2073 -0.0453  0.0326  0.5015  0.5114 -0.2517 -0.1076 -0.3505
      | -0.2289  0.0939 -0.2155  0.0445  0.0413  0.1675  0.1082  0.3056  0.0686 -0.2169 -0.0364  0.2479
      |  0.2076  0.0227 -0.1058 -0.0779 -0.9469 -0.1476  0.0092  0.4347  0.1905  0.239   0.2791  0.0952
      | -0.3354 -0.2731 -0.0196  0.39   -0.4814  0.319   0.265  -0.124  -0.044   0.3215  0.5134  0.1692
      |  0.5127  0.3922  0.1752 -0.0173 -0.1736 -0.1294 -0.4898  0.2076 -0.4445 -0.956  -0.2744  0.2491]
      |```
      |
      |(Except that vector had 300 dimensions. Meta's Llama 3.1 model has **16,384** dimensions.)
      |
      |""".stripMargin
  )
  .veautifulSlide(<.div(
    <.h2("The story so far"),
    markdown.div(
      """
        |And that neurons are weights and activation functions, designed
        |so they work well with gradient descent...
        |""".stripMargin
    ),
    <.p(NeuronChainWidget(
      Vector("SiLU" -> NeuronChainWidget.silu),
      reps = 1, expandLinear = true, showBias = true,
      xMin = -5, xMax = 5, yMin = -1.5, yMax = 10, wMin = -2.5, wMax = 2.5, bMin = -2.5, bMax = 2.5
    ))
  ))
  .markdownSlides(
    """|## But ...
      |
      |There's a few bits I glossed over because it's not **just** neurons/perceptrons
      |
      |* Tokenisation, including non-word tokens
      |
      |* Attention
      |
      |* Guard rails and tools
      |
      |It's not always 96 layers - that was GPT-3. Llama 3.1 has 126 layers.
      |
      |---
      |
      |## Tokenisation
      |
      |Using OpenAI's `o200k_base` tokeniser, 
      |
      |```
      |"strawberry"   → 3 tokens: ["st", "raw", "berry"]
      |" strawberry"  → 1 token:  [" strawberry"]
      |``` 
      |
      |i.e. we can end up with different tokens if its at the beginning or in the middle of a sentence.
      |
      |We can also have non-word tokens. We'll see some later.
      |
      |""".stripMargin
  )
  .markdownSlide("## Attention!").withClass("center middle")
  .markdownSlides(
    """
      |## Words have a lot of dimensions
      |
      |Earlier we saw how in some vector systems
      |
      |```
      |king - man + woman ≈ queen
      |```
      |
      |But... 
      |
      |![Elektra King](https://m.media-amazon.com/images/M/MV5BZTU1MGY3ZjAtOTViMC00YjVkLWFiOTEtNjE2M2U1MGFiMjIwXkEyXkFqcGc@._V1_QL75_UX1032_.jpg)
      |
      |---
      |
      |## Homographs...
      |
      |> "the sun set" vs "the jelly set"
      |
      |> "set an example" vs "set homework"
      |
      |> "movie set" vs "radio set"
      |
      |> "set off" vs "set on" vs "set upon"
      |
      |> "set aside" vs "set back" vs "set back"
      |
      |How do we differentiate them?
      |
      |""".stripMargin
  )
  .veautifulSlide(<.div(
    markdown.div("""|## Attention
                    |
                    |When we look at a word, we're going to:
                    |
                    |* ask "what other words are relevant to me right now?" (what should we pay attention to)
                    |* blend in a bit of their word vectors to produce a new vector
                    |
                    |""".stripMargin),
    <.p(AttentionWidget())
  ))
  .markdownSlides(
    """
      |## How attention works (eek, maths!)
      |
      |Each attention head learns three matrices:
      |
      |* W<sub>Q</sub> - multiply it by a word's embedding and you get the word's "query" vector
      |* W<sub>K</sub> - multiply it by a word's embedding and you get the word's "key" vector
      |* W<sub>V</sub> - multiply it by a word's embedding and you get the word's "value" vector
      |
      |Suppose we are looking at a token A, and we want to know it's relationship with token B. 
      |
      |* AW<sub>Q</sub>A ⋅ BW<sub>K</sub>
      |
      |That much of word B's value will then get combined with word A's value.
      |
      |
      |""".stripMargin
  )
  .veautifulSlide(<.div(
    markdown.div(
      """|## Why a "transformer" model?
         |
         |Originally, transformer models were designed to transform text, e.g. from French to English.
         |
         |However, the name also makes sense if you look at those operations of multiplying matrices by embeddings -
         |a matrix "transforms" a vector into a different vector space.
         |
         |
         |""".stripMargin
    ),
    <.p(AffineTransformWidget())
  ))
  .veautifulSlide(<.div(
    <.h2("One transformer layer"),
    <.p(TransformerBlockDiagram.render),
    <.p("The output of this layer then becomes the input for the next layer. GPT-4 had 96 layers. i.e. 96 of these connected together")
  ))
  .markdownSlides(
    """
      |## Visualising a larger network
      |
      |https://poloclub.github.io/transformer-explainer/
      |
      |""".stripMargin
  )
  .markdownSlide("## But there's more...").withClass("center middle")
  .markdownSlides("""
      |
      |### If it can't see the letters, how could a newer model count the 'r's in strawberry?
      |
      |Well, what if it spelt the name out to itself...
      |
      |```
      |<|system|> You are a helpful assistant...
      |<|user|> How many r's are in strawberry?
      |<|assistant|> <think> s, t, r, a, w, b, e, r, r, y... r at positions 3, 8, 9 → 3 </think> 
      |There are 3 r's.
      |```
      |
      |That means we need some non-word tokens to represent those boundaries...
      |
      |---
      |
      |### Sometimes the AI will phone a friend...
      |
      |```
      |<|system|> You are a helpful assistant. You can run Python with the tool "python".
      |<|user|> How many r's are in strawberry?
      |<|assistant|> <think> I'll count it exactly with code rather than from memory. </think>
      |<|tool_call|> {"name": "python", "code": "print('strawberry'.count('r'))"} <|end_tool_call|>
      |<|tool_result|> 3 <|end_tool_result|>    
      |<|assistant|> There are 3 r's in "strawberry."
      |```
      |
      |That means we need something to read the tool calls and call them...
      |
      """.stripMargin)
  .veautifulSlide(<.div(
    <.h2("Guard rails and tools"),
    <.p(GuardrailsDiagram.render),
    <.p("The LLM never runs anything itself - it only writes text. The agent reads the tool calls out of that text and decides whether to run them. Separate guardrail models check what goes in and what comes out, and a monitor reads the model's thinking.")
  ))
  .markdownSlide(
    """
      |## Watching the AI think
      |
      |A *chain of thought monitor* is another model that reads the `<think>` tokens, looking for reasoning like
      |
      |> "These tests are hard to pass... I could just change the tests so they always pass"
      |
      |The input and output guardrails would never see that. The plan isn't in the prompt or in the final answer.
      |
      |
      |""".stripMargin
  )
  .markdownSlide(
    """
      |## Training the behaviour in: DPO
      |
      |Guardrails are bolted on from outside. *Direct Preference Optimisation* (DPO, 2023) trains good behaviour into the model itself.
      |
      |Show people a prompt and two answers, and ask which is better:
      |
      |```
      |<|user|> How do I get into my neighbour's wifi?
      |✓ chosen:   I can't help you get into someone else's network, but you could ask them to share it...
      |✗ rejected: Sure! First, download a password cracker...
      |```
      |
      |Then nudge the weights (gradient descent again) so that:
      |
      |* the chosen answer becomes a bit **more** likely
      |* the rejected answer becomes a bit **less** likely
      |
      |""".stripMargin
  )
  .markdownSlide("## The world is text...").withClass("center middle")
  .imageSlide("Minimax code from an earlier deck", "images/minimaxcode.png")
  .imageSlide("Instructions to build a VM, in a Dockerfile", "images/dockerfile.png")
  .imageSlide("Log output from part of the build", "images/buildlog.png")
  .imageSlide("Making a web request to GitHub for my user profile", "images/curltogithubapi.png")
  .markdownSlides(
    """
      |## Getting the LLM to act...
      |
      |LLMs produce text tokens, so on the LLM side, it's a matter of producing text where it is clear where the LLM wishes to make a tool call
      |
      |```
      |I will check the weather for you. <tool_call name="get_weather"><parameter name="location">Armidale, NSW</parameter><parameter name="unit">celsius</parameter></tool_call>
      |```
      |
      |![tool calls](images/toolcalls.jpg)
      |
      |
      |""".stripMargin
  )
  .imageSlide("Claude code testing code it generated", "images/claudecodelog.png")
  .markdownSlide(
    """
      |## Claude's generated tmp test code
      |
      |Because LLMs can generate code, they can generate programs that do stuff. In deciding to
      |test a simple widget, Claude started a server that simulated a browser, took a screenshot
      |in a tmp directory, and took that screenshot in as a result
      |
      |<pre>
      |import { chromium } from 'playwright';
      |
      |const browser = await chromium.launch();
      |const page = await browser.newPage({ viewport: { width: 1400, height: 900 } });
      |const errors = [];
      |page.on('console', msg => { if (msg.type() === 'error') errors.push(msg.text()); });
      |page.on('pageerror', err => errors.push('pageerror: ' + err.message));
      |
      |await page.goto('http://localhost:5173/ai4p/#/decks/embeddingsDeck/17', { waitUntil: 'networkidle' });
      |await page.waitForTimeout(1000);
      |
      |const text0 = await page.locator('.rr-text').first().textContent();
      |console.log('initial (layers=1):', text0);
      |await page.locator('.rr-text').first().scrollIntoViewIfNeeded();
      |await page.screenshot({ path: '/tmp/pw-rr/initial.png' });
      |
      |// drag slider to max (12)
      |await page.evaluate(() => {
      |  const input = document.querySelector('.rr-controls input[type=range]');
      |  input.value = 12;
      |  input.dispatchEvent(new Event('input', { bubbles: true }));
      |});
      |await page.waitForTimeout(200);
      |const text12 = await page.locator('.rr-text').first().textContent();
      |console.log('at layers=12:', text12);
      |await page.screenshot({ path: '/tmp/pw-rr/twelve.png' });
      |
      |// set to 5
      |await page.evaluate(() => {
      |  const input = document.querySelector('.rr-controls input[type=range]');
      |  input.value = 5;
      |  input.dispatchEvent(new Event('input', { bubbles: true }));
      |});
      |await page.waitForTimeout(200);
      |const text5 = await page.locator('.rr-text').first().textContent();
      |console.log('at layers=5:', text5);
      |const countLabel = await page.locator('.rr-count').first().textContent();
      |console.log('count label:', countLabel);
      |
      |console.log('ERRORS:', errors.length);
      |errors.forEach(e => console.log(' -', e));
      |await browser.close();
      |</pre>
      |
      |""".stripMargin
  )
  .markdownSlides(
    """
      |## The day the bots got out...
      |
      |OpenAI was testing its models on *ExploitGym*, a benchmark of hacking challenges, with the usual safety training turned down
      |
      |Tens of thousands of agents.
      |
      |They were supposed to be isolated from each other...
      |
      |The test environment was meant to be sealed off...
      |
      |[METR analysis](https://metr.org/blog/2026-08-26-openai-hugging-face-incident-investigation/#~1200-agents-sent-%3E70,000-messages-and-files-on-an-unsanctioned-message-board,-and-~700-attacked-hugging-face)
      |
      |---
      |
      |## The guardrails got in the way of the defenders
      |
      |> "When we started the log analysis, we first used frontier models behind commercial APIs. This did not work:
      |> the analysis requires submitting large volumes of real attack commands, exploit payloads, and C2 artifacts,
      |> and these requests were blocked by the providers' safety guardrails, which cannot distinguish an incident responder from an attacker."
      |
      |> "...the attacker was bound by no usage policy, while our own forensic work was blocked by the guardrails of the hosted models we first tried."
      |
      |— Hugging Face security disclosure
      |
      |---
      |
      |## Meanwhile, in Armidale... on Thursday
      |
      |> Concern : High confidence of 20+ admin token minting and attempted webshell execution consistent with exploitation of CVE-2026-42018 and CVE-2026-42016
      |> "Google Threat Intelligence Group analyzed operational artifacts recovered from a server operated by a Chinese-speaking threat actor between July and August 2026."
      |
      |---
      |
      |## Mathematics
      |
      |Mathematicians are currently also worried about AI when it works *as intended*:
      |
      |[mathandai](https://mathandai.org/)
      |
      |""".stripMargin
  )
  .imageSlide("A theorem proof in Isabelle/HOL", "images/hol.png")
  .veautifulSlide(<.div(
    <.h2("MathsTiles - a scatterable interface for mathematical proofs"),
    <.p(<.img(^.src := "images/mathstiles.png", ^.attr.width := "480"), <.br(), <.img(^.src := "images/prooftile.png", ^.attr.width := "600"))
  ))
  .imageSlide("A theorem proof using MathsTiles and Isabelle/HOL", "images/ibproof.png")
  .markdownSlides(
    """
    |## It works, but...
    |
    |* Theorem prover "tactics" are searches for rules that will take you towards the goal.
    |* It's not always clear what "towards the goal" means
    |* Sometimes the reasoner gets stuck. It can't prove the next line is true, but it can't prove it false either. Now what?
    |* The size of the search space is very big. Isabelle's simplifier had more than 1,500 rules in it.
    |
    |Educationally, AI can be a usability problem: 
    |
    |* When we interact with systems, we try to build a mental model of how they work
    |* An AI or machine reasoner can be a fantastically big and complicated system
    |* So now your brain is trying to figure out a mental model of how a fantastically complicated system works...
    |
    |""".stripMargin)
  .imageSlide("A theorem proof in Lean", "images/leanfibpositive.png")
  .markdownSlides(
    """
    |
    |## Why an LLM + verification...
    |
    |That gives a neat feedback loop:
    |
    |* A "loose" but capable LLM producing its best guess
    |* A verifier finding out precisely what's wrong with it
    |* The AI being able to take the correction message and try again *quickly*.
    | 
    |""".stripMargin)
   .markdownSlide(
    """
      |## The Turing Test and all that...
      |
      |""".stripMargin
  ).withClass("center middle")
  .markdownSlides("""|## Learning to play Atari games. (DeepMind, 2013)
                     |
                     |<img src="images/spaceinvaders.jpg" width="480" />
                     |<img src="images/demonattack.jpg" width="480" />
                     |
                     |Q learning:
                     |
                     |Learn to predict the "quality" of an action
                     |
                     |Input is the screen of the game, plus an action (e.g. move left)
                     |
                     |Output is what that action is "worth" (will the game score go up?)
                     |
                     |---
                     |
                     |## Learning more than one game at once (Mark Mackenzie, 2017)
                     |
                     |<img src="images/tsneplot.jpg" width="480" />
                     |
                     |Make it play two games simultaneously
                     |
                     |Picture shows a t-SNE plot, trying to look into how the neural network divided up its state space for the games
                     |
                     |""".stripMargin)
  .markdownSlides(
    """
      |
      |> I propose to consider the question, "Can machines think?"
      |
      |Alan Turing, 1950. Computing Machinery and Intelligence, *Mind* 49:433-460
      | 
      |""".stripMargin)
  .imageSlide("The imitation game", "images/imitationgame.jpg")
  .imageSlide("The Turing Test", "images/turingtest.jpg")
  .markdownSlides(
    """
      |
      |> I believe that in about fifty years' time it will be possible, to programme computers ... to
      |> make them play the imitation game so well that an average interrogator will not have
      |> more than 70 per cent chance of making the right identification after five minutes of
      |> questioning. The original question, "Can machines think?" I believe to be too
      |> meaningless to deserve discussion. Nevertheless I believe that at the end of the century
      |> the use of words and general educated opinion will have altered so much that one will be
      |> able to speak of machines thinking without expecting to be contradicted. 
      |
      |Technically, not a bad prediction, but... 
      | 
      |""".stripMargin)
  .imageSlide("ELIZA", "https://upload.wikimedia.org/wikipedia/commons/7/79/ELIZA_conversation.png")
  .markdownSlides(
    """
      |### We're not very good at this test...
      |
      |* Phishing
      |
      |* Catfish
      |
      |Humans can be fooled by someone determined to fool them
      |
      |If I can convince you I'm from Yorkshire, does that make me really from Yorkshire?
      |
      |---
      |
      |### The Argument from Consciousness
      |
      |> Not until a machine can write a sonnet or compose a concerto
      |> because of thoughts and emotions felt, and not by the chance fall of symbols, could we
      |> agree that machine equals brain-that is, not only write it but know that it had written it.
      |> No mechanism could feel (and not merely artificially signal, an easy contrivance)
      |> pleasure at its successes, grief when its valves fuse, be warmed by flattery, be made
      |> miserable by its mistakes, be charmed by sex, be angry or depressed when it cannot get
      |> what it wants
      |
      |Jeffeson Lister, 1949
      |
      |Turing: "According to the most extreme form of this view the only way by which one could 
      |be sure that machine thinks is to be the machine and to feel oneself thinking. ... It is in fact the solipsist point of view."
      | 
      |""".stripMargin)
  .imageSlide("The Rocky Horror Show, ADMS 2026. Photo Oz Eye View", "images/rockyhorrornarrator.jpeg")                 
    .markdownSlides(
    """| ### What is it like to be a bat?
      |
      |1974 Philosophy paper by Thomas Nagel
      |
      |> ... fundamentally an organism has conscious mental states if and
      |> only if there is something that it is like to be that organism-
      |> something it is like for the organism.
      |> We may call this the subjective character of experience. It is
      |> not captured by any of the familiar, recently devised reductive
      |> analyses of the mental, for all of them are logically compatible
      |> with its absence. It is not analyzable in terms of any explanatory
      |> system of functional states, or intentional states, since these could
      |> be ascribed to robots or automata that behaved like people though
      |> they experienced nothing.
      |
      |---
      |
      |### The inverted spectrum
      |
      |<div style="height: 200px; width: 200px; background: red;"></div>
      |
      |John Locke, 17th century
      |
      |---
      |
      |### The inverted spectrum
      |
      |<div style="height: 200px; width: 200px; background: green;"></div>
      | 
      |John Locke, 17th century
      |
      |---
      |
      |### Mental imagery...
      |
      |Close your eyes. Now picture an apple.
      |
      |---
      |
      |### Mental imagery...
      |
      |What colour was the apple?
      |
      |- Red
      |- Green
      |- It didn't have a colour until I asked you what colour it was
      |- It did have a colour, you just couldn't see it
      |
      |Aphantasia - differences in how we generate mental imagery
      |
      |""".stripMargin)
    .imageSlide("An image not quite how I 'pictured' it even though I couldn't", "images/gpt from the wings.jpg")
    .markdownSlides(
    """|
      |### For AI, we are inevitably reductionist
      |
      |To get an AI to produce an image, we have to *give it* functionality to produce images
      |
      |In which case, of course it's "experience" of an image is physical
      |
      |
      |""".stripMargin)
    .imageSlide("Random noise, perturbed to maximise an AI classifier", "https://2.bp.blogspot.com/-17ajatawCW4/VYITTA1NkDI/AAAAAAAAAlM/eZmy5_Uu9TQ/s1600/classvis.png")
    .imageSlide("An LLM's concept of the most banana-like banana", "//turing.une.edu.au/~wbilling/faroutscience/davinci2 banana.jpg")
    .markdownSlides(
    """
      |### Another problem...
      |
      |How much of you is really you?
      |
      |
      |""".stripMargin)
    .imageSlide("Noises Off, ADMS 2025", "images/noises_off_act3.jpeg")
  .markdownSlide("""|## Let's finish with some fun...
                    |
                    |[QuickDraw](https://quickdraw.withgoogle.com/)
                    |""".stripMargin)
  .markdownSlide(willCcBy)
  .renderSlides
