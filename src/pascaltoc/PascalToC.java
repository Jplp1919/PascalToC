/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pascaltoc;

import pascaltoc.lexer.Lexer;
import pascaltoc.parser.Parser;

public class PascalToC {


    public static void main(String[] args) {
       Lexer lex = new Lexer();
        //lex.scan("22aa :=33<>");
        Parser pedro = new Parser();
        pedro.parse("program butts; uses libone, libtwo; var varname : string;  2.9;");
        //lex.scan("program butts; var varname : vartype;  2.9;");
    }
    
}
