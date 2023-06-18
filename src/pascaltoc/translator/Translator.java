package pascaltoc.translator;

import java.util.Hashtable;
import pascaltoc.lexer.Token;


public class Translator {
    Token tokenBucket;
    
    public void translate( Hashtable<Integer, Token> tokens){
        StringBuilder stb = new StringBuilder();
        
        for (int i =1; i<= tokens.size(); i++){
          tokenBucket = tokens.get(i);
          if(tokenBucket.getType().equals("Program")){
           stb.append("/* program ");
           i++;
           tokenBucket = tokens.get(i);
           stb.append(tokenBucket.getContent());
           stb.append(" */");
          } else{
              stb.append(tokenBucket.getContent());
              stb.append(" ");  
          }
           
        }
        
        System.out.println(stb.toString());
    }
}
