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

open class ParseResult(var closingChunkSymbol:Char = ' ',
                       var openingChunkSymbol:Char = ' ',
                       var score:Int = 0,
                       var outcome:Outcome = Outcome.SUCCESS,
                        ) {}

enum class Outcome {SUCCESS, INCOMPLETE, INVALID}


/*
* This class represents closing chunk ')'
*/
class RoundBracket:ParseResult(closingChunkSymbol = ')', openingChunkSymbol = '(', score = 3)

/*
* This class represents closing chunk ']'
*/
class SquareBracket:ParseResult(closingChunkSymbol = ']', openingChunkSymbol = '[', score = 57)

/*
* This class represents closing chunk '}'
*/
class CurlyBracket:ParseResult(closingChunkSymbol = '}', openingChunkSymbol = '{', score = 1197)

/*
* This class represents closing chunk '>'
*/
class AngelBracket:ParseResult(closingChunkSymbol = '>', openingChunkSymbol = '<', score = 25137)