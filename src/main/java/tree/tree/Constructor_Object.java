package tree.tree;

import java.util.ArrayList;

public class Constructor_Object {
	private String constructorName;
	private String constructorModifier;
	private String constructorParameter;
	private String constructorUML="";
	private String[] primitive = {"byte", "short", "int", "long", "float", "double", "char",
			"boolean", "String"};
	private ArrayList<String> constructornonprimitiveList = new ArrayList<String>();
	
	public String getconstructorUML()
	{
		if (constructorName!=null)
		{
			constructorUML+="+";
			constructorUML+=constructorName;
			constructorUML+="("+parseParameter(constructorParameter)+")";
		}
		return constructorUML;
	}
	
	public ArrayList<String> getnonprimitiveList()
	{
		ArrayList<String> ret = new ArrayList<String>();
		if (constructorParameter!=null)
		{
		String[] par = constructorParameter.split("[,]");
		for (int i=0;i<par.length;i++)
		{
			splitParameters(par[i],ret);
		}
		}
		return ret;
	}

	public void splitParameters(String parameter,ArrayList<String> ret)
	{
		String temp = parameter.trim();
		String[] splited = temp.split(" ");
		if (!isPrimitive(splited[0])||splited[0].equals("\\r"))
		    ret.add(trim(splited[0]));
	}
	
	public boolean isPrimitive(String typename)
	{
		for (String prim:primitive)
		{
			if (typename.contains(prim))
				return true;
		}
		return false;
	}
	
	public String parseParameter(String constructorParameter)
	{
		String all="";
		if (constructorParameter!=null)
		{
			String[] ret = constructorParameter.split("[,]");
			for (int i=0;i<ret.length-1;i++)
			{
				all=ret[i].trim();
				all = trimParameter(all);
				all +=", ";
			}
			String last = ret[ret.length-1].trim();
			all+=trimParameter(last);
		}
		return all;
	}
	
	public String trimParameter(String param)
	{
		String[] ret = param.split(" ");
		String result="";
		for (int i=ret.length-1;i>0;i--){
			result+=trim(ret[i]);
			result+=":";
		}
		result+=trim(ret[0]);
		return result;	
	}
	
	public String trim(String target)
	{
		String temp=target.replace("[","");
		temp =temp.replace("]","");
		return temp;
	}
	
	public String getName()
	{
		return constructorName;
	}
	
	public String getModifier()
	{
		return constructorModifier;
	}
	public String getParameter()
	{
		return constructorParameter;
	}
	
	public void setParameter(String target)
	{
		constructorParameter=target;
	}
	
	public void setName(String target)
	{
		constructorName=target;
	}
	
	public void setModifier(int target)
	{
		constructorModifier=Integer.toString(target);
	}
	

}
