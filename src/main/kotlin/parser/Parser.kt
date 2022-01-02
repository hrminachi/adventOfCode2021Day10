/*
 * Copyright (C) <2021>  <Hamid Minachi>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package parser

class Parser {
    private val symbols = setOf(AngelBracket(), CurlyBracket(), SquareBracket(), RoundBracket())
    var indexInLine=0
    var navString =""

    fun parseText(text:String):Int =
        text.replace(" ","")
            .split('\n')
            .map {parseLine(it)}
            .filter { it.outcome == Outcome.INVALID }
            .sumOf { it.score }

    fun parseLine(nav:String):ParseResult {
        indexInLine = 0
        navString = nav
        return parse()
    }

    private fun parse(): ParseResult {
        if (indexInLine >= navString.length) return ParseResult() //base case SUCCESS

        var head = navString[indexInLine]
        var next = if (indexInLine+1 >= navString.length)  ' ' else navString[indexInLine + 1]
        if (head.isOpeningSymbol()) {
            if (next.isClosingSymbol()) {
                return if (head.isCorrectChunkWith(next.getClosingChunk())) {  //Simple match
                    indexInLine += 2
                    parse()
                } else { //Simple mismatch
                    val result = next.getClosingChunk()
                    result.outcome = Outcome.INVALID
                    result
                }
            } else { //embedded
                indexInLine += 1
                val result = parse()
                if (result.outcome != Outcome.SUCCESS) return result
                return if (!head.isCorrectChunkWith(result)) {
                    result.outcome = Outcome.INVALID
                    result
                } else {
                    parse()
                }
            }
        } else {
            indexInLine += 1            // return with closing
            return head.getClosingChunk()
        }
    }

    private fun Char.getClosingChunk(): ParseResult =
        setOf(AngelBracket(), CurlyBracket(), SquareBracket(), RoundBracket()).first { it.closingChunkSymbol == this }
    private fun Char.isOpeningSymbol(): Boolean = symbols.any { it.openingChunkSymbol == this }
    private fun Char.isClosingSymbol(): Boolean = symbols.any { it.closingChunkSymbol == this }
    private fun Char.isCorrectChunkWith(nextChar:ParseResult):Boolean = this == nextChar.openingChunkSymbol
}