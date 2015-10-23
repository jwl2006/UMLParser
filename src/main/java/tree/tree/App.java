package tree.tree;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;



import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
        //http://www.mkyong.com/java/how-to-read-an-image-from-file-or-url/                             
public class App 
{
	public static void main(String[] args) throws Exception {
		
	    ArrayList<String> classNameList = new ArrayList<String>();
	    ArrayList<String> interfaceList = new ArrayList<String>();
	    ArrayList<String> extendsList = new ArrayList<String>();
	    ArrayList<String> implementList = new ArrayList<String>();
	    ArrayList<File> validlist=new ArrayList<File>();
	    ArrayList<String> allRelations = new ArrayList<String>();
	    ArrayList<String> relationList = new ArrayList<String>();
	    //Read the classname, extends, interface in the directory
	    validlist = fileScreen("/Users/wanghao/Downloads/uml-parser-test-5");
	    App app = new App();
	    app.readIn(validlist,classNameList,interfaceList,extendsList,implementList);
 
	    //for each file, get the relations
	    for (File validfile:validlist)
	    {
	    String file=validfile.toString(); 

		ArrayList<String> fieldprimitiveUML = new ArrayList<String>();
		ArrayList<String> fieldnonprimitiveUML = new ArrayList<String>();
		ArrayList<String> fieldnonprimitiveList = new ArrayList<String>();
		ArrayList<String> methodUML = new ArrayList<String>();
		ArrayList<String> methodNonPrimitiveList = new ArrayList<String>();
		ArrayList<String> constructorNonPrimitiveList = new ArrayList<String>();
		String allCompartment=""; String class_name=""; String constructorUML="";
		
		
	    app.fieldReadin(classNameList,file,fieldprimitiveUML,fieldnonprimitiveUML,fieldnonprimitiveList);
		class_name = app.MethodReadin(classNameList, file, methodUML, methodNonPrimitiveList);
		constructorUML += app.constructorReadin(file,constructorNonPrimitiveList);
		allCompartment = app.buildAllCompartment(class_name,fieldprimitiveUML,methodUML,constructorUML);

		ArrayList<String> extendFound = app.buildExtendsRelation(extendsList,class_name, allCompartment);
		ArrayList<String> implementFound = app.buildImplementRelation(implementList,class_name,interfaceList,allCompartment);
		ArrayList<String> associationFound = app.buildAssociation(fieldnonprimitiveUML, allCompartment,class_name,relationList);
		ArrayList<String> allNonprimitiveForDependency= app.combineNonprimitives(constructorNonPrimitiveList, methodNonPrimitiveList);
		ArrayList<String> dependencyFound = app.buildDependency(allCompartment, interfaceList, allNonprimitiveForDependency);
		
		allRelations.addAll(extendFound);
		allRelations.addAll(implementFound);
		allRelations.addAll(associationFound);
		allRelations.addAll(dependencyFound);
	    }    
		System.out.println(allRelations);
		String all ="";
         for (int i=0;i<allRelations.size()-1;i++)
         {
        	 all+=allRelations.get(i);
        	 all+=",";
         }
         all+=allRelations.get(allRelations.size()-1);
         String destinationFile = "output.jpg";
         getoutImage(all,destinationFile);
         
              
	}
	public static void getoutImage(String allRelations,String destinationFile)throws Exception
	{
		String baseUrl ="http://yuml.me/diagram/nofunky/class/";
		String imageUrl = baseUrl+allRelations;
		saveImage(imageUrl, destinationFile);	
	}
	
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);

	    byte[] b = new byte[2048];
	    int length;

	    while ((length = is.read(b)) != -1) {
	        os.write(b, 0, length);
	    }

	    is.close();
	    os.close();
	}
	
		public ArrayList<String> combineNonprimitives (ArrayList<String>constructorNonPrimitiveList, 
												   ArrayList<String>methodNonPrimitiveList)
		{
			
			if (constructorNonPrimitiveList.size()>0)
			{
				for (String constructs:constructorNonPrimitiveList)
				{
					if(isInMethodnonprimitive(constructs,methodNonPrimitiveList)==false)
						methodNonPrimitiveList.add(constructs);
				}
			}
			return methodNonPrimitiveList;
		}
	
		public boolean isInMethodnonprimitive(String constructornonprimitive,ArrayList<String>methodNonPrimitiveList)
		{
			for (int i=0;i<methodNonPrimitiveList.size();i++)
			{
				if (constructornonprimitive.equals(methodNonPrimitiveList.get(i)))
					return true;
			}
			return false;	
		}
	
		public ArrayList<String>buildDependency(String allCompartment, ArrayList<String> interfaceList,
			                   ArrayList<String>methodNonPrimitveList)
	    {
			ArrayList<String> result = new ArrayList<String>();
			for(String nonprimitive:methodNonPrimitveList)
			{
				String ret="";
				ret+=allCompartment;
				ret+="uses -.->";
				ret+=findDependency(interfaceList,nonprimitive);
				result.add(ret);
			}
			return result;
	    }
	
		public String findDependency(ArrayList<String> interfaceList,String target)
		{
			for (String itf:interfaceList)
			{
				String[] temp = itf.split(";");
				String middle = temp[1].replace("]","");
				if (temp.length>0 && middle.equals(target))
				{
					return itf;
				}
			}
			return null;
		}
	
		public boolean isInRelation(String target,ArrayList<String>relationList)
		{
			String reversed = new StringBuffer(target).reverse().toString();
			for (String relation:relationList)
			{
				if (relation.equals(target)||relation.equals(reversed))
					{return true;}	
			}
			return false;	
		}
		
		
		
		public ArrayList<String> buildAssociation (ArrayList<String> fieldnonprimitiveUML,String allCompartment,String class_name,
												   ArrayList<String>relationList)
		{
			ArrayList<String> result = new ArrayList<String>();
			for (String nonprimitve:fieldnonprimitiveUML)
			{
				String relation="";
				relation+=class_name;
				relation+=trimfieldnonprimitive(nonprimitve);
				if (!isInRelation(relation,relationList))
				{
					relationList.add(relation);			
					String ret ="";
					ret += allCompartment;
					ret +=nonprimitve;
					result.add(ret);
				}
			}
			return result;
		}
		
		public String trimfieldnonprimitive(String nonprimitive)
		{
			String ret ="";
			ret = nonprimitive.replace("1", "");
			ret = ret.replace("*", "");	
			ret = ret.replace("[", "");
			ret = ret.replace("]", "");
			return ret;
		}
	
		public ArrayList<String> buildImplementRelation(ArrayList<String>implementList,String class_name,
												ArrayList<String> interfaceList, String allCompartment)
		{
			ArrayList<String> result = new ArrayList<String>();
			ArrayList<String> implementFound = findImplements(implementList,class_name);
			ArrayList<String> trimedImplement = parseImplementFound(implementFound,class_name);
		
			for (String temp: trimedImplement)
			{
				String ret = findInterface(interfaceList, temp);
				ret+="^-.-";
				ret+=allCompartment;
				result.add(ret);
			}
			return result;
		}	 
		
		public ArrayList<String> parseImplementFound(ArrayList<String> implementFound,String class_name)
		{
			ArrayList<String> result = new ArrayList<String>();
			for (String imple:implementFound)
			{
				String[] ret = imple.split(",");//B2:[A1, A2]
				for (int i=0;i<ret.length;i++)
				{
					String temp =ret[i].trim();
					temp = temp.replace(class_name, "");
					temp = temp.replace(":", "");
					temp = temp.replace("[", "");
					temp = temp.replace("]", "");
					String build = class_name;
					build+=":["+temp+"]";
					result.add(build);		
				}
			}
			return result;
		}
		
		public String findInterface(ArrayList<String> interfaceList,String query)
		{
			String[] splited = query.split(":");
			String target ="";
			if (splited.length>0)
			{
				target = trimBracket(splited[1]);
			}
			for (String itf:interfaceList)
			{
				String[] temp = itf.split(";");
				String middle = temp[1].replace("]","");
				if (temp.length>0 && middle.equals(target))
				{
					return itf;
				}
			}
			return null;
		}
	
		public String trimBracket(String queryRet)
		{
			String temp= queryRet.replace("[", "");
			temp = temp.replace("]", "");
			return temp;	
		}
	
	
		public ArrayList<String> findImplements(ArrayList<String>implementList, String class_name)
		{
			ArrayList<String> result=new ArrayList<String>();
			for (String imple:implementList)
			{
				String[] temp =imple.split(":");
				if (temp[0].equals(class_name))
				{
					result.add(imple);
				}
			}
			return result;
		}
	
		public ArrayList<String> buildExtendsRelation(ArrayList<String> extendsList,String class_name, String allCompartment)
		{
			ArrayList<String> extendRelation =findExtends(extendsList,class_name);
			ArrayList<String> result = new ArrayList<String>();
			for (String extend:extendRelation)
			{	
				String[]temp =extend.split(":");
				String parent = temp[1];
				parent+="^-";
				parent+=allCompartment;
				result.add(parent);
			}
			return result;
		}
	
	
	
		public ArrayList<String> findExtends(ArrayList<String>extendsList, String class_name)
		{
			ArrayList<String> result=new ArrayList<String>();
			for (String extend:extendsList)
			{
				String[] temp =extend.split(":");
				if (temp[0].equals(class_name))
				{
					result.add(extend);
				}
			}
			return result;
		}
	
		public ArrayList<String> getExtendRelation(ArrayList<String> extendsList,ArrayList<String>classNameList)
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
	
		public String buildAllCompartment(String class_name, ArrayList<String>fieldprimitiveUML,ArrayList<String>methodUML,
    					             String constructorUML)
		{
    	   	String allcompartment = "[";
    	   	allcompartment+=class_name;
    	   	
    		if (fieldprimitiveUML.size()>0 )
    		{
    			allcompartment+='|';
    		
    			for (int i=0;i<fieldprimitiveUML.size()-1;i++)
    			{
    				allcompartment+=fieldprimitiveUML.get(i);
    				allcompartment+=";";
    			}
    	   		allcompartment+=fieldprimitiveUML.get(fieldprimitiveUML.size()-1);
    		}
    		if (methodUML.size()>0)
    		{
    	   		allcompartment+='|';
    	   		allcompartment+=constructorUML+";";
    	   		for (int i=0;i<methodUML.size()-1;i++)
    	   		{
    	   			allcompartment+=methodUML.get(i);
    	   			allcompartment+=";";
    	   		}
    	   		allcompartment+=methodUML.get(methodUML.size()-1);
    	   	}
    	   	allcompartment+="]";
    	   	return allcompartment;
		}
	
	
		public String constructorReadin(String File,ArrayList<String>constructorNonPrimitiveList)throws Exception
		{
			FileInputStream in = new FileInputStream(File);
			CompilationUnit cu;
		
			cu = JavaParser.parse(in);
			Constructor_Object ret = new Constructor_Object();
			new ConstructorVisitor().visit(cu,ret);
			ArrayList<String> result = ret.getnonprimitiveList();
			result.removeAll(Arrays.asList(null,""));
			constructorNonPrimitiveList.addAll(result);
			return ret.getconstructorUML();
		}
	
		public void fieldReadin(ArrayList<String>classNameList,String File,
			ArrayList<String> fieldprimitiveUML,ArrayList<String> fieldnonprimitiveUML,
			ArrayList<String> fieldnonprimitiveList) throws Exception	
		{	
		
			FileInputStream in = new FileInputStream(File);
			CompilationUnit cu;
		
			cu = JavaParser.parse(in);
			Field_Object ret = new Field_Object();
		
			new FieldVisitor().visit(cu,ret);

			fieldprimitiveUML.addAll(ret.getprimitiveUML());
			fieldnonprimitiveUML.addAll(ret.getnonprimitiveUML()); //association listfieldprimitiveUML
			fieldnonprimitiveList.addAll(ret.getNonprimitive());
			buildPrimitiveUML(fieldnonprimitiveUML,fieldprimitiveUML,fieldnonprimitiveList, classNameList);   
		}	
	
	
		public String MethodReadin(ArrayList<String>classNameList,String File,
			ArrayList<String> methodUML,ArrayList<String> methodNonPrimitiveList) throws Exception 			
		{
			FileInputStream in = new FileInputStream(File);
			CompilationUnit cu;

			cu = JavaParser.parse(in);
			Method_Object Method = new Method_Object();
			new MethodVisitor().visit(cu,Method); 
			Class_Object cls = new Class_Object();
			new ClassVisitor().visit(cu,cls);
			Method.setClassName(cls.getName());
	         
			methodUML.addAll(Method.getprimitiveUML());
			ArrayList<String> prononprimitive = Method.getnonprimitiveList();
			prononprimitive.removeAll(Arrays.asList(null,""));
			methodNonPrimitiveList.addAll(prononprimitive);
	    
			return Method.getClassName();
		}
	
	

		public void buildPrimitiveUML(ArrayList<String> fieldnonprimitiveUML,ArrayList<String> fieldprimitiveUML,
									  ArrayList<String> fieldnonprimitiveList,ArrayList<String> classNameList)
		{
			for (int i=0;i<fieldnonprimitiveList.size();++i)
			{   
				String temp;
	
				if (classNameList.contains(fieldnonprimitiveList.get(i))==false)
				{
					temp= findAlienPrimitive(fieldnonprimitiveUML,fieldnonprimitiveList.get(i));
					fieldnonprimitiveUML.remove(temp);
					temp = trimprimitiveUML(temp);
					fieldprimitiveUML.add(temp);
				}
			}	 							  
		}
	 
		public static String trimprimitiveUML(String nonprimitive)
		{
			String ret="";
			if (nonprimitive.contains("1["))
				ret = nonprimitive.replace("1[","");
			else if (nonprimitive.contains("*[")){
				ret = nonprimitive.replace("*[", "");
				ret = ret+ "(*)";}
			ret=ret.replace("]", "");
			return ret;
		}
	
		public String trimParamUML(String param)
		{
			param = param.split(":")[0];
			param = param.replace("[", "");
			param = param.replace("]", "");
			return param;   
		}
	 
		public static String parseParamUML(String param)
		{
			param = param.split(":")[1];
			return param;   
		}
		public void readIn(ArrayList<File> validlist, ArrayList<String> classNameList,
					ArrayList<String> interfaceList,ArrayList<String> extendsList,
					ArrayList<String> implementList)throws Exception 
		{
				for (File singleFile:validlist)
				{
			        // creates an input stream for the file to be parsed
			        FileInputStream in = new FileInputStream(singleFile.toString());
			        CompilationUnit cu;
			        try {
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
	   
		 public String trimFileName(String filename)
		 {
			 String ret =filename.replace(".java", "");
			 return ret;
		 }

		 public String findAlienPrimitive(ArrayList<String> nonpriUML,String alien)
		 {
			 for (String nonpri:nonpriUML)
			 {
				 if (nonpri.contains(alien))
					 return nonpri;
			 }
			return null; 
		 }
	      
	    public ArrayList<String> getFieldAll(ArrayList<String> classNameList,
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
	    
		public String trimExtends(String extend,String className)
		{
			String ret=extend.replace(className, "");
			ret = ret.replace(":", "");
			return ret;
		}
		
		public String addBraces(String word)
		{
			String ret="[";
			ret = ret + word +"]";
			return ret;
		}
					
	    public static boolean accept(File ff) 
	    {
	    	if(ff.getName().endsWith(".java")){
	            return true;}
	    	else{
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
