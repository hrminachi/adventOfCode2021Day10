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

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParserTest {

    @Test
    fun parseLine_emptyLine_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("")
        //Assert
        assertEquals(Outcome.SUCCESS, actualResult.outcome)
    }

    @Test
    fun parseLine_InvalidCurlyBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("(}")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(1197, actualResult.score)
    }

    @Test
    fun parseLine_InvalidAngelBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("(>")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(25137, actualResult.score)
    }

    @Test
    fun parseLine_SuccessAngelBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("<>")
        //Assert
        assertEquals(Outcome.SUCCESS, actualResult.outcome)
    }

    @Test
    fun parseLine_SuccessRoundBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("()")
        //Assert
        assertEquals(Outcome.SUCCESS, actualResult.outcome)
    }

    @Test
    fun parseLine_SuccessDoubleRoundBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("(())")
        //Assert
        assertEquals(Outcome.SUCCESS, actualResult.outcome)
    }

    @Test
    fun parseLine_InvalidDoubleRoundAngelBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("(()>")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(25137, actualResult.score)
    }

    @Test
    fun parseLine_InvalidTripleRoundAngelBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("(()()>")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(25137, actualResult.score)
    }

    @Test
    fun parseLine_InvalidTripleEmbeddedRoundAngelBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("((())>")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(25137, actualResult.score)
    }

    @Test
    fun parseLine_SimpleBrackets_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("()[]{}<>")
        //Assert
        assertEquals(Outcome.SUCCESS, actualResult.outcome)
    }

    @Test
    fun parseLine_MixSimpleAndEmbedded_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("([{}])()[{<<>>}]")
        //Assert
        assertEquals(Outcome.SUCCESS, actualResult.outcome)
    }

    @Test
    fun parseLine_Sample1_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("{([(<{}[<>[]}>{[]{[(<()>")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(1197, actualResult.score)
    }

    @Test
    fun parseLine_Sample2_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("[[<[([]))<([[{}[[()]]]")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(3, actualResult.score)
    }

    @Test
    fun parseLine_Sample3_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("[{[{({}]{}}([{[{{{}}([]")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(57, actualResult.score)
    }

    @Test
    fun parseLine_Sample4_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("[<(<(<(<{}))><([]([]()")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(3, actualResult.score)
    }

    @Test
    fun parseLine_Sample5_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseLine("<{([([[(<>()){}]>(<<{{")
        //Assert
        assertEquals(Outcome.INVALID, actualResult.outcome)
        assertEquals(25137, actualResult.score)
    }

    @Test
    fun parseText_TwoLines_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseText("<{([([[(<>()){}]>(<<{{\n" +
                "[<(<(<(<{}))><([]([]()")
        //Assert
        assertEquals(25140, actualResult)
    }

    @Test
    fun parseText_MultiLines_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseText(
                "{([(<{}[<>[]}>{[]{[(<()>\n" +
                        "[[<[([]))<([[{}[[()]]]\n" +
                        "[{[{({}]{}}([{[{{{}}([]\n" +
                        "[<(<(<(<{}))><([]([]()\n" +
                        "<{([([[(<>()){}]>(<<{{")
        //Assert
        assertEquals(26397, actualResult)
    }

    @Test
    fun parseText_MultiLinesExample_SuccessParseResult() {
        //Arrange
        val cut = Parser()
        //Act
        val actualResult = cut.parseText(
            "[({(<(())[]>[[{[]{<()<>>\n" +
                    "[(()[<>])]({[<{<<[]>>(\n" +
                    "{([(<{}[<>[]}>{[]{[(<()>\n" +
                    "(((({<>}<{<{<>}{[]{[]{}\n" +
                    "[[<[([]))<([[{}[[()]]]\n" +
                    "[{[{({}]{}}([{[{{{}}([]\n" +
                    "{<[[]]>}<{[{[{[]{()[[[]\n" +
                    "[<(<(<(<{}))><([]([]()\n" +
                    "<{([([[(<>()){}]>(<<{{\n" +
                    "<{([{{}}[<[[[<>{}]]]>[]]")
        //Assert
        assertEquals(26397, actualResult)
    }
}