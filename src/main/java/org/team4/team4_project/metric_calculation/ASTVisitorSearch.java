
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

}
