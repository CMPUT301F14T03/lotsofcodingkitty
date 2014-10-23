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
	
	//File modes
	public static final int READ = 1;
	public static final int TO_READ = 2;
	public static final int FAVORITE = 3;
	
	private Context context;
	private Integer validModes[] = {READ, TO_READ, FAVORITE};
	private String SAVE_FILE; //This will be equal to one of the filenames when user sets a mode
	private int mode = 0;
	
	public LocalDataManager(Context context) {
		this.context = context;
		
	}
	
	/*This sets the mode to indicate which file to save to*/
	public void setMode(int mode) {
		this.mode = mode;
		
		switch (mode) {
			case READ:
				SAVE_FILE = READ_FILE;
			case TO_READ:
				SAVE_FILE = TO_READ_FILE;
			case FAVORITE:
				SAVE_FILE = FAVORITE_FILE;
		}
	}
	
	/*Saves a question list to local storage*/
	@Override
	public void save(ArrayList<Question> list) {
		
        try {
        	//Check for correct mode
        	if (!isCorrectMode()) {
        		throw new IllegalStateException("Invalid mode set or not set at all.");
        	}
        	
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
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	/*Loads a general question list from local storage.
	 * In other words, this method does NOT put the retrieved
	 * Question objects into its corresponding static question list.*/
	@Override
	public ArrayList<Question> load() {

		ArrayList<Question> list = new ArrayList<Question>();
		
		try {
			//Check for correct mode
        	if (!isCorrectMode()) {
        		throw new IllegalStateException("Invalid mode set or not set at all.");
        	}
			
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);	
			InputStreamReader inputStreamRedaer = new InputStreamReader(fileInputStream);
			Type listType = new TypeToken<ArrayList<Question>>(){}.getType();
			list = new Gson().fromJson(inputStreamRedaer, listType);
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	/*Checks to see if a correct mode was set or even set at all*/
	private boolean isCorrectMode() {
		Set<Integer> set = new HashSet<Integer>(Arrays.asList(validModes));
		
		if (set.contains(mode)) {
			return true;
		} else {
			return false;
		}	
	}
}
