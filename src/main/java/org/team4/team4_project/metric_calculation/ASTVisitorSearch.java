package org.team4.team4_project.metric_calculation;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class ASTVisitorSearch extends ASTVisitor {
    public HashMap<String, Integer> names = new HashMap<String, Integer>();
    public HashMap<String, Integer> oprt = new HashMap<String, Integer>();
    CompilationUnit compilation = null;
    //public HashMap<String, Integer> declaration = new HashMap<String, Integer>();
    public int codeLen = 0;
    public int commentLen = 0;
    public int cycloComplexity = 1;
    public int num_method = 0;
    public int num_loop = 0;
    public int num_import = 0;


    public boolean visit(Expression node) {
        return true;
    }

    public boolean visit(InfixExpression node) {
        //System.out.println("InfixExpression");

        if (!this.oprt.containsKey(node.getOperator().toString())) {
            this.oprt.put(node.getOperator().toString(), 1);
        } else {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString()) + 1);
        }

        if (!this.names.containsKey(node.getLeftOperand().toString())) {
            this.names.put(node.getLeftOperand().toString(), 1);
        } else {
            this.names.put(node.getLeftOperand().toString(), this.names.get(node.getLeftOperand().toString()) + 1);
        }

        if (!this.names.containsKey(node.getRightOperand().toString())) {
            this.names.put(node.getRightOperand().toString(), 1);
        } else {
            this.names.put(node.getRightOperand().toString(), this.names.get(node.getRightOperand().toString()) + 1);
        }
        //System.out.println("Infix Left Operand  : " + node.getLeftOperand().toString());
        //System.out.println("Infix Right Operand : " + node.getRightOperand().toString());

        //For cyclomatic complexity (AND, OR)
        if (node.getOperator().toString().equals("&&")) {
            //System.out.println("InfixExpression ANDAND");
            cycloComplexity++;
        }
        if (node.getOperator().toString().equals("||")) {
            //System.out.println("InfixExpression OROR");
            cycloComplexity++;
        }

        return true;
    }

    public boolean visit(PostfixExpression node) {
        if (!this.oprt.containsKey(node.getOperator().toString())) {
            this.oprt.put(node.getOperator().toString(), 1);
        } else {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString()) + 1);
        }
        //System.out.println("PostfixExpression");

        if (!this.names.containsKey(node.getOperand().toString())) {
            this.names.put(node.getOperand().toString(), 1);
        } else {
            this.names.put(node.getOperand().toString(), this.names.get(node.getOperand().toString()) + 1);
        }
        //System.out.println("Postfix Operand  : " + node.getOperand().toString());

        return true;
    }

    public boolean visit(PrefixExpression node) {
        //System.out.println("PrefixExpression");

        if (!this.oprt.containsKey(node.getOperator().toString())) {
            this.oprt.put(node.getOperator().toString(), 1);
        } else {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString()) + 1);
        }

        if (!this.names.containsKey(node.getOperand().toString())) {
            this.names.put(node.getOperand().toString(), 1);
        } else {
            this.names.put(node.getOperand().toString(), this.names.get(node.getOperand().toString()) + 1);
        }
        //System.out.println("Prefix Operand  : " + node.getOperand().toString());

        return true;
    }

    public boolean visit(Assignment node) {
        if (!this.oprt.containsKey(node.getOperator().toString())) {
            this.oprt.put(node.getOperator().toString(), 1);
        } else {
            this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString()) + 1);
        }

        if (!this.names.containsKey(node.getLeftHandSide().toString())) {
            this.names.put(node.getLeftHandSide().toString(), 1);
        } else {
            this.names.put(node.getLeftHandSide().toString(), this.names.get(node.getLeftHandSide().toString()) + 1);
        }
        if (!this.names.containsKey(node.getRightHandSide().toString())) {
            this.names.put(node.getRightHandSide().toString(), 1);
        } else {
            this.names.put(node.getRightHandSide().toString(), this.names.get(node.getRightHandSide().toString()) + 1);
        }
        //System.out.println(node.getLeftHandSide().toString());
        //out.println(node.getRightHandSide().toString());

        //System.out.println("Assignment");
        return true;
    }

    public boolean visit(SingleVariableDeclaration node) {
        if (node.getInitializer() != null) {
            if (!this.oprt.containsKey("=")) {
                this.oprt.put("=", 1);
            } else {
                this.oprt.put("=", this.oprt.get("=") + 1);
            }

            List<String> templist = Arrays.asList(node.toString().split("="));
            if (!this.names.containsKey(templist.get(0))) {
                this.names.put(templist.get(0), 1);
            } else {
                this.names.put(templist.get(0), this.names.get(templist.get(0)) + 1);
            }
            if (!this.names.containsKey(templist.get(1))) {
                this.names.put(templist.get(1), 1);
            } else {
                this.names.put(templist.get(1), this.names.get(templist.get(1)) + 1);
            }
        }
        //System.out.println("SingleVariableDeclaration");
        return true;
    }

    public boolean visit(VariableDeclarationFragment node) {
        if (node.getInitializer() != null) {
            if (!this.oprt.containsKey("=")) {
                this.oprt.put("=", 1);
            } else {
                this.oprt.put("=", this.oprt.get("=") + 1);
            }

            List<String> templist = Arrays.asList(node.toString().split("="));
            if (!this.names.containsKey(templist.get(0))) {
                this.names.put(templist.get(0), 1);
            } else {
                this.names.put(templist.get(0), this.names.get(templist.get(0)) + 1);
            }
            if (!this.names.containsKey(templist.get(1))) {
                this.names.put(templist.get(1), 1);
            } else {
                this.names.put(templist.get(1), this.names.get(templist.get(1)) + 1);
            }
        }
        //System.out.println("VariableDeclarationFragment");
        return true;
    }

    public boolean visit(SimpleName node) {
        /*
        if (!this.names.containsKey(node.getIdentifier())) {
            this.names.put(node.getIdentifier(), 1);
        } else {
            this.names.put(node.getIdentifier(), this.names.get(node.getIdentifier()) + 1);
        }
         */
        if (node.getIdentifier().equals("assert") || node.getIdentifier().equals("List") || node.getIdentifier().equals("Set") || node.getIdentifier().equals("Map")) {
            cycloComplexity++;
        }
        //System.out.println("SimpleName");
        //System.out.println(node.getIdentifier());
        return true;
    }
    /*
    public boolean visit(NullLiteral node) {
        if (!this.names.containsKey("null")) {
            this.names.put("null", 1);
        } else {
            this.names.put("null", this.names.get("null") + 1);
        }
        System.out.println("NullLiteral");
        return true;
    }
    public boolean visit(StringLiteral node) {
        if (!this.names.containsKey(node.getLiteralValue())) {
            this.names.put(node.getLiteralValue(), 1);
        } else {
            this.names.put(node.getLiteralValue(), this.names.get(node.getLiteralValue()) + 1);
        }
        System.out.println("StringLiteral");
        return true;
    }
    // Override visit the character literal nodes.
    // if the character literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(CharacterLiteral node) {
        if (!this.names.containsKey(Character.toString(node.charValue()))) {
            this.names.put(Character.toString(node.charValue()), 1);
        } else {
            this.names.put(Character.toString(node.charValue()), this.names.get(Character.toString(node.charValue())) + 1);
        }
        System.out.println("CharacterLiteral");
        return true;
    }
    // Override visit the boolean literal nodes.
    // if the boolean literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(BooleanLiteral node) {
        if (!this.names.containsKey(Boolean.toString(node.booleanValue()))) {
            this.names.put(Boolean.toString(node.booleanValue()), 1);
        } else {
            this.names.put(Boolean.toString(node.booleanValue()), this.names.get(Boolean.toString(node.booleanValue())) + 1);
        }
        System.out.println("BooleanLiteral");
        return true;
    }
    // Override visit the Number literal nodes.
    // if the Number literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
    public boolean visit(NumberLiteral node) {
        if (!this.names.containsKey(node.getToken())) {
            this.names.put(node.getToken(), 1);
        } else {
            this.names.put(node.getToken(), this.names.get(node.getToken()) + 1);
        }
        System.out.println("NumberLiteral");
        return true;
    }
    // Override visit the compilationUnit to be able to retrieve the line numbers.
    public boolean visit(CompilationUnit unit) {
        compilation = unit;
        System.out.println("CompilationUnit");
        return true;
    }
     */
    /**
     * visit() for Cyclomatic Complexity
     */
    @Override
    public boolean visit(IfStatement unit) {
        cycloComplexity++;
        //System.out.println("IFStatement");
        //System.out.println(unit.getElseStatement().toString());
        return true;
    }
    @Override
    public boolean visit(ForStatement unit) {
        cycloComplexity++;
        num_loop++;
        //System.out.println("ForStatement");
        return true;
    }
    @Override
    public boolean visit(WhileStatement unit) {
        cycloComplexity++;
        num_loop++;
        //System.out.println("WhileStatement");
        return true;
    }
    @Override
    public boolean visit(AssertStatement unit) {
        cycloComplexity++;
        //System.out.println("AssertStatement");
        return true;
    }
    @Override
    public boolean visit(TryStatement unit) {
        if (unit.getFinally() != null)
            cycloComplexity++;

        cycloComplexity += unit.catchClauses().size();
        //System.out.println("TryStatement");
        return true;
    }
    @Override
    public boolean visit(SwitchCase unit) {
        if(!unit.isDefault()) {
            cycloComplexity++;
        }
        //System.out.println("SwitchCase");
        return true;
    }
    @Override
    public boolean visit(ConditionalExpression unit){
        cycloComplexity++;
        //System.out.println("ConditionalExpression");
        if (!this.oprt.containsKey("?")) {
            this.oprt.put("?", 1);
        } else {
            this.oprt.put("?", this.oprt.get("?") + 1);
        }

        if (!this.names.containsKey(unit.getElseExpression().toString())) {
            this.names.put(unit.getElseExpression().toString(), 1);
        } else {
            this.names.put(unit.getElseExpression().toString(), this.names.get(unit.getElseExpression().toString()) + 1);
        }
        if (!this.names.containsKey(unit.getThenExpression().toString())) {
            this.names.put(unit.getThenExpression().toString(), 1);
        } else {
            this.names.put(unit.getThenExpression().toString(), this.names.get(unit.getThenExpression().toString()) + 1);
        }
        //System.out.println("Condition Else : " + unit.getElseExpression().toString());
        //System.out.println("Condition Then : " + unit.getThenExpression().toString());
        return true;
    }
    @Override
    public boolean visit(DoStatement unit){
        cycloComplexity++;
        num_loop++;
        //System.out.println("DoStatement");
        return true;
    }

    @Override
    public boolean visit(MethodDeclaration unit){
        num_method++;
        //System.out.println("MethodDeclaration");
        return true;
    }

    @Override
    public boolean visit(ImportDeclaration unit){
        num_import++;
        //System.out.println("MethodDeclaration");
        return true;
    }



}