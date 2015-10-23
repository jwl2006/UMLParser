package tree.tree;

import java.util.ArrayList;





public class SequenceDiagram {
	public static void main(String[] args) throws Exception 
	{
		
	
 ArrayList<String> ret=new ArrayList<String>();
 ret.add("\\@startuml");
 ret.add("\\Test4 -> m1 : do_e2()");
 ret.add("\\m1 -> S0:e2()");
 ret.add("\\S0 -> m1:set_s2()");
 ret.add("\\@enduml");
 
	}
}
