package pascaltoc.lexer;


public class Token {
    TokenType Type;
    
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Token(TokenType t, String s) {
        this.Type = t;
        this.content = s;
    }
    
    public String getType() {
        return Type.toString();
    }

    public void setType(TokenType Type) {
        this.Type = Type;
    }
    
    
}
