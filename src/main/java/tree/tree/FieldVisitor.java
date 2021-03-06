package tree.tree;





import com.github.javaparser.ast.body.FieldDeclaration;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldVisitor extends VoidVisitorAdapter<Field_Object> {
  
	@Override
    public void visit(FieldDeclaration n, Field_Object ret) {
        // here you can access the attributes of the method.
        // this method will be called for all methods in this 
        // CompilationUnit, including inner class methods
    	 
    		
    	    ret.setName(n.getVariables().toString());
    	    ret.setType(n.getType().toString());
    //	    if (n.getModifiers()=="1"||n.getModifiers()==2)
                ret.setModifier(n.getModifiers());
        }
       
    }
    


