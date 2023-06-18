/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pascaltoc.parser;

import java.util.ArrayList;

public class Function {

    ArrayList<String> names = new ArrayList<>();
    varType returnType;
    //ArrayList<Var> parameters = new ArrayList<>();
    ArrayList<varType> parametersTypes = new ArrayList<>();
    String functionName;

    public String getFunctionName() {
        return functionName;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<varType> getParametersTypes() {
        return parametersTypes;
    }

    
    
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }



    public void addType(varType vt){
        this.parametersTypes.add(vt);
    }
    
    public void addName(String s) {
        this.names.add(s);
    }

    public varType getReturnType() {
        return returnType;
    }

    public void setReturnType(varType returnType) {
        this.returnType = returnType;
    }


}
