/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.syntaxhighlighting.lexer;

import java.util.*;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class SJLanguageHierarchy extends LanguageHierarchy<SJTokenId> {

    private static List<SJTokenId> tokens;
    private static Map<Integer, SJTokenId> idToToken;

    private static void init() {
        tokens = Arrays.<SJTokenId>asList(new SJTokenId[]{
            new SJTokenId("EOF", "whitespace", 0),
            new SJTokenId("WHITESPACE", "whitespace", 1),
            
            new SJTokenId("SINGLE_LINE_COMMENT", "comment", 2),
            
            new SJTokenId("ASPECT", "keyword", 3),
            new SJTokenId("AFTER", "keyword", 4),
            new SJTokenId("AROUND", "keyword", 5),
            new SJTokenId("BEFORE", "keyword", 6),
            new SJTokenId("POINTCUT", "keyword", 7),
            new SJTokenId("EXECUTION", "keyword", 8),
            new SJTokenId("RETURNING", "keyword", 9),
            new SJTokenId("PRIVILEGED", "keyword", 10),
            new SJTokenId("PERTARGET", "keyword", 11),
            new SJTokenId("PERTHIS", "keyword", 12),
            new SJTokenId("PERCFLOW", "keyword", 13),
            new SJTokenId("PERCFLOWBELOW", "keyword", 14),
            new SJTokenId("PERTYPEWITHIN", "keyword", 15),
            new SJTokenId("ISSINGLETON", "keyword", 16),
            new SJTokenId("THROWING", "keyword", 17),
            new SJTokenId("PROCEED", "keyword", 18),
            new SJTokenId("THISJOINPOINT", "keyword", 19),
            new SJTokenId("THISJOINPOINTSTATICPART", "keyword", 20),
            new SJTokenId("THISENCLOSINGJOINPOINTSTATICPART", "keyword", 21),
            new SJTokenId("DECLARE", "keyword", 22),
            new SJTokenId("PARENTS", "keyword", 23),
            new SJTokenId("WARNING", "keyword", 24),
            new SJTokenId("ERROR", "keyword", 25),
            new SJTokenId("SOFT", "keyword", 26),
            new SJTokenId("PRECEDENCE", "keyword", 27),
            new SJTokenId("DESIGNATORANNTYPE", "keyword", 28),
            new SJTokenId("DESIGNATORANNMETHOD", "keyword", 29),
            new SJTokenId("DESIGNATORANNCONSTRUCTOR", "keyword", 30),
            new SJTokenId("DESIGNATORANNFIELD", "keyword", 31),
            new SJTokenId("INITIALIZATION", "keyword", 32),
            new SJTokenId("PREINITIALIZATION", "keyword", 33),
            new SJTokenId("STATICINITIALIZATION", "keyword", 34),
            new SJTokenId("GET", "keyword", 35),
            new SJTokenId("SET", "keyword", 36),
            new SJTokenId("HANDLER", "keyword", 37),
            new SJTokenId("ADVICEEXECUTION", "keyword", 38),
            new SJTokenId("WITHIN", "keyword", 39),
            new SJTokenId("WITHINCODE", "keyword", 40),
            new SJTokenId("CFLOW", "keyword", 41),
            new SJTokenId("CFLOWBELOW", "keyword", 42),
            new SJTokenId("TARGET", "keyword", 43),
            new SJTokenId("ARGS", "keyword", 44),
            new SJTokenId("DESIGNATORANNTHIS", "keyword", 45),
            new SJTokenId("DESIGNATORANNTARGET", "keyword", 46),
            new SJTokenId("DESIGNATORANNARGS", "keyword", 47),
            new SJTokenId("DESIGNATORANNWITHIN", "keyword", 48),
            new SJTokenId("DESIGNATORANNANNOTATION", "keyword", 49),
            new SJTokenId("ABSTRACT", "keyword", 50),
            new SJTokenId("ASSERT", "keyword", 51),
            new SJTokenId("BOOLEAN", "keyword", 52),
            new SJTokenId("BREAK", "keyword", 53),
            new SJTokenId("BYTE", "keyword", 54),
            new SJTokenId("CASE", "keyword", 55),
            new SJTokenId("CATCH", "keyword", 56),
            new SJTokenId("CHAR", "keyword", 57),
            new SJTokenId("CLASS", "keyword", 58),
            new SJTokenId("CONST", "keyword", 59),
            new SJTokenId("CONTINUE", "keyword", 60),
            new SJTokenId("DEFLT", "keyword", 61),
            new SJTokenId("DO", "keyword", 62),
            new SJTokenId("DOUBLE", "keyword", 63),
            new SJTokenId("ELSE", "keyword", 64),
            new SJTokenId("ENUM", "keyword", 65),
            new SJTokenId("EXTENDS", "keyword", 66),
            new SJTokenId("FINALLY", "keyword", 67),
            new SJTokenId("FOR", "keyword", 68),
            new SJTokenId("GOTO", "keyword", 69),
            new SJTokenId("IF", "keyword", 70),
            new SJTokenId("IMPLEMENTS", "keyword", 71),
            new SJTokenId("IMPORT", "keyword", 72),
            new SJTokenId("INSTANCEOF", "keyword", 73),
            new SJTokenId("INT", "keyword", 74),
            new SJTokenId("INTERFACE", "keyword", 75),
            new SJTokenId("LONG", "keyword", 76),
            new SJTokenId("NATIVE", "keyword", 77),
            new SJTokenId("NEW", "keyword", 78),
            new SJTokenId("NULL", "keyword", 79),
            new SJTokenId("PACKAGE", "keyword", 80),
            new SJTokenId("PRIVATE", "keyword", 81),
            new SJTokenId("PROTECTED", "keyword", 82),
            new SJTokenId("PUBLIC", "keyword", 83),
            new SJTokenId("RETURN", "keyword", 84),
            new SJTokenId("SHORT", "keyword", 85),
            new SJTokenId("STATIC", "keyword", 86),
            new SJTokenId("STRICTFP", "keyword", 87),
            new SJTokenId("SUPER", "keyword", 88),
            new SJTokenId("SWITCH", "keyword", 89),
            new SJTokenId("SYNCHRONIZED", "keyword", 90),
            new SJTokenId("THIS", "keyword", 91),
            new SJTokenId("THROW", "keyword", 92),
            new SJTokenId("THROWS", "keyword", 93),
            new SJTokenId("TRANSIENT", "keyword", 94),
            new SJTokenId("TRUE", "keyword", 95),
            new SJTokenId("TRY", "keyword", 96),
            new SJTokenId("VOID", "keyword", 97),
            new SJTokenId("VOLATILE", "keyword", 98),
            new SJTokenId("WHILE", "keyword", 99),
            
            new SJTokenId("INTEGER_LITERAL", "literal", 100),
            new SJTokenId("DECIMAL_LITERAL", "literal", 101),
            new SJTokenId("HEX_LITERAL", "literal", 102),
            new SJTokenId("OCTAL_LITERAL", "literal", 103),
            new SJTokenId("FLOATING_POINT_LITERAL", "literal", 104),
            new SJTokenId("DECIMAL_FLOATING_POINT_LITERAL", "literal", 105),
            new SJTokenId("DECIMAL_EXPONENT", "number", 106),
            new SJTokenId("HEXADECIMAL_FLOATING_POINT_LITERAL", "literal", 107),
            new SJTokenId("HEXADECIMAL_EXPONENT", "number", 108),
            new SJTokenId("CHARACTER_LITERAL", "literal", 109),
            new SJTokenId("STRING_LITERAL", "literal", 110),
            new SJTokenId("IDENTIFIER", "identifier", 111),
            new SJTokenId("LETTER", "literal", 112),
            new SJTokenId("PART_LETTER", "literal", 113),
            
            new SJTokenId("LPAREN", "operator", 114),
            new SJTokenId("RPAREN", "operator", 115),
            new SJTokenId("LBRACE", "operator", 116),
            new SJTokenId("RBRACE", "operator", 117),
            new SJTokenId("LBRACKET", "operator", 118),
            new SJTokenId("RBRACKET", "operator", 119),
            new SJTokenId("SEMICOLON", "operator", 120),
            new SJTokenId("COMMA", "operator", 121),
            new SJTokenId("DOT", "operator", 122),
            new SJTokenId("AT", "operator", 123),
            new SJTokenId("ASSIGN", "operator", 124),
            new SJTokenId("LT", "operator", 125),
            new SJTokenId("GT", "operator", 126),
            new SJTokenId("BANG", "operator", 127),
            new SJTokenId("TILDE", "operator", 128),
            new SJTokenId("HOOK", "operator", 129),
            new SJTokenId("COLON", "operator", 130),
            new SJTokenId("EQ", "operator", 131),
            new SJTokenId("LE", "operator", 132),
            new SJTokenId("GE", "operator", 133),
            new SJTokenId("NE", "operator", 134),
            new SJTokenId("SC_OR", "operator", 135),
            new SJTokenId("SC_AND", "operator", 136),
            new SJTokenId("INCR", "operator", 137),
            new SJTokenId("DECR", "operator", 138),
            new SJTokenId("PLUS", "operator", 139),
            new SJTokenId("MINUS", "operator", 140),
            new SJTokenId("STAR", "operator", 141),
            new SJTokenId("SLASH", "operator", 142),
            new SJTokenId("BIT_AND", "operator", 143),
            new SJTokenId("BIT_OR", "operator", 144),
            new SJTokenId("XOR", "operator", 145),
            new SJTokenId("REM", "operator", 146),
            new SJTokenId("LSHIFT", "operator", 147),
            new SJTokenId("RSHIFT", "operator", 148),
            new SJTokenId("LUNSIGNEDSHIFT", "operator", 149),
            new SJTokenId("RUNSIGNEDSHIFT", "operator", 150),
            new SJTokenId("PLUSASSIGN", "operator", 151),
            new SJTokenId("MINUSASSIGN", "operator", 152),
            new SJTokenId("STARASSIGN", "operator", 153),
            new SJTokenId("SLASHASSIGN", "operator", 154),
            new SJTokenId("ANDASSIGN", "operator", 155),
            new SJTokenId("ORASSIGN", "operator", 156),
            new SJTokenId("XORASSIGN", "operator", 157),
            new SJTokenId("REMASSIGN", "operator", 158),
            new SJTokenId("LSHIFTASSIGN", "operator", 159),
            new SJTokenId("RSHIFTASSIGN", "operator", 160),
            new SJTokenId("RSIGNEDSHIFTASSIGN", "operator", 161),
            new SJTokenId("RUNSIGNEDSHIFTASSIGN", "operator", 162),
            new SJTokenId("ELLIPSIS", "operator", 163),
            
            //new SJTokenId("FORMAL_COMMENT", "comment", 166),
            new SJTokenId("MULTI_LINE_COMMENT", "comment", 165)
                
            
        });
        idToToken = new HashMap<Integer, SJTokenId>();
        for (SJTokenId token : tokens) {
            idToToken.put(token.ordinal(), token);
        }
    }

    static synchronized SJTokenId getToken(int id) {
        if (idToToken == null) {
            init();
        }
        return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<SJTokenId> createTokenIds() {
        if (tokens == null) {
            init();
        }
        return tokens;
    }

    @Override
    protected synchronized Lexer<SJTokenId> createLexer(LexerRestartInfo<SJTokenId> info) {
        return new SJLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/x-aj";
    }

}