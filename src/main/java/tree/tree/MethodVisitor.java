package tree.tree;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<Method_Object> {

    @Override
    public void visit(MethodDeclaration n, Method_Object ret) {
        // here you can access the attributes of the method.
        // this method will be called for all methods in this 
        // CompilationUnit, including inner class methods
   
    	if (n.getModifiers()==1)
    	{
    	    ret.setName(n.getName());
        
    	    ret.setParameters(n.getParameters().toString());
       
    	    ret.setType(n.getType().toString());

    	    ret.setModifier(n.getModifiers());
    	}
    }
}

