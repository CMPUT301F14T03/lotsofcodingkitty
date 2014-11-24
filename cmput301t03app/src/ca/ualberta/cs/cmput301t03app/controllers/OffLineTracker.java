package ca.ualberta.cs.cmput301t03app.controllers;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.tuple;


public class OffLineTracker
{
	static ArrayList<tuple> offLine=null;
	
	public OffLineTracker(){}
	
	public ArrayList<tuple> getOffLineInstance(){
		if (this.offLine==null){
			offLine=new ArrayList<tuple>();
			}
		return this.offLine;
	}
	
	public void addTuple(String qId,String aId,Comment comment){
		getOffLineInstance().add(new tuple(qId,aId,comment));
	}
	
	public void clearOffLine(){
		getOffLineInstance().clear();
	}
	public int getOffLineSize(){
		return getOffLineInstance().size();
	}
	
	
	
}
