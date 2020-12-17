package org.team4.team4_project.metric_calculation;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class ASTVisitorSearch extends ASTVisitor {
    public HashMap<String, Integer> names = new HashMap<String, Integer>();
    public HashMap<String, Integer> oprt = new HashMap<String, Integer>();

    public int codeLen = 0;
    public int commentLen = 0;
    public int cycloComplexity = 1;
    public int num_method = 0;
    public int num_loop = 0;
    public int num_import = 0;

    /**
     * A method visits InfixExpression and count operators & operands
     * @param node ASTNode InfixExpression
     * @return true
     */
    @Override
    public boolean visit(InfixExpression node) {
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

        //For cyclomatic complexity (AND, OR)
        if (node.getOperator().toString().equals("&&")) {
            cycloComplexity++;
        }
        if (node.getOperator().toString().equals("||")) {
            cycloComplexity++;
        }
        return true;
    }
    /**
     * A method visits PostfixExpression and count operators & operands
     * @param node ASTNode PostfixExpression
     * @return true
     */
    @Override
    public boolean visit(PostfixExpression node) {
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

        return true;
    }
    /**
     * A method visits PrefixExpression and count operators & operands
     * @param node ASTNode PrefixExpression
     * @return true
     */
    @Override
    public boolean visit(PrefixExpression node) {
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

        return true;
    }
    /**
     * A method visits Assignment and count operators & operands
     * @param node ASTNode Assignment
     * @return true
     */
    @Override
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

        return true;
    }
    /**
     * A method visits SingleVariableDeclaration and count operators & operands
     * @param node ASTNode SingleVariableDeclaration
     * @return true
     */
    @Override
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
        return true;
    }
    /**
     * A method visits VariableDeclarationFragment and count operators & operands
     * @param node ASTNode SingleVariableDeclaration
     * @return true
     */
    @Override
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
        return true;
    }
    /**
     * A method visits SimpleName and count assert, List, Set, Map
     * @param node ASTNode SimpleName
     * @return true
     */
    @Override
    public boolean visit(SimpleName node) {
        if (node.getIdentifier().equals("assert") || node.getIdentifier().equals("List") || node.getIdentifier().equals("Set") || node.getIdentifier().equals("Map")) {
            cycloComplexity++;
        }

        return true;
    }
    /**
     * A method visits IfStatement and count cycloComplexity
     * @param unit ASTNode IfStatement
     * @return true
     */
    @Override
    public boolean visit(IfStatement unit) {
        cycloComplexity++;
        return true;
    }
    /**
     * A method visits ForStatement and count cycloComplexity, number of loops
     * @param unit ASTNode ForStatement
     * @return true
     */
    @Override
    public boolean visit(ForStatement unit) {
        cycloComplexity++;
        num_loop++;
        return true;
    }
    /**
     * A method visits WhileStatement and count cycloComplexity, number of methods
     * @param unit ASTNode WhileStatement
     * @return true
     */
    @Override
    public boolean visit(WhileStatement unit) {
        cycloComplexity++;
        num_loop++;
        return true;
    }
    /**
     * A method visits TryStatement and count cycloComplexity
     * @param unit ASTNode ForStatement
     * @return true
     */
    @Override
    public boolean visit(TryStatement unit) {
        if (unit.getFinally() != null)
            cycloComplexity++;

        cycloComplexity += unit.catchClauses().size();
        return true;
    }
    /**
     * A method visits SwitchCase and count cycloComplexity
     * @param unit ASTNode ForStatement
     * @return true
     */
    @Override
    public boolean visit(SwitchCase unit) {
        if(!unit.isDefault()) {
            cycloComplexity++;
        }
        return true;
    }
    /**
     * A method visits ConditionalExpression and count operators and operands
     * @param unit ASTNode ConditionalExpression
     * @return true
     */
    @Override
    public boolean visit(ConditionalExpression unit){
        cycloComplexity++;
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

        return true;
    }
    /**
     * A method visits DoStatement and count cycloComplexity and count number of loops
     * @param unit ASTNode DoStatement
     * @return true
     */
    @Override
    public boolean visit(DoStatement unit){
        cycloComplexity++;
        num_loop++;
        return true;
    }
    /**
     * A method visits MethodDeclaration and count number of methods
     * @param unit ASTNode DoStatement
     * @return true
     */
    @Override
    public boolean visit(MethodDeclaration unit){
        num_method++;
        return true;
    }
    /**
     * A method visits ImportDeclaration and count number of imports
     * @param unit ASTNode ImportDeclaration
     * @return true
     */
    @Override
    public boolean visit(ImportDeclaration unit){
        num_import++;
        return true;
    }

}