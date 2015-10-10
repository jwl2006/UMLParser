package tree.tree;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<Class_Object> {

	    @Override
	    public void visit(ClassOrInterfaceDeclaration n, Class_Object obj) {
	        // here you can access the attributes of the method.
	        // this method will be called for all methods in this 
	        // CompilationUnit, including inner class methods
	       
	    	   obj.setName(n.getName());
	    	   if(n.getExtends()!=null)
	    		   obj.setExtends(n.getExtends().toString());
	    	   if(n.getImplements()!=null)
	    		   obj.setImplements(n.getImplements().toString());
	    }
}

