package pascaltoc.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import pascaltoc.lexer.Lexer;
import pascaltoc.lexer.Token;
import pascaltoc.lexer.TokenType;

public class Parser {

    private Hashtable<Integer, Token> tokenTable = new Hashtable<>();
    int position = 1;
    Token tokenBucket;
    Hashtable<Integer, Token> tokens;
    String programName;
    ArrayList<String> libraries = new ArrayList<>();
    ArrayList<Var> vars = new ArrayList<>();

    int varPosition = 1;

    public void programStart() throws IncorrectGrammarException {
        if (!"Program".equals(tokenBucket.getType())) {
            throw new IncorrectGrammarException("PROGRAM WAS EXPECTED");
        } else {
            position++;
            tokenBucket = tokens.get(position);
            if (tokenBucket.getType().equals("String")) {
                programName = tokenBucket.getContent();

                position++;
                tokenBucket = tokens.get(position);

                if (tokenBucket.getType().equals("EndPoint")) {
                    // position++;
                    // tokenBucket = tokens.get(position);

                } else {
                    throw new IncorrectGrammarException("; WAS EXPECTED");
                }
            } else {
                throw new IncorrectGrammarException("PROGRAM NAME WAS EXPECTED");
            }

        }
    }

    public void usesVarFunction() throws IncorrectGrammarException {

        position++;
        tokenBucket = tokens.get(position);

        if ("Uses".equals(tokenBucket.getType())) {
            while (!tokenBucket.getType().equals("EndPoint")) {

                this.usesMaker();
            }

        }

        tokenBucket = tokens.get(position);

        if ("Var".equals(tokenBucket.getType())) {
            while (!tokenBucket.getType().equals("EndPoint")) {

                this.varMaker();
            }

        }
        if ("Function".equals(tokenBucket.getType())) {
            while (!tokenBucket.getType().equals("EndPoint")) {
                this.functionMaker();
            }
        }
    }

    public void functionParamName(Function f){
        if (tokenBucket.getType().equals("Comma")) {
                //next param name 
                position++;
                tokenBucket = tokens.get(position);
                if (tokenBucket.getType().equals("String")) {
                    f.addName(tokenBucket.getContent());
                    position++;
                    tokenBucket = tokens.get(position);
                } else {
                    throw new IncorrectGrammarException("EXPECTED PARAM NAME");
                }
            } else if (tokenBucket.getType().equals("EndPoint")){
            
            }else {
                throw new IncorrectGrammarException("EXPECTED Comma");
            }
    }
    
    public void functionParam(Function f) throws IncorrectGrammarException {
        tokenBucket = tokens.get(position);

        //if : = type
        //if string paramName
        if (tokenBucket.getType().equals("String")) {
            f.addName(tokenBucket.getContent());
            if (tokenBucket.getType().equals("TwoPoints")) {
                position++;
                tokenBucket = tokens.get(position);
                // get type
            } else if (tokenBucket.getType().equals("Comma")) {
                //next param name 
                position++;
                tokenBucket = tokens.get(position);
                if (tokenBucket.getType().equals("String")) {
                    // comma or twopoints
                    f.addName(tokenBucket.getContent());
                    position++;
                    tokenBucket = tokens.get(position);
                } else {
                    throw new IncorrectGrammarException("EXPECTED PARAM NAME");
                }
            } else {
                throw new IncorrectGrammarException("EXPECTED Comma");
            }
        } else {
            throw new IncorrectGrammarException("EXPECTED FIRST PARAM NAME");
        }
    }

    public void functionMaker() throws IncorrectGrammarException {
        String name;
        position++;
        tokenBucket = tokens.get(position);
        if (tokenBucket.getType().equals("String")) {
            name = tokenBucket.getContent();
            position++;
            tokenBucket = tokens.get(position);

            if (tokenBucket.getType().equals("OpenParentheses")) {
                position++;
                tokenBucket = tokens.get(position);

                while (!tokenBucket.getType().equals("TwoPoints")) {
                  
                }
            } else {
                throw new IncorrectGrammarException("EXPECTED (");
            }
        } else {
            throw new IncorrectGrammarException("FUNCTION NAME");
        }

    }

    public void usesMaker() throws IncorrectGrammarException {
        String name;
        position++;
        tokenBucket = tokens.get(position);

        if (tokenBucket.getType().equals("String")) {

            name = tokenBucket.getContent();

            libraries.add(name);
            position++;
            tokenBucket = tokens.get(position);

            if (tokenBucket.getType().equals("Comma")) {

            } else if (tokenBucket.getType().equals("EndPoint")) {
                position++;

            } else {
                throw new IncorrectGrammarException("EXPECTED COMMA");
            }
        } else {
            throw new IncorrectGrammarException("EXPECTED LIBRARY NAME");
        }

    }

    public void varMaker() throws IncorrectGrammarException {
        String name;
        position++;
        tokenBucket = tokens.get(position);

        if (tokenBucket.getType().equals("String")) {

            name = tokenBucket.getContent();
            Var v = new Var();
            v.setName(name);
            position++;
            tokenBucket = tokens.get(position);
            if (tokenBucket.getType().equals("TwoPoints")) {
                position++;
                tokenBucket = tokens.get(position);

                if (tokenBucket.getType().equals("StringType")) {
                    v.setType(varType.String);
                } else if (tokenBucket.getType().equals("Real")) {
                    v.setType(varType.Real);
                } else if (tokenBucket.getType().equals("Integer")) {
                    v.setType(varType.Integer);
                } else if (tokenBucket.getType().equals("Character")) {
                    v.setType(varType.Character);
                } else if (tokenBucket.getType().equals("String")) {
                    v.setType(varType.String);
                } else {
                    throw new IncorrectGrammarException("TYPE NOT FOUND");
                }
                v.setPosition(varPosition);
                varPosition++;

                vars.add(v);

            } else {
                throw new IncorrectGrammarException("WANTED DOT DOT!");

            }
        } else {
            throw new IncorrectGrammarException("EXPECTED A NAME");

        }
        position++;
        tokenBucket = tokens.get(position);

    }

    public void parse(String code) throws IncorrectGrammarException {

        Lexer lexi = new Lexer();
        tokens = lexi.scan(code);
        StringBuilder stb = new StringBuilder();

        /* for (int i = 1; i < tokens.size(); i++) {
            tokenBucket = tokens.get(i);
            //System.out.println(tokenBucket.getContent());
           // System.out.println(tokenBucket.getType());
            System.out.println( tokenBucket.getContent() + " AT --- " +i );
        }*/
        tokenBucket = tokens.get(position);

        programStart();
        usesVarFunction();
    }

}
