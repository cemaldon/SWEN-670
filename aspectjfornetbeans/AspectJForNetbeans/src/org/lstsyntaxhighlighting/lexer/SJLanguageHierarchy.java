/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lstsyntaxhighlighting.lexer;

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
            new SJTokenId("whitespace", "whitespace", 1),
            new SJTokenId("comment", "comment", 2),
            new SJTokenId("number", "literal", 3),
            new SJTokenId("identifier", "identifier", 4),
            new SJTokenId("separator", "separator", 5),
            new SJTokenId("keyword", "keyword", 6),
            new SJTokenId("operator", "operator", 7)
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