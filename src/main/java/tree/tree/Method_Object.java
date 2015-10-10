package tree.tree;

import java.util.ArrayList;

public class Method_Object {
	private ArrayList<String> methodName = new ArrayList<String>();
	private ArrayList<String> methodType = new ArrayList<String>();
	private ArrayList<String> methodModifier = new ArrayList<String>();

	private ArrayList<String> methodParameters = new ArrayList<String>();
	
	public ArrayList<String> getName()
	{
		return methodName;
	}
	public ArrayList<String> getType()
	{
		return methodType;
	}
	public ArrayList<String> getModifier()
	{
		return methodModifier;
	}
	
	public ArrayList<String> getParameters()
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
