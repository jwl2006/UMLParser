package tree.tree;

public class Class_Object {
	private String class_name=null;
	private String class_extends=null;
	private String class_implements=null;
	private String interface_name=null;
	public String getName()
	{
		return class_name;
	}
	
	public String getInterface()
	{
		return interface_name;
	}
	
	public String getExtends()
	{
		return class_extends;
	}
	
	public String getImplements()
	{
		return class_implements;
	}
	
	public void setName(String target)
	{
	    class_name=target;
	}
	
	public void setExtends(String target)
	{
	    class_extends=target;
	}
	
	public void setImplements(String target)
	{
	    class_implements=target;
	}
	
	public void setInterface(String target)
	{
	    interface_name=target;
	}
	

	
	
}
