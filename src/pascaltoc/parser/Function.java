/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pascaltoc.parser;

import java.util.ArrayList;

public class Function {
    ArrayList<String> names = new ArrayList<>();
    varType returnType;
    ArrayList<Var> parameters;

    
    public void addParam(Var v){
        this.parameters.add(v);
    }
    
    public void addName(String s){
        this.names.add(s);
    }
 

    public varType getReturnType() {
        return returnType;
    }

    public void setReturnType(varType returnType) {
        this.returnType = returnType;
    }

    public ArrayList<Var> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Var> parameters) {
        this.parameters = parameters;
    }
    
    
    
}
