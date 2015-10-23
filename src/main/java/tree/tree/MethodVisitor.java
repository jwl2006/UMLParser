package tree.tree;
import java.util.ArrayList;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<Method_Object> {

    @Override
    public void visit(MethodDeclaration n, Method_Object ret) {
        // here you can access the attributes of the method.
        // this method will be called for all methods in this 
        // CompilationUnit, including inner class methods
   
    	
   
    	    ret.setName(n.getName());
    	    String param = n.getParameters().toString();
    	//    System.out.println(param);
    	   
    
    	    ret.setParameters(param.toString());
       
    	    ret.setType(n.getType().toString());

    	    ret.setModifier(n.getModifiers());
    	
    }
}
    

    
    
 

