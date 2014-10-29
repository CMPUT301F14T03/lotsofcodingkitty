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

/**
 * This class manages the saving and loading of an arraylist of Questions to local storage
 * Read questions, questions the user wants to read later, and favorite questions
 * will be saved to local storage.
 * 
 * @author 
 */
public class LocalDataManager implements iDataManager{

	//File names
	private static final String READ_FILE = "read.sav";
	private static final String TO_READ_FILE = "read_later.sav";
	private static final String FAVORITE_FILE = "favorite.sav";
	private static final String POSTED_QUESTIONS_FILE = "post_questions.sav";
	
	private Context context;
	private String SAVE_FILE; //This will be equal to one of the filenames when user sets a mode

	public LocalDataManager(Context context) {
		this.context = context;
		
	}
<<<<<<< HEAD
	/**
	 * Saves a list of question id's of favorite questions to cache.
	 * @param list A list of strings containing ID's.
	 */
	public void saveFavorites(Question q) {
		SAVE_FILE = FAVORITE_FILE;
		saveIds(q);
=======
	
	public void saveFavorites(ArrayList<Question> list) {
		SAVE_FILE = FAVORITE_FILE;
		save(list);
>>>>>>> upstream/master
	}
	
	public void saveRead(ArrayList<Question> list) {
		SAVE_FILE = READ_FILE;
		save(list);
	}
	
	public void saveToRead(ArrayList<Question> list) {
		SAVE_FILE = TO_READ_FILE;
		save(list);
	}
	
	public void savePostedQuestions(ArrayList<Question> list) {
		SAVE_FILE = POSTED_QUESTIONS_FILE;
		save(list);
	}
<<<<<<< HEAD
	/**
	 * Saves a list of answers posted by the user to cache.
	 * @param list A list of Answer objects.
	 */
	public void savePostedAnswers(ArrayList<Answer> list) {
		SAVE_FILE = POSTED_ANSWERS_FILE;
		saveAnswers(list);
	}
=======
>>>>>>> upstream/master
	
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
	
	public ArrayList<Question> loadPostedQuestions() {
		SAVE_FILE = POSTED_QUESTIONS_FILE;
		ArrayList<Question> list = new ArrayList<Question>();
		list = load();
		return list;
	}
	
	// need to add this method
	
<<<<<<< HEAD
	/**
	 * Loads a list of answers posted by the user from cache.
	 * @return list A list of Question objects.
	 */
	public ArrayList<String> loadPostedAnswers() {
		SAVE_FILE = POSTED_ANSWERS_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
=======
	public ArrayList<Answer> loadPostedAnswers() {
		return null;
>>>>>>> upstream/master
	}
	
	
	/**
	 * Saves a general question list to local storage.
	 * 
	 * @param list 
	 */
	@Override
	public void save(ArrayList<Question> list) {
		
        try {
        	
        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        	
        	GsonBuilder builder = new GsonBuilder();
        	//Gson does not serialize/deserialize dates with milisecond precision unless specified
        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        	builder.serializeNulls(); //Show fields with null values
        	
        	gson.toJson(list, outputStreamWriter); //Serialize to Json
        	outputStreamWriter.flush();
	        outputStreamWriter.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a general question list from local storage.
	 * In other words, this method does NOT put the retrieved
	 * Question objects into its corresponding question list.
	 * @return 
	 */
	@Override
	public ArrayList<Question> load() {

		ArrayList<Question> questionArray = new ArrayList<Question>();
		
		try {
			
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);	
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			Type listType = new TypeToken<ArrayList<Question>>(){}.getType();
			GsonBuilder builder = new GsonBuilder();
			//Gson does not serialize/deserialize dates with milisecond precision unless specified
        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
        	ArrayList<Question> list = gson.fromJson(inputStreamReader, listType);
        	questionArray = list;
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return questionArray;
		
	}
	
<<<<<<< HEAD
	private ArrayList<String> loadIds() {
		
		ArrayList<String> idArray = new ArrayList<String>();
		
		try {
			
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);	
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			Type listType = new TypeToken<ArrayList<String>>(){}.getType();
			GsonBuilder builder = new GsonBuilder();
        	Gson gson = builder.create(); 
        	ArrayList<String> list = gson.fromJson(inputStreamReader, listType);
        	idArray = list;
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return idArray;
	}
	
	public void saveAnswers(ArrayList<Answer> list) {
		
        try {
        	
        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        	
        	GsonBuilder builder = new GsonBuilder();
        	//Gson does not serialize/deserialize dates with milisecond precision unless specified
        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        	builder.serializeNulls(); //Show fields with null values
        	
        	gson.toJson(list, outputStreamWriter); //Serialize to Json
        	outputStreamWriter.flush();
	        outputStreamWriter.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Answer> loadAnswers() {
		
		ArrayList<Answer> answerArray = new ArrayList<Answer>();
		
	try {
			
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);	
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			Type listType = new TypeToken<ArrayList<Answer>>(){}.getType();
			GsonBuilder builder = new GsonBuilder();
			//Gson does not serialize/deserialize dates with milisecond precision unless specified
        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
        	ArrayList<Answer> list = gson.fromJson(inputStreamReader, listType);
        	answerArray = list;
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return answerArray;
	}
}
=======
}
>>>>>>> upstream/master
