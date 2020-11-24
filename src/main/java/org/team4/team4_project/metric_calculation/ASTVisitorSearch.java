package org.team4.team4_project.metric_calculation;

import java.util.HashMap;

import org.eclipse.jdt.core.dom.*;

public class ASTVisitorSearch extends ASTVisitor{
    public HashMap<String, Integer> names = new HashMap<String, Integer>();
    public HashMap<String, Integer> oprt = new HashMap<String, Integer>();
    CompilationUnit compilation=null;
    //public HashMap<String, Integer> declaration = new HashMap<String, Integer>();
    public int codeLen = 0;
    public int commentLen = 0;
    public int cycloComplexity = 0;


    public boolean visit(Expression node){
        return true;
    }

    public boolean visit(InfixExpression node) {
        if (!this.oprt.containsKey(node.getOperator().toString()))
        {
            this.oprt.put(node.getOperator().toString(), 1);
        }
        else
        {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);
        }

        //For cyclomatic complexity (AND, OR)
        if (node.getOperator().toString().equals("&&"))
            cycloComplexity ++;
        if (node.getOperator().toString().equals("||"))
            cycloComplexity ++;
        return true;
    }
    // Override visit the postfix expressions nodes.
    // if the expression's operator doesn't exist in the operator hashmap, insert it, otherwise, increment the count field.
    public boolean visit(PostfixExpression node) {
        if (!this.oprt.containsKey(node.getOperator().toString()))
        {
            this.oprt.put(node.getOperator().toString(), 1);
        }
        else
        {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);
        }
        return true;
    }
    // Override visit the prefix expressions nodes.
    // if the expression's operator doesn't exist in the operator hashmap, insert it, otherwise, increment the count field.
    public boolean visit(PrefixExpression node) {
        if (!this.oprt.containsKey(node.getOperator().toString()))
        {
            this.oprt.put(node.getOperator().toString(), 1);
        }
        else
        {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);
        }

        return true;
    }
    // Override visit the Assignment statements nodes.
    // if the assignment's operator doesn't exist in the operator hashmap, insert it, otherwise, increment the count field.
    public boolean visit(Assignment node) {
        if (!this.oprt.containsKey(node.getOperator().toString()))
        {
            this.oprt.put(node.getOperator().toString(), 1);
        }
        else
        {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);
        }

        return true;
    }
    // Override visit the Single Variable Declaration nodes.
    // add the "=" operators to the hashmap of operators if the variable is initialized
    public boolean visit(SingleVariableDeclaration node) {
        if(node.getInitializer()!=null)
        {
            if (!this.oprt.containsKey("="))
            {
                this.oprt.put("=", 1);
            }
            else
            {
                this.oprt.put("=", this.oprt.get("=")+1);
            }
        }

        return true;
    }
    // Override visit the Variable Declaration Fragment nodes.
    // add the "=" operators to the hashmap of operators if the variable is initialized
    public boolean visit(VariableDeclarationFragment node) {

        if(node.getInitializer()!=null)
        {
            if (!this.oprt.containsKey("="))
            {
                this.oprt.put("=", 1);
            }
            else
            {
                this.oprt.put("=", this.oprt.get("=")+1);
            }
        }

        return true;
    }
    // Override visit the SimpleNames nodes.
    // if the SimpleName doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(SimpleName node) {

        if (!this.names.containsKey(node.getIdentifier()))
        {
            this.names.put(node.getIdentifier(),1);
        }
        else
        {
            this.names.put(node.getIdentifier(), this.names.get(node.getIdentifier())+1);
        }
        return true;
    }
    // Override visit the null nodes.
    // if the null doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(NullLiteral node) {
        if (!this.names.containsKey("null"))
        {
            this.names.put("null", 1);
        }
        else
        {
            this.names.put("null", this.names.get("null")+1);
        }

        return true;
    }
    // Override visit the string literal nodes.
    // if the string literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(StringLiteral node) {

        if (!this.names.containsKey(node.getLiteralValue()))
        {
            this.names.put(node.getLiteralValue(),1);
        }
        else
        {
            this.names.put(node.getLiteralValue(), this.names.get(node.getLiteralValue())+1);
        }
        return true;
    }
    // Override visit the character literal nodes.
    // if the character literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(CharacterLiteral node) {

        if (!this.names.containsKey(Character.toString(node.charValue())))
        {
            this.names.put(Character.toString(node.charValue()),1);
        }
        else
        {
            this.names.put(Character.toString(node.charValue()), this.names.get(Character.toString(node.charValue()))+1);
        }

        return true;
    }
    // Override visit the boolean literal nodes.
    // if the boolean literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(BooleanLiteral node) {

        if (!this.names.containsKey(Boolean.toString(node.booleanValue())))
        {
            this.names.put(Boolean.toString(node.booleanValue()),1);
        }
        else
        {
            this.names.put(Boolean.toString(node.booleanValue()), this.names.get(Boolean.toString(node.booleanValue()))+1);
        }

        return true;
    }
    // Override visit the Number literal nodes.
    // if the Number literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(NumberLiteral node) {
        if (!this.names.containsKey(node.getToken()))
        {
            this.names.put(node.getToken(),1);
        }
        else
        {
            this.names.put(node.getToken(), this.names.get(node.getToken())+1);
        }

        return true;
    }
    // Override visit the compilationUnit to be able to retrieve the line numbers.
    public boolean visit(CompilationUnit unit)	{
        compilation=unit;
        return true;
    }
    /**
     * visit() for Cyclomatic Complexity
     */
    public boolean visit(IfStatement unit)	{
        cycloComplexity++;
        return true;
    }
    public boolean visit(ForStatement unit)	{
        cycloComplexity++;
        return true;
    }
    public boolean visit(WhileStatement unit)	{
        cycloComplexity++;
        return true;
    }
    public boolean visit(AssertStatement unit)	{
        cycloComplexity++;
        return true;
    }
}
