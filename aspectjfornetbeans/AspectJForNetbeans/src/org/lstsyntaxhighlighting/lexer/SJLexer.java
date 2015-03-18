/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lstsyntaxhighlighting.lexer;

import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.lstsyntaxhighlighting.jcclexer.JavaCharStream;
import org.lstsyntaxhighlighting.jcclexer.JavaParserTokenManager;
import org.lstsyntaxhighlighting.jcclexer.Token;

class SJLexer implements Lexer<SJTokenId> {

    private LexerRestartInfo<SJTokenId> info;
    private JavaParserTokenManager javaParserTokenManager;

    SJLexer(LexerRestartInfo<SJTokenId> info) {
        this.info = info;
        JavaCharStream stream = new JavaCharStream(info.input());
        javaParserTokenManager = new JavaParserTokenManager(stream);
    }

    @Override
    public org.netbeans.api.lexer.Token<SJTokenId> nextToken() {
        Token token = javaParserTokenManager.getNextToken();
        if (info.input().readLength() < 1) {
            return null;
        }
        return info.tokenFactory().createToken(SJLanguageHierarchy.getToken(token.kind));
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

}
