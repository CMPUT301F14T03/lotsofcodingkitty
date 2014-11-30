//package ca.ualberta.cs.cmput301t03app.incomplete;
//package ca.ualberta.cs.cmput301t03app.controllers;
//
//import java.util.ArrayList;
//
//import ca.ualberta.cs.cmput301t03app.models.Comment;
//import ca.ualberta.cs.cmput301t03app.models.Tuple;
//
//
//public class OffLineTracker
//{
//	static ArrayList<Tuple> offLine=null;
//	
//	public OffLineTracker(){}
//	
//	public ArrayList<Tuple> getOffLineInstance(){
//		if (this.offLine==null){
//			offLine=new ArrayList<Tuple>();
//			}
//		return this.offLine;
//	}
//	
//	public void addTuple(String qId,String aId,Comment comment){
//		getOffLineInstance().add(new Tuple(qId,aId,comment));
//	}
//	
//	public void clearOffLine(){
//		getOffLineInstance().clear();
//	}
//	public int getOffLineSize(){
//		return getOffLineInstance().size();
//	}
//	
//	
//	
//}
