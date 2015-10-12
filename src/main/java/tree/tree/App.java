package tree.tree;

import java.io.*;
import java.util.ArrayList;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
                                     
public class App 
{
	public static String trimExtends(String extend,String className)
	{
		String ret=extend.replace(className, "");
		ret = ret.replace(":", "");
		return ret;
	}
	
	public static String addBraces(String word)
	{
		String ret="[";
		ret = ret + word +"]";
		return ret;
	}
	
	public static ArrayList<String> getExtendRelation(ArrayList<String> extendsList,
													  ArrayList<String>classNameList)
	{
		ArrayList<String> extendRelation =  new ArrayList<String>();
	       for (String extend:extendsList)
	       {
	    	   String ret="";
	    	   for (int i=0;i<classNameList.size();i++)
	    	   {
	    		   if (extend.contains(classNameList.get(i)))
	    		   {
	    				   ret=classNameList.get(i)+"^-";
	    				   String extendName = trimExtends(extend,classNameList.get(i));
	    				   ret+=addBraces(extendName);
	    				   extendRelation.add(ret);
	    		   }		  		
	    	   }
	       }
		return extendRelation;	
	}
	
	public static ArrayList<String> parseImplementList(String implement,ArrayList<String>interfaceList )
	{
		ArrayList<String> ret = new ArrayList<String>();
		for (String interfaceName:interfaceList)
		{
			String temp = interfaceName.split(";")[1];
			temp = temp.replace("]", "");
			if (implement.contains(temp))
				ret.add(interfaceName);
		}
		return ret;
	}
	
	public static String getMainInImplement(String implement)
	{
		String ret =implement.split(":")[0];
		ret = "["+ret+"]";
		return ret;
	}

	public static ArrayList<String> getImplementRelation(ArrayList<String>implementList,
														ArrayList<String>interfaceList)
	{
        ArrayList<String> implementRelation =  new ArrayList<String>();
       for (String implement:implementList)
       {
    	  
    	   ArrayList<String> impl = parseImplementList(implement,interfaceList);	    	   
    	   for (String temp:impl)
    	   {
    		  
    		   String ret="";
    		   ret+=temp;
    		   ret+="^-.-";
    		   ret+=getMainInImplement(implement);
    		   implementRelation.add(ret);
    	   } 
       }
		return implementRelation;
		
	}
		
		
	
	    public static void main(String[] args) throws Exception {
	        ArrayList<String> classNameList = new ArrayList<String>();
	        ArrayList<String> interfaceList = new ArrayList<String>();
	        ArrayList<String> extendsList = new ArrayList<String>();
	        ArrayList<String> implementList = new ArrayList<String>();
//	    	ArrayList<String> name = new ArrayList<String>();
//	    	ArrayList<String> type = new ArrayList<String>();
//	   	 	ArrayList<String> modifier = new ArrayList<String>();
	        ArrayList<String> primitiveUML = new ArrayList<String>();
	    	ArrayList<String> nonprimitiveUML = new ArrayList<String>();
	    	ArrayList<String> nonprimitiveList = new ArrayList<String>();
	    	ArrayList<File> validlist=new ArrayList<File>();
	    	
	        validlist = fileScreen("/Users/wanghao/Downloads/uml-parser-test-2");
	        readIn(validlist,classNameList,interfaceList,extendsList,implementList);
	        ArrayList<String> extendRelation = getExtendRelation(extendsList,classNameList);
	        ArrayList<String> implementRelation=getImplementRelation(implementList,interfaceList);
	      
	        

	       
	       for(String ir:implementRelation)
	       {
	    	   System.out.println(ir);
	       }
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        String singleFile = "A.java";
	        	
	        FileInputStream in = new FileInputStream(singleFile);
	        CompilationUnit cu;
	        String class_name = trimFileName(singleFile.toString());

	        try {
	            // parse the file
	            cu = JavaParser.parse(in);
	            Field_Object ret = new Field_Object();
	            new FieldVisitor().visit(cu,ret);
	          
	            primitiveUML = ret.getprimitiveUML();
	            nonprimitiveUML = ret.getnonprimitiveUML();
	            nonprimitiveList = ret.getNonprimitive();
	           
	        } finally {
	            in.close();}
	        
	        buildPrimitiveUML(nonprimitiveUML,primitiveUML,nonprimitiveList,classNameList);
	        String prime = buildprimitiveParameter(class_name,primitiveUML);
	        ArrayList<String> finalresult= getFieldAll(classNameList,class_name,nonprimitiveUML,prime);
	        /* 
	        	for (String a: finalresult)
	        	{
	        		System.out.println(a);
	        	} 
	        	*/       		
	    }
	       
	    
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
		//	 if (!primitiveUML.isEmpty())
			 {
			 ret+=primitiveUML.get(primitiveUML.size()-1);
			 ret+="]";}
			 return ret;
			 
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
		 
	    public static ArrayList<String> getFieldAll(ArrayList<String> classNameList,
				String class_name,ArrayList<String> nonprimitiveUML,
				String prime)
		{
	    	ArrayList<String> finalresult=new ArrayList<String>();
	    	boolean sign = false;
	    	for (int i=0;i<classNameList.size();i++)
	    	{
	    		String ret="";
	    		String appendString = classNameList.get(i);
	    		if (!appendString.equals(class_name) && sign==false)
	    		{
	    			String temp =findAlienPrimitive(nonprimitiveUML,appendString);
	    			ret+=prime+"1"+temp;
	    			finalresult.add(ret);
	    			sign=true;
	    			continue;
	    		}
	    		if (appendString!=class_name && sign==true)
	    		{
	    			String temp =findAlienPrimitive(nonprimitiveUML,appendString);
	    			ret+="[";
	    			ret+=class_name;
	    			ret+="]";
	    			ret+=temp;
	    			finalresult.add(ret);
	    		}
	    	}
	    	return finalresult;
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
	    		{
        	    validlist.add(a);
	    		}
	    	}	
	    	return validlist;
	    }
	
}
