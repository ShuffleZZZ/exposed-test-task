package com.github.shufflezzz.matcher.automata

import com.github.shufflezzz.matcher.grammar.RegexParser
import com.github.shufflezzz.matcher.grammar.RegexVisitor
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor
import org.antlr.v4.runtime.tree.TerminalNode

class AutomataVisitor : AbstractParseTreeVisitor<NfaEpsAutomata>(), RegexVisitor<NfaEpsAutomata> {

    private val automataBuilder = AutomataBuilder()

    override fun visitRegex(ctx: RegexParser.RegexContext) =
        automataBuilder.or(ctx.concat().map(::visitConcat))

    override fun visitConcat(ctx: RegexParser.ConcatContext) =
        automataBuilder.and(ctx.closure().map(::visitClosure))

    override fun visitClosure(ctx: RegexParser.ClosureContext) =
        ctx.OPERATOR().fold(visitToken(ctx.token()), automataBuilder::closure)

    override fun visitToken(ctx: RegexParser.TokenContext): NfaEpsAutomata {
        val nextRule = ctx.children?.find { it !is TerminalNode } ?: error("Unsupported token")

        return visit(nextRule)
    }

    override fun visitRange(ctx: RegexParser.RangeContext) =
        automataBuilder.or(ctx.char_().map(::visitChar))

    override fun visitChar(ctx: RegexParser.CharContext) =
        automataBuilder.char(ctx.text.first())
}
