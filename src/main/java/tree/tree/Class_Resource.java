package tree.tree;

public class Class_Resource {
	private Field_Object fieldobj;
	private Class_Object classobj;
	private Method_Object methodobj;

	public Field_Object getFieldObj ()
	{
		return fieldobj;
	}
	
	public Class_Object getClassObj ()
	{
		return classobj;
	}
	public Method_Object getMethodObj ()
	{
		return methodobj;
	}
	public void setFieldObj (Field_Object target)
	{
		fieldobj=target;
	}
	public void setClassObj (Class_Object target)
	{
		classobj=target;
	}
	public void setMethodObj (Method_Object target)
	{
		methodobj=target;
	}
	
}
