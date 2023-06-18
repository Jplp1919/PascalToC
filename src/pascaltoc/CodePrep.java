package pascaltoc;

public class CodePrep {

    public String lexer(String text) {
        String newText = "";
        for (int i = 0; i < text.length(); i++) {
           // System.out.println(i + " " + text.charAt(i));
            if (text.charAt(i) == '{') {
                i++;
                while (text.charAt(i) != '}') {
                    i++;
                }
            } else if (text.charAt(i) == '(' && (text.charAt(i + 1) == '*')) {
                 //System.out.println("OY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                i++;
                i++;
                while (text.charAt(i) != '*' && text.charAt(i + 1) != ')') {
                    i++;

                }
                i++;

            } else if (text.charAt(i) == '/') {
                if (text.charAt(i + 1) == '*') {
                    i++;
                    i++;
                    while (text.charAt(i) != '*' && text.charAt(i + 1) != '/') {
                        i++;

                    }
                    i++;
                }
            } else if (text.charAt(i) == ' ') {
             if(text.charAt(i+1) == ' '){
                i++; 
             } else{
               newText = newText + Character.toLowerCase(text.charAt(i));  
             }
            } else {
                newText = newText + Character.toLowerCase(text.charAt(i));
            }
        }
        return newText;
    }
}
