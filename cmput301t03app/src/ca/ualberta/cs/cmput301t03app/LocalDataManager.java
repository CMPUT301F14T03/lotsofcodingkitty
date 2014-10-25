package ca.ualberta.cs.cmput301t03app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

/*This class manages the saving and loading of an arraylist of Questions to local storage
 * Read questions, questions the user wants to read later, and favorite questions
 * will be saved to local storage.*/

public class LocalDataManager implements iDataManager{

	//File names
	private static final String READ_FILE = "read.sav";
	private static final String TO_READ_FILE = "read_later.sav";
	private static final String FAVORITE_FILE = "favorite.sav";
	
	private Context context;
	private String SAVE_FILE; //This will be equal to one of the filenames when user sets a mode
	private int mode = 0;

	public LocalDataManager(Context context) {
		this.context = context;
		
	}
	
	public void saveFavorites(ArrayList<Question> list) {
		SAVE_FILE = FAVORITE_FILE;
		save(list);
	}
	
	public void saveRead(ArrayList<Question> list) {
		SAVE_FILE = READ_FILE;
		save(list);
	}
	
	public void saveToRead(ArrayList<Question> list) {
		SAVE_FILE = TO_READ_FILE;
		save(list);
	}
	
	public ArrayList<Question> loadFavorites() {
		SAVE_FILE = FAVORITE_FILE;
		ArrayList<Question> list = new ArrayList<Question>();
		list = load();
		return list;
	}
	
	public ArrayList<Question> loadRead() {
		SAVE_FILE = READ_FILE;
		ArrayList<Question> list = new ArrayList<Question>();
		list = load();
		return list;
	}
	
	public ArrayList<Question> loadToRead() {
		SAVE_FILE = TO_READ_FILE;
		ArrayList<Question> list = new ArrayList<Question>();
		list = load();
		return list;
	}
	
	/*Saves a general question list to local storage*/
	@Override
	public void save(ArrayList<Question> list) {
		
        try {
        	
        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
        	OutputStreamWriter objectStreamWriter = new OutputStreamWriter(fileOutputStream);
        	
        	GsonBuilder builder = new GsonBuilder();
        	Gson gson = builder.create();
        	builder.serializeNulls(); //Show fields with null values
        	
        	gson.toJson(list, objectStreamWriter); //Serialize to Json
        	fileOutputStream.flush();
	        fileOutputStream.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*Loads a general question list from local storage.
	 * In other words, this method does NOT put the retrieved
	 * Question objects into its corresponding question list.*/
	@Override
	public ArrayList<Question> load() {

		ArrayList<Question> list = new ArrayList<Question>();
		
		try {
			
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);	
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			Type listType = new TypeToken<ArrayList<Question>>(){}.getType();
			list = new Gson().fromJson(inputStreamReader, listType);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
}
