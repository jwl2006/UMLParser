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
	    ArrayList<String> allCompartmentList =new ArrayList<String>();

	    String directory =args[0];

	    System.out.println(directory);
	    validlist = fileScreen(directory);
	    App app = new App();
	    app.readIn(validlist,classNameList,interfaceList,extendsList,implementList);
	    ArrayList<ClassRelations> allResources = new ArrayList<ClassRelations>();
	    //for each file, get the relations

	
	    for (File validfile:validlist)
	    {
	    String file=validfile.toString(); 

	    String class_name = app.parseGetClassName(file,directory);

	    ClassRelations relationClass = new ClassRelations();
		ArrayList<String> fieldprimitiveUML = new ArrayList<String>();
		ArrayList<String> fieldnonprimitiveUML = new ArrayList<String>();
		ArrayList<String> fieldnonprimitiveList = new ArrayList<String>();
		ArrayList<String> methodUML = new ArrayList<String>();
		ArrayList<String> methodNonPrimitiveList = new ArrayList<String>();
		ArrayList<String> constructorNonPrimitiveList = new ArrayList<String>();
		ArrayList<String> methodBodydependency = new ArrayList<String>();
		String allCompartment="";  String constructorUML="";
		
		app.MethodReadin(classNameList, file, methodUML, methodNonPrimitiveList,methodBodydependency);
	    app.fieldReadin(classNameList,file,fieldprimitiveUML,fieldnonprimitiveUML,fieldnonprimitiveList);
		ArrayList<String> targetList = app.getSgetter_target(methodUML, fieldprimitiveUML);
		methodUML =app.screenmethodUML(targetList,methodUML) ;
		fieldprimitiveUML =app.screenfieldprimitiveUML(targetList,fieldprimitiveUML);
	       
		constructorUML += app.constructorReadin(file,constructorNonPrimitiveList);
		allCompartment = app.buildAllCompartment(class_name,fieldprimitiveUML,methodUML,constructorUML,classNameList,interfaceList);
		allCompartmentList.add(allCompartment);
		

		relationClass.setallCompartment(allCompartment);
		relationClass.setclass_name(class_name);
		relationClass.setconstructorNonPrimitiveList(constructorNonPrimitiveList);
		relationClass.setfieldnonprimitiveList(fieldnonprimitiveList);
		relationClass.setfieldnonprimitiveUML(fieldnonprimitiveUML);
		relationClass.setfieldprimitiveUML(fieldprimitiveUML);
		relationClass.setmethodNonPrimitiveList(methodNonPrimitiveList);
		relationClass.setmethodUML(methodUML);
		relationClass.setconstructorUML(constructorUML);
		
		
		allResources.add(relationClass);	
		
		ArrayList<String> associationFound = app.buildAssociation(fieldnonprimitiveUML, allCompartment,class_name,relationList);
		ArrayList<String> allNonprimitiveForDependency= app.combineNonprimitives(constructorNonPrimitiveList, methodNonPrimitiveList);
		ArrayList<String> dependencyFound = app.buildDependency(allCompartment, interfaceList, allNonprimitiveForDependency, methodBodydependency);

		allRelations.addAll(associationFound);
		allRelations.addAll(dependencyFound);
	    }    
		
	    ArrayList<String> implementFound = app.buildImplementRelation(implementList,allResources);
	    ArrayList<String> extendFound = app.buildExtendsRelation(extendsList,allResources);
	    allRelations.addAll(implementFound);
	    allRelations.addAll(extendFound);
	    System.out.println(allRelations);
	    
	    	   
         String destinationFile = args[1];
         getoutImage(allRelations,destinationFile);
	}
	
	public ArrayList<String> getSgetter_target(ArrayList<String> methodUML, ArrayList<String> fieldprimitiveUML)
	{
		ArrayList<String> targetlist = new ArrayList<String>();
	   	ArrayList<String> trimmedMethod = trimSgetters(methodUML);
	    ArrayList<String> trimmedPrimitive = trimSgetters(fieldprimitiveUML);
	      
	       	for (String primi: trimmedPrimitive)
	       	{
	       		String target = isSgetters(primi,trimmedMethod);

	       	 	targetlist.add(target);
	       	}
	  	return targetlist;
	}
	
	
	public String trimword_Sgetter (String field)
	{
		String []temp = field.split(":");
		String mid =temp[0].replace("-", "");
		mid =mid.replace("+", "");
		mid = mid.replaceAll("\\(.*", "");
		mid = mid.toLowerCase();
		return mid;
	}
	
	public ArrayList<String> trimSgetters(ArrayList<String> methodUML)
	{
		ArrayList<String> result = new ArrayList<String> ();
		for (String field:methodUML)
		{
			String mid = trimword_Sgetter(field);
			result.add(mid);
		}
		return result;
	}
	

	
	public String isSgetters (String target, ArrayList<String> method)
	{ 
		if (method.contains("set"+target) && method.contains("get"+target))
		{
			return target;
			
		}
		return null;
	}

	public ArrayList<String> screenfieldprimitiveUML (ArrayList<String> target, ArrayList<String> fieldprimitiveUML)
	{
		ArrayList<String> result = new ArrayList<String>();
		for (String primi:fieldprimitiveUML)
		{
			String temp = trimword_Sgetter(primi);
			if (target.contains(temp)==true)
			{
				primi = primi.replace("-", "");
				primi = primi.replace("+", "");
				primi = "+"+primi;
				result.add(primi);
			}
			else
				result.add(primi);
		}
		return result;
	}
	
	
	public ArrayList<String> screenmethodUML (ArrayList<String> targetList, ArrayList<String> methodUML)
	{
		ArrayList<String> result = new ArrayList<String>();
		for (String primi:methodUML)
		{
			String temp = trimword_Sgetter(primi);
			for (String target:targetList)
			{				
				if (("set"+target).equals(temp) || ("get"+target).equals(temp))
					primi = "";
			}
			result.add(primi);
		}
		result.removeAll(Arrays.asList(null,""));		
		return result;
	}
	
	
	public String findTarget (String query,ArrayList<String> fieldprimitiveUML)
	{
		for (String candidate:fieldprimitiveUML)
		{
			candidate = candidate.toLowerCase();
			if (candidate.contains(query))
				return candidate;
		}
		return null;
	}
	
	
	public String parseGetClassName(String filename,String directory)
	{
		String ret = filename.replace(directory, "");
		ret = ret.replace("/", "");
		ret = ret.replace(".java","");
		return ret;
	}
	
	
	
	
	public static void getoutImage(ArrayList<String> allRelations,String destinationFile)throws Exception
	{
		String all ="";
        for (int i=0;i<allRelations.size()-1;i++)
        {
       	 all+=allRelations.get(i);
       	 all+=",";
        }
        if (allRelations.size()>0)
            all+=allRelations.get(allRelations.size()-1);
		String baseUrl ="http://yuml.me/diagram/nofunky/class/";
		String imageUrl = baseUrl+all;
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
	
		public ArrayList<String> findmethodBodyList(ArrayList<String>methodBodydependency,ArrayList<String> interfaceList)
		{
			 ArrayList<String> result = new ArrayList<String>();
			for (String candi:methodBodydependency)
			{
				String ret = findDependency(interfaceList,candi);
				if (ret!=null)
					result.add(ret);
			}		
			return result;	
		}
		
		public ArrayList<String>buildDependency(String allCompartment, ArrayList<String> interfaceList,
			                   ArrayList<String>methodNonPrimitveList,ArrayList<String>methodBodydependency)
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
		
		public String findInallResources(ArrayList<ClassRelations> allResources, String class_name)
		{
			String ret ="";
			for(ClassRelations relation:allResources)
			{
				if (class_name.equals(relation.getclass_name()))
					 ret=relation.getallCompartment();
			}
			return ret;	
		}
		
		public ArrayList<String> parseImplementList(ArrayList<String>implementList)
		{
			ArrayList<String> result = new ArrayList<String>();
			for (String imple:implementList)
			{
				String ret="";
				String[] temp = imple.split(":");
				ArrayList<String> parsed = parseImplementHelper(temp[1]);
				for (String pr:parsed)
				{
					ret = temp[0]+":"+pr;
					result.add(ret);
				}
			}		
			return result;	
		}
	
		public ArrayList<String> parseImplementHelper(String temp1)
		{
			ArrayList<String> result = new ArrayList<String>();
			String[] ret = temp1.split(",");
			
			for (int i=0;i<ret.length;i++)
			{
				
				String candidate =trimBracket(ret[i].trim());
				String c1="["+candidate+"]";
				result.add(c1);
			}
			return result;		
		}
		
		public ArrayList<String> buildImplementRelation(ArrayList<String>implementList,
												ArrayList<ClassRelations> allResources)
		{
			ArrayList<String> result = new ArrayList<String>();
			String child=""; String parent="";String ret=";";
			implementList = parseImplementList(implementList);
		
			for (String implement:implementList)
			{
				String[] temp = implement.split(":");
				child=temp[0];
				parent=trimBracket(temp[1]);
				child  = findInallResources(allResources,child);
				parent = findInallResources(allResources,parent);
				ret=parent+"^-.-"+child;
				result.add(ret);
			}
			return result;
		}	 
		


	
		public String trimBracket(String queryRet)
		{
			String temp= queryRet.replace("[", "");
			temp = temp.replace("]", "");
			return temp;	
		}
	
	
	
	
		public ArrayList<String> buildExtendsRelation(ArrayList<String> extendsList,ArrayList<ClassRelations>allResources)
		{
	
			ArrayList<String> result = new ArrayList<String>();
			for (String extend:extendsList)
			{	
				String[]temp =extend.split(":");
				String child = trimBracket(temp[1]);
				child = findInallResources(allResources,child);
				String parent = temp[0];
				parent = findInallResources(allResources,parent);
				String ret =child+"^-"+parent;
				result.add(ret);
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
		public String findInClassNameList(String query,ArrayList<String> classNameList)
		{
			for(String name:classNameList)
			{
				if (query.equals(name))
					return query;
				
			}
			return null;
		}
		
		public String findIninterfaceList(String query,ArrayList<String> interfaceList)
		{
//			System.out.println("^"+query);
			for(String name:interfaceList)
			{
//				System.out.println("^"+name);
				String[] temp = name.split(";");
				String middle = temp[1].replace("]","");
				if (query.equals(middle)&&temp.length>0)
					return name;
			}
			return null;
		}
			
		
		public String buildAllCompartment(String class_name, ArrayList<String>fieldprimitiveUML,ArrayList<String>methodUML,
    					             String constructorUML,ArrayList<String>classNameList,ArrayList<String>interfaceList )
		{
    	   	String allcompartment = "[";
    	   	if (findInClassNameList(class_name,classNameList)!=null)
    	   		allcompartment+=class_name;
    	   	else
    	   		allcompartment+=trimBracket(findIninterfaceList(class_name,interfaceList));
    	   			
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
    		
    		if (methodUML.size()>0 ||!constructorUML.isEmpty())
    		{	allcompartment+='|';
    	   		
    	   		for (int i=0;i<methodUML.size();i++)
    	   		{
    	   			allcompartment+=methodUML.get(i);
    	   			allcompartment+=";";
    	   		}
    	  
    		    allcompartment+=constructorUML;
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
	
	
		public void MethodReadin(ArrayList<String>classNameList,String File,
			ArrayList<String> methodUML,ArrayList<String> methodNonPrimitiveList,
			ArrayList<String> methodbodydependency) throws Exception 			
		{
			FileInputStream in = new FileInputStream(File);
			CompilationUnit cu;

			cu = JavaParser.parse(in);
			Method_Object Method = new Method_Object();
			new MethodVisitor().visit(cu,Method); 
			Class_Object cls = new Class_Object();
			new ClassVisitor().visit(cu,cls);

			Method.setClassName(cls.getName());
			methodbodydependency.addAll(Method.getmethodBodydependency());
			methodUML.addAll(Method.getprimitiveUML());
			ArrayList<String> prononprimitive = Method.getnonprimitiveList();
			prononprimitive.removeAll(Arrays.asList(null,""));
			methodNonPrimitiveList.addAll(prononprimitive);
		
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
