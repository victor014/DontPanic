package sample.Controllers;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Configs {
    public static final String UserName="";
    public static final String[] KEYWORDS = new String[] {
            "objeto","escenario","si","entonces","durante",
            "hacer","mientras","crear","agregar","importar",
            "accion","nuevo","ejecutar","controlar","declarar",
            "valor","plantilla","!!","entero","decimal","flotante",
            "texto","hola","habla","conoceme por","por favor haz lo siguiente","gracias"
    };

    public static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    public static final String PAREN_PATTERN = "\\(|\\)";
    public static final String BRACE_PATTERN = "\\{|\\}";
    public static final String BRACKET_PATTERN = "\\[|\\]";
    public static final String SEMICOLON_PATTERN = "\\;";
    public static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\""; //comentario simple
    public static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";  //comentario multiple

    public static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public static final String sampleCode = String.join("\n", new String[] {
            "hola, habla Victor conoceme por 12345, por favor haz lo siguiente {",
            "crear escenario SistemaSolar(1050,2450,3450);",
            "crear objeto Tierra;",
            "crear objeto Marte;",
            "crear objeto Jupiter;",
            "Tierra.accion(9,1450);",
            "Marte.accion(4,1000);",
            "Jupiter.accion(21,7450);",
            "//SentenciaIF",
            "//accionesLogicas",
            "//comentariosSinEspaciosPorElMomento",
            "}",
            //"SistemaSolar.agregar(340,560,70) para Tierra;",
            //"SistemaSolar.agregar(234,89,987) para Marte;",
            //"SistemaSolar.agregar(120,750,450) para Jupiter;",
            "} gracias;"

        //  "package com.example;",
          //  "",
          //  "import java.util.*;",
          //  "",
          //  "public class Foo extends Bar implements Baz {",
          //  "",
          //  "    /*",
          //  "     * multi-line comment",
          //  "     */",
          //  "    public static void main(String[] args) {",
          //  "        // single-line comment",
          //  "        for(String arg: args) {",
          //  "            if(arg.length() != 0)",
          //  "                System.out.println(arg);",
          //  "            else",
          //  "                System.err.println(\"Warning: empty string as argument\");",
          //  "        }",
          //  "    }",
          //  "",
          //  "}"
    });

    public  static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public static String[] EXPRESIONES={
            "(hola, habla )\\w+( conoceme por )\\w+(, por favor haz lo siguiente)(\\s[{])",
            "(crear escenario )\\w+[(]([0-9]+),([0-9]+),([0-9]+)[)];",
            "(crear objeto)(\\s\\w+);",
            "(\\w+)\\.(accion)[(][0-9]+,[0-9]+[)];",
            //"(\\w+)\\.(agregar)[(]([0-9]+,[0-9]+,[0-9]+[)] para (\\w+);", //arreglar
            //"(if) [(]\\w+(>)\\w+[)] {",
            "[}]",
            "\\/\\/(\\w+)",
            "[}]( gracias);"
    };

}

