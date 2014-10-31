package ca.ualberta.cs.cmput301t03app;

import java.io.File;
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
 * This class manages the saving and loading of an array list of Questions to local storage
 * Read questions, questions the user wants to read later, and favorite questions
 * will be saved to local storage.
 * 
 * @author ckchan
 * @since 2014-10-28
 *  
 *  
 */

public class LocalDataManager implements iDataManager{
	//File names
	private static final String READ_FILE = "read.sav";
	private static final String TO_READ_FILE = "read_later.sav";
	private static final String FAVORITE_FILE = "favorite.sav";
	private static final String POSTED_QUESTIONS_FILE = "post_questions.sav";
	private static final String POSTED_ANSWERS_FILE = "post_answers.sav";
	private static final String QUESTION_BANK = "question_bank.sav";
	private static final String ANSWER_BANK = "answer_bank.sav"; // This is required and use to save the posted answers ONLY.
	private static final String FAVORITE_ANSWERS_ID = "favorite_answer.sav";

	private Context context;
	private String SAVE_FILE; //This will be equal to one of the filenames 

	public LocalDataManager(Context context) {
		this.context = context;
	}
	
	/***********************************SAVE METHODS**************************************/
	
	/**
	 * Saves a list of question id's of favorite questions to cache.
	 * @param UUID
	 */
	public void saveFavoritesID(String id) {
		SAVE_FILE = FAVORITE_FILE;
		saveIds(id);
	}
	
	/**
	 * Saves a list of question id's of read questions to cache.
	 * @param UUID
	 */
	public void saveReadID(String id) {
		SAVE_FILE = READ_FILE;
		saveIds(id);
	}

	/**
	 * Saves a list of question id's of questions to be
	 * read later to cache.
	 * @param UUID
	 */
	public void saveToReadID(String id) {
		SAVE_FILE = TO_READ_FILE;
		saveIds(id);
	}

	/**
	 * Saves a list of question id's of questions posted by the
	 * user to cache.
	 * @param UUID
	 */
	public void savePostedQuestionsID(String id) {
		SAVE_FILE = POSTED_QUESTIONS_FILE;
		saveIds(id);
	}

	// These two methods set the same mode!
	
	/**
	 * Saves a list of question id's of questions the user posted
	 * an answer to.
	 * @param UUID
	 * 
	 * 
	 * Going to comment this one out! -- Eric
	 * 
	public void saveQuestionsOfPostedAnswers(ArrayList<String> list) {
		SAVE_FILE = POSTED_ANSWERS_FILE;
		saveIds(list);
	}
	*/

	/**
	 * Saves a list of answers posted by the user to cache.
	 * @param UUID
	 *
	 */
	public void savePostedAnswersID(String id) {
		SAVE_FILE = POSTED_ANSWERS_FILE;
		saveIds(id);
	}
	
	/**
	 * Saves the answer ID.  This should only be needed if we're saving answers independent of questions.
	 * @param UUID
	 */
	public void saveFavoriteAnswerID(String id) {
		SAVE_FILE = FAVORITE_ANSWERS_ID;
		saveIds(id);
	}
	
	/**
	 * Saves a single question to the bank of questions.
	 * @param Question object
	 */
	public void appendToQuestionBank(Question q) {
		SAVE_FILE = QUESTION_BANK;
		saveQuestion(q);
	}
	
	/**
	 * Saves
	 * @param list
	 */
	public void saveToQuestionBank(ArrayList<Question> list) {
		SAVE_FILE = QUESTION_BANK;
		saveQuestions(list);
	}
	
	/**
	 * Saves an answer to the bank of answers.
	 * @param Answer object
	 */
	public void saveToAnswerBank(Answer a) {
		SAVE_FILE = ANSWER_BANK;
		saveAnswer(a);
	}
	
	/**
	 * Appends a single Answer object to the answer bank.
	 * @param a An Answer object.
	 */
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

	/**
	 * Save a list of Answer objects to the answer bank.
	 * @param list A list of Answer Objects.
	 */
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
		
	
	/***********************************LOAD METHODS**************************************/
	
	
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
//	public ArrayList<String> loadQuestionsOfPostedAnswers() {
//		SAVE_FILE = POSTED_ANSWERS_FILE;
//		ArrayList<String> list = new ArrayList<String>();
//		list = loadIds();
//		return list;
//	}

	/**
	 * Loads a list of answers posted by the user from cache.
	 * @return list A list of strings containing ID's.
	 */
	public ArrayList<String> loadPostedAnswers() {
		SAVE_FILE = POSTED_ANSWERS_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}

	/**
	 * Returns a list of Question objects from the question bank.
	 * @return questionArray A list of Question objects.
	 */
	@Override
	public ArrayList<Question> loadQuestions() {
		ArrayList<Question> questionArray = new ArrayList<Question>();
		try {		
			FileInputStream fileInputStream = context.openFileInput(QUESTION_BANK);	
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			Type listType = new TypeToken<ArrayList<Question>>(){}.getType();
			GsonBuilder builder = new GsonBuilder();
			
        	//Gson does not serialize/deserialize dates with milisecond precision unless specified
        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        	builder.serializeNulls(); //Show fields with null values
 
        	ArrayList<Question> list = gson.fromJson(inputStreamReader, listType);
        	questionArray = list;		
		} catch(IOException e) {
			e.printStackTrace();
		}
		return questionArray;
	}

	/**
	 * Returns a list of Answer objects from the answer bank.
	 * @return answerArray A list of Answer objects.
	 */
	public ArrayList<Answer> loadAnswers() {
		ArrayList<Answer> answerArray = new ArrayList<Answer>();
		try {			
	
				FileInputStream fileInputStream = context.openFileInput(ANSWER_BANK);	
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
				Type listType = new TypeToken<ArrayList<Answer>>(){}.getType();
				GsonBuilder builder = new GsonBuilder();
	
				//Gson does not serialize/deserialize dates with milisecond precision unless specified
	        	Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
	        	builder.serializeNulls(); //Show fields with null values
	        	ArrayList<Answer> list = gson.fromJson(inputStreamReader, listType);
	        	answerArray = list;
			} catch(IOException e) {
				e.printStackTrace();
			}
		return answerArray;
	}

	
	
	/***********************************PRIVATE METHODS*****************************************/
	
	
	private void saveIds(String id) {
        try {
        	FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_APPEND);
        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);      	
        	GsonBuilder builder = new GsonBuilder();
        	Gson gson = builder.create();
        	gson.toJson(id, outputStreamWriter); //Serialize to Json
        	outputStreamWriter.flush();
	        outputStreamWriter.close();        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Appends a single Question object to the question bank.
	 * @param q A Question object
	 */
	private void saveQuestion(Question q) {
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
	
	/**
	 * Saves a list of Question objects to the question bank.
	 * @param list A list of Question objects.
	 * 
	 */
	private void saveQuestions(ArrayList<Question> list) {
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
}