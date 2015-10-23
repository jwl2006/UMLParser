package tree.tree;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<Class_Object> {

	    @Override
	    public void visit(ClassOrInterfaceDeclaration n, Class_Object obj) {
	        // here you can access the attributes of the method.
	        // this method will be called for all methods in this 
	        // CompilationUnit, including inner class methods
	    		if (n.getName()!=null && n.isInterface()==false)
	    		{
	    			String ret="";
	    			ret+=n.getName();
	    			
	    			obj.setName(ret);
	    		}
	    	   if(n.getExtends()!=null)
	    		   obj.setExtends(n.getName()+":"+ n.getExtends().toString());
	    	   if(n.getImplements()!=null)
	    		   obj.setImplements(n.getName()+":"+n.getImplements().toString());
	    	   if (n.isInterface()){
	    		   String ret ="[<<interface>>;";
	    		   ret=ret+n.getName()+"]";
	    		   obj.setInterface(ret);}
	    	   
	    		   
	    }
	    
	   
}

