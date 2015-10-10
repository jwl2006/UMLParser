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
	    public static void main(String[] args) throws Exception {
	        ArrayList<File> validlist=new ArrayList<File>();
	        validlist = fileScreen("/Users/wanghao/Downloads/uml-parser-test-2");
	        ArrayList<Class_Resource> resources = new ArrayList<Class_Resource>();
	    for (File singleFile:validlist)
	        {
	        // creates an input stream for the file to be parsed
	        FileInputStream in = new FileInputStream(singleFile.toString());
	         Field_Object field = new Field_Object();
	         Method_Object method= new Method_Object();
	         Class_Object classchar = new Class_Object();
	         Class_Resource singleResource = new Class_Resource();
	   //     ArrayList<Object> finallist = new ArrayList<Object>();
	        CompilationUnit cu;
	        try {
	            // parse the file
	            cu = JavaParser.parse(in);
	            new MethodVisitor().visit(cu, method);
	            new FieldVisitor().visit(cu, field);
	            new ClassVisitor().visit(cu, classchar);
	            singleResource.setClassObj(classchar);
	            singleResource.setMethodObj(method);
	            singleResource.setFieldObj(field);
	            resources.add(singleResource);
	        } finally {
	            in.close();}
	        }
	
	        
	  //     ArrayList<ArrayList<String>> finalList = ret.parseToken();
	  //     ret.print_fieldObj(finalList);
	   //    ArrayList<String> typeList = ret.getType();
	    //   for(String list:typeList)
	     //  {
	   // 	   String temp=ret.collectionParse(list);
	    //	   System.out.println(temp);
	     //  }
	       
	       
	       
	       
	        // prints the resulting compilation unit to default system output
	       // System.out.println(cu.toString());
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
