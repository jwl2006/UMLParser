package tree.tree;

import java.util.ArrayList;

public class Method_Object {
	private ArrayList<String> methodName = new ArrayList<String>();
	private ArrayList<String> methodType = new ArrayList<String>();
	private ArrayList<String> methodModifier = new ArrayList<String>();
	private ArrayList<String> methodParameters = new ArrayList<String>();
	private ArrayList<String> methodprimitiveUML = new ArrayList<String>();
	private ArrayList<String> parameterList = new ArrayList<String>();
	private ArrayList<String> methodPrimitiveList =new ArrayList<String>();
	private ArrayList<String> methodNonPrimitiveList =new ArrayList<String>();
	
	
	private String className;
	private String[] primitive = {"byte", "short", "int", "long", "float", "double", "char",
			"boolean", "String"};
	
	public boolean isPrimitive(String typename)
	{
		for (String prim:primitive)
		{
			if (typename.contains(prim))
				return true;
		}
		return false;
	}
	public ArrayList<String> getParameterList()
	{
		return parameterList;
	}
	
	public ArrayList<String> getnonprimitiveList()
	{
		ArrayList<String> ret = new ArrayList<String>();
		for (String param: methodParameters )
		{
		//	System.out.println(param);
			String[] par = param.split("[,]");
			for (int i=0;i<par.length;i++)
			{
				splitParameters(par[i],ret);
			}
		}
		
		return ret;
	}
	
	public ArrayList<String> getmethodparameters()
	{
		return methodParameters;
	}
	
	public boolean collectionCheck(String type)
	{
		if (type.contains("Collection"))
			return true;
		return false;
	}
	
	public boolean arrayCheck(String type)
	{
		boolean ret = false;
		if (type.contains("[]"))
			ret=true; 
		return ret;	
	}
	
	
	public void splitParameters(String parameter,ArrayList<String> ret)
	{
		String temp = parameter.trim();
		String[] splited = temp.split(" ");
		if (!isPrimitive(splited[0])||splited[0].equals("\\r"))
		    ret.add(splited[0]);
	}
	

	public String parseSingleParameter (String param)
	{
		String[] par = param.split("[,]");
		String all="";
		
		for (int i=0;i<par.length-1;i++)
		{
			all=par[i].trim();
			all = trimParameter(all);
			all +=", ";
		}
		String last = par[par.length-1].trim();
		all+=trimParameter(last);
		
		return all;
	}
	
	public String trimParameter(String param)
	{
		String[] ret = param.split(" ");
		String result="";
		for (int i=ret.length-1;i>0;i--){
			result+=ret[i];
			result+=":";
		}
		result+=ret[0];
		return result;	
	}
	
	public ArrayList<String> getprimitiveUML()
	{
	
	    for (int i=0;i<methodParameters.size();i++)
	    {
	    	String temp = methodParameters.get(i);
	    	String parameter = parseSingleParameter(temp);
	    //	System.out.println(parameter);
	    	parameterList.add(parameter);
	    	String ret = "";
	    	if (methodModifier.get(i).equals("1"))
	    	{
	    		ret+="+";
	    		ret+=methodName.get(i);
	    		ret+="("+parameterList.get(i)+")";
	    		ret+=":";
	    		ret+=methodType.get(i);
	    	}
	    //	System.out.println(ret);
	    	methodprimitiveUML.add(ret);	
	    }	
		return methodprimitiveUML;
	}
	
	
	public void setClassName(String target)
	{
			className = target;
	}
	public String getClassName()
	{
		return className;
	}
	public ArrayList<String> getMethodName()
	{
		return methodName;
	}
	public ArrayList<String> getMethodType()
	{
		return methodType;
	}
	public ArrayList<String> getMethodModifier()
	{
		return methodModifier;
	}
	
	public ArrayList<String> getMethodParameters()
	{
		return methodParameters;
	}

	
	
	public void setName(String target)
	{
	
		methodName.add(target);	 //Public Methods (ignore private, package and protected scope)
	}
	public void setType(String target)
	{
		methodType.add(trim(target));	
	}
	public void setModifier(Integer target)
	{
		
		methodModifier.add(trim(Integer.toString(target)));	
	}
	
	public void setParameters(String target)
	{
		methodParameters.add(trim(target));
	}
	
	public String trim(String target)
	{
		String temp=target.replace("[","");
		temp =temp.replace("]","");
		return temp;
	}
	
}
