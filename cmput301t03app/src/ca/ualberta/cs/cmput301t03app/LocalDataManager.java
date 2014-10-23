package ca.ualberta.cs.cmput301t03app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	private static final int READ = 1;
	private static final int TO_READ = 2;
	private static final int FAVORITE = 3;
	
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
        	assert isCorrectMode() : "Mode is not set or incorrect mode."; 
        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
        	ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        	objectOutputStream.writeObject(list);
	        fileOutputStream.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*Loads a question list from local storage*/
	@Override
	public ArrayList<Question> load() {

		ArrayList<Question> list = new ArrayList<Question>();
		
		try {
			assert isCorrectMode() : "Mode is not set or incorrect mode.";
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);	
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			list = (ArrayList<Question>) objectInputStream.readObject();
			
		} catch(Exception e) {
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
