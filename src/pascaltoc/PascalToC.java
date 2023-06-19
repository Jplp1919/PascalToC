/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pascaltoc;

import pascaltoc.lexer.Lexer;
import pascaltoc.parser.Parser;
import pascaltoc.translator.Translator;

public class PascalToC {

    public static void main(String[] args) {
        Lexer lex = new Lexer();

        //lex.scan("22aa :=33<>");
        Parser pedro = new Parser();
        /* pedro.parse("program pname; uses libone, libtwo; var varname : string; nametoo :  2.9; "
                + "function functionname (argumentname, argumentsecond, thirdone3: real; second: boolean ) : Integer;");
         */
 /* String code = "program number;\n"
                + " (* add line numbers to each line of an input file *)\n"
                +"uses input, output;"
                + "var\n"
                + "	n: 0.999999;\n"
                + "	c: char;\n"
                + "begin\n"
                + "	n := 0;\n"
                + "\n"
                + "	while not eof(input)\n"
                + "	   do begin\n"
                + "			n := n + 1;\n"
                +"if (testing this) then "
                + "s1 "
                + "end;"
                + "			write (n:6,' ');\n"
                + "\n"
                + "			while not eoln (input)\n"
                + "			   do begin\n"
                + "				read (c);\n"
                + "				write (c);\n"
                + "			      end;\n"
                + "			writeln;\n"
                + "			readln;\n"
                + "		end;\n"
               
                +""
                + "end.";*/

        String code = "program whileLoop;\n"
                + "var\n"
                + "   a: integer;\n"
                + "\n"
                + "begin\n"
                + "   a := 10;\n"
                + "   while a < 20  do\n"
                + "   \n"
                + "   begin\n"
                + "      writeln('value of a: ', a);\n"
                + "      a := a + 1;\n"
                + "   end;\n"
                + "end.";
        CodePrep prep = new CodePrep();
        String goodcode = prep.lexer(code);
        // System.out.println(goodcode);
        pedro.parse(goodcode);
        //lex.scan("program butts; var varname : vartype;  2.9;");
    }

}
