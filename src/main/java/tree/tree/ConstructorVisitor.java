package tree.tree;

import com.github.javaparser.ast.body.ConstructorDeclaration;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


	public class ConstructorVisitor extends VoidVisitorAdapter<Constructor_Object> {

	    @Override
	    public void visit(ConstructorDeclaration n, Constructor_Object ret) {
	        // here you can access the attributes of the method.
	        // this method will be called for all methods in this 
	        // CompilationUnit, including inner class methods
	   
	    	
	    		if (n.getName()!=null)
	    			ret.setName(n.getName());
	    	    String param = n.getParameters().toString();
	    	//    System.out.println(param);
	    	   
	    
	    	    ret.setParameter(param.toString());
	    
	    	    ret.setModifier(n.getModifiers());
	    	
	    }
	}

