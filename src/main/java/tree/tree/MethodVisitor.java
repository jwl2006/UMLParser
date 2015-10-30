package tree.tree;



import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.stmt.BlockStmt;
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
    	 
    	    if (ModifierSet.isAbstract(n.getModifiers()))
    	    	ret.setModifier(n.getModifiers()-1024);
    	    else if (ModifierSet.isStatic(n.getModifiers()))
    	    	ret.setModifier(n.getModifiers()-8);
    	    else
    	    	ret.setModifier(n.getModifiers());
    	      
    	    BlockStmt block = n.getBody();
			if (block != null)
			{
				for (Node node : block.getChildrenNodes()) 
				{
		//			System.out.println(node.toString());
					String[] strs = node.toString().split(" ");
					String declear = strs[0];
					if (!declear.equals("return"))
					ret.setmethodBodydependency(declear);
				}
			}
    	 
    }
}
    

    
    
 

