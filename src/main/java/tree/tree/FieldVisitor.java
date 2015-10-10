package tree.tree;





import com.github.javaparser.ast.body.FieldDeclaration;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldVisitor extends VoidVisitorAdapter<Field_Object> {
  
	@Override
    public void visit(FieldDeclaration n, Field_Object ret) {
        // here you can access the attributes of the method.
        // this method will be called for all methods in this 
        // CompilationUnit, including inner class methods
    	
    	 //   System.out.println(n.getVariables());
    	    ret.setName(n.getVariables().toString());
    	 //   System.out.println(n.getType());
    	    ret.setType(n.getType().toString());
    	  //  System.out.println(n.getModifiers());
            ret.setModifier(n.getModifiers());
        }
       
    }
    


