package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public interface iDataManager {


	public void save(ArrayList<Question> list);
	public ArrayList<Question> load();


}