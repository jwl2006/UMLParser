package tree.tree;

import java.io.*;
import java.util.ArrayList;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
/**
 * Hello world!
 *
 */

                                        


public class App 
{
	 public static void readIn(ArrayList<File> validlist, ArrayList<String> classNameList,
				ArrayList<String> interfaceList,ArrayList<String> extendsList,
				ArrayList<String> implementList)throws Exception 
		{
			 for (File singleFile:validlist)
		        {
		        // creates an input stream for the file to be parsed
		        FileInputStream in = new FileInputStream(singleFile.toString());
		         
		   //     ArrayList<Object> finallist = new ArrayList<Object>();
		        CompilationUnit cu;
		        try {
		            // parse the file
		            cu = JavaParser.parse(in);
		            Class_Object ret = new Class_Object();
		            new ClassVisitor().visit(cu,ret);
		            if (ret.getName()!=null)
		                classNameList.add(ret.getName());
		            if (ret.getInterface()!=null)
		            	interfaceList.add(ret.getInterface());
		            if (ret.getExtends()!=null)
		            	extendsList.add(ret.getExtends());
		            if (ret.getImplements()!=null)
		            	implementList.add(ret.getImplements());
		        } finally {
		            in.close();}
		        }		
		}
	 public static String trimFileName(String filename)
	 {
		 String ret =filename.replace(".java", "");
		 return ret;
	 }

	 public static String findAlienPrimitive(ArrayList<String> nonpriUML,String alien)
	 {
		 for (String nonpri:nonpriUML)
		 {
			 if (nonpri.contains(alien))
				 return nonpri;
		 }
		return null; 
	 }
	 public static void buildPrimitiveUML(ArrayList<String> nonprimitiveUML,
			 							  ArrayList<String> primitiveUML,
			 							 ArrayList<String> nonprimitiveList,
			 							 ArrayList<String> classNameList
			 							  )
	{
		    for (int i=0;i<nonprimitiveList.size();++i)
	        {   
	        	String temp;
	        	if (classNameList.contains(nonprimitiveList.get(i))==false)
	        	{
	        		temp= findAlienPrimitive(nonprimitiveUML,nonprimitiveList.get(i));
	        		primitiveUML.add(temp);
	        	}
	        }	 							  
	}
	 public static String buildprimitiveParameter(String class_name,
			                                      ArrayList<String> primitiveUML)	 													
	 {
		        String ret ="";
        		ret+="[";
        		ret+=class_name;
        		ret+="|";
        		for (int j=0;j<primitiveUML.size()-1;j++)
        		{
        			ret+=primitiveUML.get(j);
        			ret+=";";
        		}
        		ret+=primitiveUML.get(primitiveUML.size()-1);
        		ret+="]";
        		return ret;
     }
		 
		 
		 
	 
	
	    public static void main(String[] args) throws Exception {
	       
	    	ArrayList<File> validlist=new ArrayList<File>();
	        validlist = fileScreen("/Users/wanghao/Downloads/uml-parser-test-1");
	        ArrayList<String> classNameList = new ArrayList<String>();
	        ArrayList<String> interfaceList = new ArrayList<String>();
	        ArrayList<String> extendsList = new ArrayList<String>();
	        ArrayList<String> implementList = new ArrayList<String>();
	        readIn(validlist,classNameList,interfaceList,extendsList,implementList);
	        String file ="A.java";
	        FileInputStream in = new FileInputStream(file);
	        CompilationUnit cu;
	        String class_name = trimFileName(file);
//	    	ArrayList<String> name = new ArrayList<String>();
//	    	 ArrayList<String> type = new ArrayList<String>();
//	   	 ArrayList<String> modifier = new ArrayList<String>();
	   	ArrayList<String> primitiveUML = new ArrayList<String>();
	    	ArrayList<String> nonprimitiveUML = new ArrayList<String>();
	    	ArrayList<String> nonprimitiveList = new ArrayList<String>();
	        try {
	            // parse the file
	            cu = JavaParser.parse(in);
	            Field_Object ret = new Field_Object();
	            new FieldVisitor().visit(cu,ret);
//	            name = ret.getName();
//	            type = ret.getType();
//	            modifier = ret.getModifier();
	            primitiveUML = ret.getprimitiveUML();
	            nonprimitiveUML = ret.getnonprimitiveUML();
	            nonprimitiveList = ret.getNonprimitive();
	           
	        } finally {
	            in.close();}
	        buildPrimitiveUML(nonprimitiveUML,primitiveUML,nonprimitiveList,classNameList);
	        String prime = buildprimitiveParameter(class_name,primitiveUML);
	        System.out.println(prime);
	        	
	        	
	        	
	        		
}
	  
	
	    
	    
	public static boolean accept(File ff) {//重写accept方法
	     if(ff.getName().endsWith(".java")){//name以.java结尾的为符合条件，将被筛出
	            return true;                                          
	        }else{
	            return false;
	        }
	    }    
	public static ArrayList<File> fileScreen(String directory)
	{
		File file = new File(directory);
        File[] filelist = file.listFiles();
        ArrayList<File> validlist=new ArrayList<File>();
        for (File a:filelist)
        {
        	if (accept(a))
        	    validlist.add(a);
        }	
        return validlist;
	}
	
}
