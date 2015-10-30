package tree.tree;

import java.util.ArrayList;

public class ClassRelations {
		ArrayList<String> fieldprimitiveUML = new ArrayList<String>();
		ArrayList<String> fieldnonprimitiveUML = new ArrayList<String>();
		ArrayList<String> fieldnonprimitiveList = new ArrayList<String>();
		ArrayList<String> methodUML = new ArrayList<String>();
		ArrayList<String> methodNonPrimitiveList = new ArrayList<String>();
		ArrayList<String> constructorNonPrimitiveList = new ArrayList<String>();
		String allCompartment=""; String class_name=""; String constructorUML="";
		public ArrayList<String> getfieldprimitiveUML()
		{
			return fieldprimitiveUML ;
		}
		
		public String getconstructorUML()
		{
			return constructorUML ;
		}
		
		public void setconstructorUML(String target)
		{
			constructorUML=target ;
		}
		
		
		public void setfieldprimitiveUML(ArrayList<String> target)
		{
			fieldprimitiveUML=target;
		}
		
	
		public ArrayList<String> getfieldnonprimitiveUML()
		{
			return fieldnonprimitiveUML;
		}
	
		
		public void setfieldnonprimitiveUML (ArrayList<String> target)
		{
			fieldnonprimitiveUML=target;
		}
		
		
		
		public ArrayList<String> getfieldnonprimitiveList()
		{
			return fieldnonprimitiveList;
		}
		
		public void setfieldnonprimitiveList (ArrayList<String> target)
		{
			fieldnonprimitiveList=target;
		}
		
		
		
	
		public ArrayList<String>getmethodUML()
		{
			return methodUML;
		}
	
		public void setmethodUML (ArrayList<String> target)
		{
			methodUML=target;
		}
		
		
		
		
		public ArrayList<String>getmethodNonPrimitiveList()
		{
			return methodNonPrimitiveList;
		}
		
		public void setmethodNonPrimitiveList (ArrayList<String> target)
		{
			methodNonPrimitiveList=target;
		}
		
		
		
	
		public ArrayList<String>getconstructorNonPrimitiveList ()
		{
			return constructorNonPrimitiveList;
		}
	
		public void setconstructorNonPrimitiveList (ArrayList<String> target)
		{
			constructorNonPrimitiveList=target;
		}
		
		
		
		
		
		
		
		
		public String getallCompartment ()
		{
			return allCompartment;
		}
	
		public String getclass_name ()
		{
			return class_name;
		}
		
		public void setclass_name (String target)
		{
			class_name=target;
		}
		
		public void setallCompartment (String target)
		{
			allCompartment=target;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}