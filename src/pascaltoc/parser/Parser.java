package pascaltoc.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import pascaltoc.lexer.Lexer;
import pascaltoc.lexer.Token;
import pascaltoc.lexer.TokenType;
import pascaltoc.translator.Translator;

public class Parser {

    private Hashtable<Integer, Token> tokenTable = new Hashtable<>();
    int position = 1;
    Token tokenBucket;
    Hashtable<Integer, Token> tokens;
    String programName;
    ArrayList<String> libraries = new ArrayList<>();
    ArrayList<Var> vars = new ArrayList<>();
    ArrayList<Function> functions = new ArrayList<>();
    StringBuilder stb = new StringBuilder();
    int varPosition = 1;

    public void programStart() throws IncorrectGrammarException {
        if (!"Program".equals(tokenBucket.getType())) {
            throw new IncorrectGrammarException("PROGRAM WAS EXPECTED");
        } else {
            stb.append("/* program ");
            position++;
            tokenBucket = tokens.get(position);

            if (tokenBucket.getType().equals("String")) {
                programName = tokenBucket.getContent();
                programName = programName + " */";
                stb.append(programName);
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
            position++;
            tokenBucket = tokens.get(position);
            if (tokenBucket.getType().equals("String")) {
                position--;
                tokenBucket = tokens.get(position);

                this.varMaker();
                position++;
                tokenBucket = tokens.get(position);
            } else if (tokenBucket.getType().equals("Begin")) {
                //statement
                // yeah i know it is not very descritive, but i know what it means
            } else if (tokenBucket.getType().equals("Function")) {
                this.functionMaker();
            }
        }
        tokenBucket = tokens.get(position);

        if ("Function".equals(tokenBucket.getType())) {

            //while (!tokenBucket.getType().equals("EndPoint")) {
            this.functionMaker();

            //
        }

    }

    public void functionParamName(Function f) throws IncorrectGrammarException {
        String paramNames = "";
        Var v = new Var();
        if (tokenBucket.getType().equals("String")) {

            while (true) {

                if (tokenBucket.getType().equals("Comma")) {

                    position++;
                    tokenBucket = tokens.get(position);
                    if (!tokenBucket.getType().equals("String")) {
                        throw new IncorrectGrammarException("EXPECTED A NAME, FOUND: " + tokenBucket.getContent());
                    }

                } else if (tokenBucket.getType().equals("TwoPoints")) {
                    f.addName(paramNames);

                    paramNames = "";
                    position++;
                    tokenBucket = tokens.get(position);

                    this.functionParamType(f);

                    position++;
                    tokenBucket = tokens.get(position);

                } else if (tokenBucket.getType().equals("EndPoint")) {

                    position++;
                    tokenBucket = tokens.get(position);

                } else if (tokenBucket.getType().equals("CloseParentheses")) {

                    break;

                } else if (tokenBucket.getType().equals("String")) {
                    paramNames = paramNames + tokenBucket.getContent() + " ";

                    position++;
                    tokenBucket = tokens.get(position);

                } else {
                    throw new IncorrectGrammarException("EXPECTED COMMA, FOUND: " + tokenBucket.getContent());
                }
            }

            position++;
            tokenBucket = tokens.get(position);

            if (tokenBucket.getType().equals("TwoPoints")) {

                position++;
                tokenBucket = tokens.get(position);
                this.functionType(f);

            } else {
                throw new IncorrectGrammarException("EXPECTED TWO POINTS, FOUND: " + tokenBucket.getContent());
            }
        }
    }

    public void functionParamType(Function f) throws IncorrectGrammarException {

        if (tokenBucket.getType().equals("String") || tokenBucket.getType().equals("StringType")) {
            f.addType(varType.String);
        } else if (tokenBucket.getType().equals("Integer")) {
            f.addType(varType.Integer);
        } else if (tokenBucket.getType().equals("Real")) {

            f.addType(varType.Real);
        } else if (tokenBucket.getType().equals("Character")) {
            f.addType(varType.Character);
        } else if (tokenBucket.getType().equals("Boolean")) {
            f.addType(varType.Boolean);
        } else {
            throw new IncorrectGrammarException("TYPE NOT FOUND");
        }
    }

    public void functionType(Function f) throws IncorrectGrammarException {
        if (tokenBucket.getType().equals("StringType")) {
            f.setReturnType(varType.String);
        } else if (tokenBucket.getType().equals("Integer")) {
            f.setReturnType(varType.Integer);
        } else if (tokenBucket.getType().equals("Real")) {
            f.setReturnType(varType.Real);
        } else if (tokenBucket.getType().equals("Character")) {
            f.setReturnType(varType.Character);
        } else if (tokenBucket.getType().equals("Boolean")) {
            f.setReturnType(varType.Boolean);
        } else {
            throw new IncorrectGrammarException("TYPE NOT FOUND");
        }
    }

    public void functionMaker() throws IncorrectGrammarException {
        String name;
        Function f = new Function();
        position++;
        tokenBucket = tokens.get(position);

        if (tokenBucket.getType().equals("String")) {
            name = tokenBucket.getContent();
            f.setFunctionName(name);

            position++;
            tokenBucket = tokens.get(position);

            if (tokenBucket.getType().equals("OpenParentheses")) {

                position++;
                tokenBucket = tokens.get(position);

                functionParamName(f);
                functions.add(f);

            } else {
                throw new IncorrectGrammarException("EXPECTED (");
            }
        } else {
            throw new IncorrectGrammarException("FUNCTION NAME");
        }
        position++;
        tokenBucket = tokens.get(position);

        if (tokenBucket.getType().equals("Begin")) {
            position++;
            tokenBucket = tokens.get(position);
            while (!tokenBucket.getType().equals("End")) {
                statementMaker();
            }
        }
    }

    public void usesMaker() throws IncorrectGrammarException {
        String name;

        stb.append("\n #include ");
        position++;
        tokenBucket = tokens.get(position);

        if (tokenBucket.getType().equals("String") || tokenBucket.getType().equals("Input") || tokenBucket.getType().equals("Output")) {

            name = tokenBucket.getContent();

            libraries.add(name);
            stb.append(name);
            //stb.append(System.getProperty("line.separator"));
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
                    v.setContent(tokenBucket.getContent());
                    v.setType(varType.Real);
                } else if (tokenBucket.getType().equals("Integer")) {
                    v.setType(varType.Integer);
                } else if (tokenBucket.getType().equals("Number")) {
                    v.setType(varType.Integer);
                    v.setContent(tokenBucket.getContent());
                } else if (tokenBucket.getType().equals("Character")) {
                    v.setType(varType.Character);
                } else if (tokenBucket.getType().equals("String")) {
                    v.setContent(tokenBucket.getContent());
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

    public void statementMaker() throws IncorrectGrammarException {

        if (tokenBucket.getType().equals("While")) {

            stb.append("\n");
            stb.append(tokenBucket.getContent());
            stb.append(" (");
            position++;
            tokenBucket = tokens.get(position);
            if (!tokenBucket.getType().equals("(")) {
                while (!tokenBucket.getType().equals("Do")) {
                    stb.append(tokenBucket.getContent());
                    position++;
                    tokenBucket = tokens.get(position);
                    //System.out.println(tokenBucket.getContent());
                    //System.out.println(tokenBucket.getType());
                }
                stb.append(" ) {");
                stb.append("\n");
                position++;
                tokenBucket = tokens.get(position);

            } else {
                tokenBucket = tokens.get(position);
                stb.append(tokenBucket.getContent());

            }

            if (tokenBucket.getType().equals("Begin")) {
                while (!tokenBucket.getType().equals("End")) {
                    position++;
                    tokenBucket = tokens.get(position);

                    this.statementMaker();

                    if (tokenBucket.getType().equals("EndPoint")) {

                        stb.append(tokenBucket.getContent());
                        stb.append("\n");

                    } else if (tokenBucket.getType().equals("While")) {
                        this.statementMaker();
                    } else {

                        stb.append(tokenBucket.getContent());
                        //System.out.println(tokenBucket.getContent());
                    }

                }

            }

        } else if (tokenBucket.getType().equals("If")) {
            stb.append('\n');
            stb.append(tokenBucket.getContent());
            position++;
            tokenBucket = tokens.get(position);
            stb.append(" (");
            if (tokenBucket.getType().equals("OpenParentheses")) {
                // position++;
                // tokenBucket = tokens.get(position);
                while (!tokenBucket.getType().equals("CloseParentheses")) {
                    position++;
                    tokenBucket = tokens.get(position);

                    stb.append(tokenBucket.getContent());
                    stb.append(" ");

                }

                stb.append(" ");
            } else {

            }

        } else if (tokenBucket.getType().equals("EndPoint")) {
            //stb.append("\n");

        } else if (tokenBucket.getType().equals("Begin")) {
            stb.append("\n ");
             stb.append(tokenBucket.getContent());
            //position++;
            //tokenBucket = tokens.get(position);
            // System.out.println(tokenBucket.getContent());

            while (!tokenBucket.getType().equals("End")) {
                position++;
                tokenBucket = tokens.get(position);

                this.statementMaker();

                if (tokenBucket.getType().equals("EndPoint")) {

                    stb.append(tokenBucket.getContent());
                    stb.append("\n");

                } else if (tokenBucket.getType().equals("While")) {
                    this.statementMaker();
                } else {

                    stb.append(tokenBucket.getContent());
                    //System.out.println(tokenBucket.getContent());
                }

            }
        } else if (tokenBucket.getType().equals("String")) {
            stb.append(" ");
            stb.append(tokenBucket.getContent());
            position++;
            tokenBucket = tokens.get(position);
        } else {

            stb.append(" ");
             //stb.append(tokenBucket.getContent());
        }

    }

    public void parse(String code) throws IncorrectGrammarException {

        Lexer lexi = new Lexer();
        tokens = lexi.scan(code);

        tokenBucket = tokens.get(position);

        programStart();
        usesVarFunction();
        // System.out.println(tokenBucket.getContent());

        for (int i = 0; i < vars.size(); i++) {
            stb.append('\n');
            Var v = new Var();
            v = vars.get(i);
            String append = v.getType() + " " + v.getName();
            if (v.getContent() != null) {
                append = append + " " + v.getContent() + ";";
            } else {
                append = append + ";";
            }

            stb.append(append);

        }

        for (int i = 0; i < functions.size(); i++) {
            String functionContent = "";

            stb.append('\n');
            Function f = new Function();
            f = functions.get(i);
            String append = f.returnType + " " + f.getFunctionName();
            ArrayList<String> list = new ArrayList<>();
            ArrayList<varType> typeList = new ArrayList<>();
            list = f.getNames();
            typeList = f.getParametersTypes();
            if (list.isEmpty()) {
                append = append + " (){";
            } else {
                for (int j = 0; j < list.size(); j++) {
                    String st = typeList.get(j).toString();
                    String s = list.get(j);
                    String[] splited = s.split("\\s+");

                    s = "";
                    for (int k = 0; k < splited.length; k++) {
                        s = s + " " + st + " " + splited[k];

                        if (j + 1 != list.size()) {
                            s = s + ",";
                        }
                    }
                    functionContent = functionContent + s;
                }
                append = append + " (" + functionContent + ")" + " {\n";
                //System.out.println(list.get(i));
            }
            stb.append(append);
        }

        while (!tokenBucket.getType().equals("End")) {
            statementMaker();
            position++;
            tokenBucket = tokens.get(position);
        }

        System.out.println(stb);
        //System.out.println(tokenBucket.getContent());
    }

    public String getStb() {
        return stb.toString();
    }

}
