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
 * @author ckchan
 * @since 2014-10-28
 */
public class LocalDataManager implements iDataManager{

	//File names
	private static final String READ_FILE = "read.sav";
	private static final String TO_READ_FILE = "read_later.sav";
	private static final String FAVORITE_FILE = "favorite.sav";
	private static final String POSTED_QUESTIONS_FILE = "post_questions.sav";
	private static final String POSTED_ANSWERS_FILE = "post_answers.sav";
	
	private Context context;
	private String SAVE_FILE; //This will be equal to one of the filenames when user sets a mode

	public LocalDataManager(Context context) {
		this.context = context;
		
	}
	
	/**
	 * Saves a list of question id's of favorite questions to cache.
	 * @param list A list of strings containing ID's.
	 */
	public void saveFavorites(ArrayList<String> list) {
		SAVE_FILE = FAVORITE_FILE;
		saveIds(list);
	}
	
	/**
	 * Saves a list of question id's of read questions to cache.
	 * @param list A list of strings containing ID's.
	 */
	public void saveRead(ArrayList<String> list) {
		SAVE_FILE = READ_FILE;
		saveIds(list);
	}

	/**
	 * Saves a list of question id's of questions to be
	 * read later to cache.
	 * @param list A list of strings containing ID's.
	 */
	public void saveToRead(ArrayList<String> list) {
		SAVE_FILE = TO_READ_FILE;
		saveIds(list);
	}
	
	/**
	 * Saves a list of question id's of questions posted by the
	 * user to cache.
	 * @param list A list of strings containing ID's.
	 */
	public void savePostedQuestions(ArrayList<String> list) {
		SAVE_FILE = POSTED_QUESTIONS_FILE;
		saveIds(list);
	}
	
	/**
	 * Saves a list of question id's of questions the user posted
	 * an answer to.
	 * @param list A list of strings containing ID's.
	 */
	public void saveQuestionsOfPostedAnswers(ArrayList<String> list) {
		SAVE_FILE = POSTED_ANSWERS_FILE;
		saveIds(list);
	}
	/**
	 * Saves a list of answers posted by the user to cache.
	 * @param list A list of Answer objects.
	 */
//	public void savePostedAnswers(ArrayList<Answer> list) {
//		SAVE_FILE = POSTED_ANSWERS_FILE;
//		saveAnswers(list);
//	}
	
	/**
	 * Loads a list of question ID's of favorite questions from cache.
	 * @return list A list of strings containing ID's.
	 */
	public ArrayList<String> loadFavorites() {
		SAVE_FILE = FAVORITE_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}
	
	/**
	 * Loads a list of question ID's of read questions from cache.
	 * @return list A list of strings containing ID's.
	 */
	public ArrayList<String> loadRead() {
		SAVE_FILE = READ_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}
	
	/**
	 * Loads a list of question ID's of questions to be
	 * read later from cache.
	 * @return list A list of strings containing ID's.
	 */
	public ArrayList<String> loadToRead() {
		SAVE_FILE = TO_READ_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}
		
	/**
	 * Loads a list of question ID's of questions posted by 
	 * the user from cache.
	 * @return list A list of strings containing ID's.
	 */
	public ArrayList<String> loadPostedQuestions() {
		SAVE_FILE = POSTED_QUESTIONS_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}
	
	/**
	 * Loads a list of question ID's of questions posted by 
	 * the user from cache.
	 * @return list A list of strings containing ID's.
	 */
	public ArrayList<String> loadQuestionsOfPostedAnswers() {
		SAVE_FILE = POSTED_ANSWERS_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}
	
	/**
	 * Loads a list of answers posted by the user from cache.
	 * @return list A list of Question objects.
	 */
//	public ArrayList<Answer> loadPostedAnswers() {
//		SAVE_FILE = POSTED_ANSWERS_FILE;
//		ArrayList<Answer> list = new ArrayList<Answer>();
//		list = loadAnswers();
//		return list;
//	}
	
	
	/**
	 * Saves a general question list to local storage.
	 * This method is called by other methods that specify which
	 * list to save to.
	 * @param list A list of Question objects.
	 */
	@Override
	public void saveQuestions(ArrayList<Question> list) {
		
        try {
        	
        	FileOutputStream fileOutputStream = context.openFileOutput("question_bank.sav", Context.MODE_PRIVATE);
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
	
	private void saveIds(ArrayList<String> list) {
		
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
	 * This method is called by other methods that specify which
	 * list to load from.
	 * @return questionArray A list of Question objects.
	 */
	@Override
	public ArrayList<Question> loadQuestions() {

		ArrayList<Question> questionArray = new ArrayList<Question>();
		
		try {
			
			FileInputStream fileInputStream = context.openFileInput("question_bank.sav");	
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			Type listType = new TypeToken<ArrayList<Question>>(){}.getType();
			GsonBuilder builder = new GsonBuilder();
        	Gson gson = builder.create(); 
        	ArrayList<Question> list = gson.fromJson(inputStreamReader, listType);
        	questionArray = list;
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return questionArray;
		
	}
	
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
	
//	public void saveAnswers(ArrayList<Answer> list) {
//		
//        try {
//        	
//        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
//        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//        	
//        	GsonBuilder builder = new GsonBuilder();
//        	//Gson does not serialize/deserialize dates with milisecond precision unless specified
//        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
//        	builder.serializeNulls(); //Show fields with null values
//        	
//        	gson.toJson(list, outputStreamWriter); //Serialize to Json
//        	outputStreamWriter.flush();
//	        outputStreamWriter.close();
//	        
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public ArrayList<Answer> loadAnswers() {
//		
//		ArrayList<Answer> answerArray = new ArrayList<Answer>();
//		
//		try {
//			
//			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);	
//			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//			Type listType = new TypeToken<ArrayList<Answer>>(){}.getType();
//			GsonBuilder builder = new GsonBuilder();
//			//Gson does not serialize/deserialize dates with milisecond precision unless specified
//        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
//        	ArrayList<Answer> list = gson.fromJson(inputStreamReader, listType);
//        	answerArray = list;
//			
//			
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//		return answerArray;
//	}
}
