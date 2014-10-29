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
	private static final String ANSWER_FILE = "answer.sav";
	private static final String POSTED_ANSWER_FILE = "post_answers.sav";
	
	private Context context;
	private String SAVE_FILE; //This will be equal to one of the filenames when user sets a mode

	public LocalDataManager(Context context) {
		this.context = context;
		
	}	
	public void saveFavorites(Question q) {
		SAVE_FILE = FAVORITE_FILE;
		saveQuestion(q);
	}
	
	public void saveRead(Question q) {
		SAVE_FILE = READ_FILE;
		saveQuestion(q);
	}
	
	public void saveToRead(Question q) {
		SAVE_FILE = TO_READ_FILE;
		saveQuestion(q);
	}
	
	public void savePostedQuestions(Question q) {
		SAVE_FILE = POSTED_QUESTIONS_FILE;
		saveQuestion(q);
	}
	
	public void savePostedAnswer(Answer a) {
		SAVE_FILE = POSTED_ANSWER_FILE;
		saveAnswer(a);
	}
	
	public void saveFavoriteAnswers(ArrayList<Answer> list) {
		SAVE_FILE = ANSWER_FILE;
		saveAnswerList(list);
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
	
	public ArrayList<Question> loadPostedQuestions() {
		SAVE_FILE = POSTED_QUESTIONS_FILE;
		ArrayList<Question> list = new ArrayList<Question>();
		list = load();
		return list;
	}
	
	// need to add this method
	
	public ArrayList<Answer> loadPostedAnswers() {
		return null;
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
	
	/**
	 * As per Victor's suggestion, the method name is more specific
	 */
	public void saveQuestion(Question q) {
		 try {
	        	
	        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_APPEND);
	        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
	        	
	        	GsonBuilder builder = new GsonBuilder();
	        	//Gson does not serialize/deserialize dates with milisecond precision unless specified
	        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
	        	builder.serializeNulls(); //Show fields with null values
	        	
	        	gson.toJson(q, outputStreamWriter); //Serialize to Json
	        	outputStreamWriter.flush();
		        outputStreamWriter.close();
		        
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	public void saveAnswer(Answer a) {
		 try {
	        	
	        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_APPEND);
	        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
	        	
	        	GsonBuilder builder = new GsonBuilder();
	        	//Gson does not serialize/deserialize dates with milisecond precision unless specified
	        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
	        	builder.serializeNulls(); //Show fields with null values
	        	
	        	gson.toJson(a, outputStreamWriter); //Serialize to Json
	        	outputStreamWriter.flush();
		        outputStreamWriter.close();
		        
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	@Override
	public ArrayList<Question> loadQuestions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Not implemented correctly right now; need to iterate through the answer id list and save the answer objects.
	 */
	public void saveAnswerList(ArrayList<Answer> list) {
		 try {
	        	
	        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_APPEND);
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
	@Override
	public void saveAnswers(ArrayList<Answer> list) {
		// TODO Auto-generated method stub
		
	}
	
}