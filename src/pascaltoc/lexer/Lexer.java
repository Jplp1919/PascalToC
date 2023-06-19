package pascaltoc.lexer;

import java.util.ArrayList;
import java.util.Hashtable;

public class Lexer {

    private char bucket = ' ';
    int line = 1;
    int position = 0;
    int order = 1;
    private char peek = ' ';
    String code = "";

    //private Hashtable<Integer, Token> tokenHash = new Hashtable<>();
    public void read() throws IndexOutOfBoundsException {
        bucket = Character.toLowerCase(code.charAt(position));
        //System.out.println("In the bucket " + bucket);
        position++;
    }

    public boolean read(char c) throws IndexOutOfBoundsException {
        bucket = Character.toLowerCase(code.charAt(position));
        position++;

        if (bucket == c) {
            return true;
        } else {
            return false;
        }

    }

    public char peek() {
        if (position < code.length()) {
            peek = Character.toLowerCase(code.charAt(position));

        } else {
            peek = ' ';
        }

        return peek;
    }

    public boolean peek(char c) throws IndexOutOfBoundsException {
        // System.out.println(Character.toLowerCase(code.charAt(position + 1)) == c);
        return Character.toLowerCase(code.charAt(position + 1)) == c;
    }

    public Hashtable<Integer, Token> scan(String s) throws IndexOutOfBoundsException {
        code = s;
        String content;
        read();
        Hashtable<Integer, Token> tokenHash = new Hashtable<>();

        while (position < s.length()) {

            for (;; read()) {
                if (bucket == ' ' || bucket == '\t') {
                    //continue;
                } else if (bucket == '\n') {

                    line = line + 1;
                } else {
                    break;
                }
            }

            // System.out.println("In the bucket: " + bucket);
            content = "";

            if (bucket == '+') {
                content = "+";
                Token t = new Token(TokenType.Plus, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == '\'') {
                content = "'";
                Token t = new Token(TokenType.Apostrophe, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == '-') {

                content = "-";
                Token t = new Token(TokenType.Minus, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == '=') {

                //content = "=";
                content = "==";
                Token t = new Token(TokenType.Equal, content);
                tokenHash.put(order, t);
                order++;

                // Token test = tokenHash.get(order - 1);
                //System.out.println(test.getContent());
                // System.out.println(test.getType());
                if (position < s.length()) {
                    read();

                }

            } else if (bucket == '<') {
                if (read('>')) {
                    //content = "<>";
                    content = "!=";
                    Token t = new Token(TokenType.NotEqual, content);
                    tokenHash.put(order, t);
                    order++;
                    if (position < s.length()) {
                        read();
                    }
                } else {
                    content = "<";
                    Token t = new Token(TokenType.Lesser, content);
                    tokenHash.put(order, t);
                    order++;
                }
            } else if (bucket == ':') {
                if (read('=')) {
                    read();
                    //content = ":=";
                    content = "=";
                    Token t = new Token(TokenType.Assignment, content);
                    tokenHash.put(order, t);
                    order++;

                } else {
                    content = ":";
                    Token t = new Token(TokenType.TwoPoints, content);
                    tokenHash.put(order, t);
                    order++;
                    read();
                }

            } else if (bucket == '\n') {
                content = "\n";
                Token t = new Token(TokenType.NewLine, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == ';') {

                content = ";";
                Token t = new Token(TokenType.EndPoint, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == ',') {
                content = ",";
                Token t = new Token(TokenType.Comma, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == '(') {
                content = "(";
                Token t = new Token(TokenType.OpenParentheses, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == ')') {
                content = ")";
                Token t = new Token(TokenType.CloseParentheses, content);
                tokenHash.put(order, t);
                order++;
                read();
            } else if (bucket == '>') {
                content = ">";
                Token t = new Token(TokenType.Greater, content);
                tokenHash.put(order, t);
                order++;
                read();
            }else if (Character.isAlphabetic(bucket)) {
                StringBuilder stb = new StringBuilder();

                stb.append(bucket);

                while (Character.isAlphabetic(bucket) || Character.isDigit(bucket) && position < code.length()) {
                    read();
                    if (Character.isAlphabetic(bucket) || Character.isDigit(bucket) && position < code.length()) {
                        stb.append(bucket);
                    }

                }
                //content.equalsIgnoreCase()

                content = stb.toString();

                if (content.equalsIgnoreCase("program")) {
                    //program 

                    Token t = new Token(TokenType.Program, content);
                    tokenHash.put(order, t);
                    order++;

                } else if (content.equalsIgnoreCase("uses")) {

                    //uses
                    content = "#include";
                    Token t = new Token(TokenType.Uses, content);
                    tokenHash.put(order, t);
                    order++;

                } else if (content.equalsIgnoreCase("function")) {
                    content = "function";
                    Token t = new Token(TokenType.Function, content);
                    tokenHash.put(order, t);
                    order++;

                } else if (content.equalsIgnoreCase("begin")) {
                    //begin
                    content = "{";
                    Token t = new Token(TokenType.Begin, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("end")) {
                    //end
                    content = "}";
                    Token t = new Token(TokenType.End, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("or")) {
                    //or 
                    content = "||";
                    Token t = new Token(TokenType.Or, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("and")) {
                    //and
                    content = "&&";
                    Token t = new Token(TokenType.And, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("not")) {
                    //not
                    content = "!";
                    Token t = new Token(TokenType.Not, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("mod")) {
                    //mod
                    content = "%";
                    Token t = new Token(TokenType.Mod, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("div")) {
                    //div
                    content = "/";
                    Token t = new Token(TokenType.Div, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("case")) {
                    //case
                    content = "switch";
                    Token t = new Token(TokenType.Case, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("integer")) {
                    //integer
                    content = "int";
                    Token t = new Token(TokenType.Integer, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("do")) {
                    content = "\n";
                    Token t = new Token(TokenType.Do, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("if")) {
                    content = "if";
                    Token t = new Token(TokenType.If, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("then")) {
                    content = "{";
                    Token t = new Token(TokenType.Then, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("else")) {
                    content = "else";
                    Token t = new Token(TokenType.Else, content);
                    tokenHash.put(order, t);
                    order++;
                }else if (content.equalsIgnoreCase("real")) {
                    //real
                    content = "float";
                    Token t = new Token(TokenType.Real, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("input")) {
                    //input
                    content = "stdin";
                    Token t = new Token(TokenType.Input, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("output")) {
                    //output
                    content = "stdout";
                    Token t = new Token(TokenType.Output, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("var")) {
                    //var
                    content = "var";
                    Token t = new Token(TokenType.Var, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("while")) {
                    //while
                    content = "while";
                    Token t = new Token(TokenType.While, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("boolean")) {
                    content = "boolean";
                    Token t = new Token(TokenType.Boolean, content);
                    tokenHash.put(order, t);
                    order++;

                } else if (content.equalsIgnoreCase("string")) {
                    content = "string";
                    Token t = new Token(TokenType.StringType, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (content.equalsIgnoreCase("Character") || content.equalsIgnoreCase("Char")) {
                    content = "char";
                    Token t = new Token(TokenType.Character, content);
                    tokenHash.put(order, t);
                    order++;
                } else if (bucket == '\n') {
                    content = "\n";
                    Token t = new Token(TokenType.NewLine, content);
                    tokenHash.put(order, t);
                    order++;
                } else {
                    // string
                    Token t = new Token(TokenType.String, content);
                    tokenHash.put(order, t);
                    order++;

                }

            } else if (Character.isDigit(bucket)) {
                StringBuilder stb = new StringBuilder();

                stb.append(bucket);
                int dotCount = 0;
                while (bucket == '.' || Character.isDigit(bucket) && position < code.length()) {
                    read();

                    if (Character.isDigit(bucket)) {
                        stb.append(bucket);
                    } else if (bucket == '.' && dotCount < 1) {
                        stb.append(bucket);
                        dotCount++;
                    }
                }

                content = stb.toString();

                if (dotCount == 0) {
                    Token t = new Token(TokenType.Number, content);
                    tokenHash.put(order, t);
                    order++;

                } else if (dotCount == 1) {
                    Token t = new Token(TokenType.Real, content);
                    tokenHash.put(order, t);
                    order++;

                }

            }

        }
        return tokenHash;
    }

}
