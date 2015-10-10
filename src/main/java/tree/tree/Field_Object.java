package tree.tree;

import java.util.ArrayList;

//import java.util.ArrayList;

public class Field_Object {

	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> type = new ArrayList<String>();
	private ArrayList<String> modifier = new ArrayList<String>();
	
	
	public ArrayList<String> getName()
	{
		return name;
	}
	public ArrayList<String> getType()
	{
		return type;
	}
	public ArrayList<String> getModifier()
	{
		return modifier;
	}
	public void setName(String target)
	{
		name.add(target);	
	}
	public void setType(String target)
	{
		type.add(target);	
	}
	public void setModifier(Integer target)
	{
		
		modifier.add(Integer.toString(target));	
	}
	
	public String nameReplace(String target)
	{
		String temp=target.replace("[","");
		temp =temp.replace("]","");
		return temp;
	}
    public ArrayList<ArrayList<String>> parseToken ()
    {
    	ArrayList<String> field_nameList = this.getName();
        ArrayList<String> field_typeList = this.getType();
        ArrayList<String> field_modifierList = this.getModifier();
        ArrayList<ArrayList<String>> tokenList = new ArrayList<ArrayList<String>>();
        for (int i=0;i<field_nameList.size();i++)
        {
        	ArrayList<String> temp = new ArrayList<String>();
        	temp.add(accessReplace(field_modifierList.get(i)));
        	temp.add(arrayReplace(field_typeList.get(i)));
        	temp.add(nameReplace(field_nameList.get(i)));
        	tokenList.add(temp);
        }
       return tokenList;  	
    }
    
    public String accessReplace(String modifier)
    {
    	String temp="";
    	if (modifier.equals("2"))
    	    temp = "-";
    	else if (modifier.equals("1"))
    	    temp = "+";
    	return temp;
    }
	
	public boolean arrayCheck(String type)
	{
		boolean ret = false;
		if (type.contains("[]"))
			ret=true; 
		return ret;	
	}
	
	public String arrayReplace(String type)
	{
		String ret = type;
		if (arrayCheck(type)==true)
			ret = type.replace("[]", "(*)");
		return ret;	
	}
	
	public void print_fieldObj( ArrayList<ArrayList<String>> finalList)
	{     
		for (int i=0;i<finalList.size();i++)
		{
    	ArrayList<String> temp=finalList.get(i);
    	for (Integer j=0;j<temp.size();++j)
    	    System.out.println("++"+temp.get(j));
		}	
	}
	public String collectionParse(String type)
	{
		String ret="";
		if(collectionCheck(type))
		{
			ret = type.replace("Collection", "");
			ret = ret.replace("<", "");
			ret = ret.replace(">", "");
			return ret;
		}
		else if (arrayCheck(type))
		{
			ret =arrayReplace(type);
			return ret;
		}
		else
			return type;	
	}
	
	public boolean collectionCheck(String type)
	{
		if (type.contains("Collection"))
			return true;
		return false;
	}
	
}
