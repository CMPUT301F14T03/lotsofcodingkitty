package ca.ualberta.cs.cmput301t03app.datamanagers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import ca.ualberta.cs.cmput301t03app.interfaces.iDataManager;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.Post;
import ca.ualberta.cs.cmput301t03app.models.UpvoteTuple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Manages the saving and loading of an array list of Questions to
 * local storage.<p> Previously read questions, questions the user wants to read later and
 * favorited questions will be saved to local storage.
 * 
 * 
 */

public class LocalDataManager implements iDataManager {
	
	// File names
	private static final String READ_FILE = "read.sav";
	private static final String TO_READ_FILE = "read_later.sav";
	private static final String FAVORITE_FILE = "favorite.sav";
	private static final String POSTED_QUESTIONS_FILE = "post_questions.sav";
	private static final String QUESTION_BANK = "question_bank.sav";
	private static final String QUESTION_UPVOTES = "question_upvotes.sav";
	private static final String ANSWER_UPVOTES = "answer_upvotes.sav";
	private static final String PUSH_POSTS = "push_posts.sav";
	// private static final String POSTED_ANSWERS_FILE = "post_answers.sav";
	// private static final String ANSWER_BANK = "answer_bank.sav"; // This is
	// required and use to save the posted answers ONLY.
	// private static final String FAVORITE_ANSWERS_ID = "favorite_answer.sav";

	private Context context;
	private String SAVE_FILE; // This will be equal to one of the filenames
	
	/**
	 * 
	 * Constructs a {@link #LocalDataManager() LocalDataManager}
	 * @param context
	 *            The context of the LocalDataManager
	 */
	public LocalDataManager(Context context) {

		this.context = context;
	}

	/*********************************** SAVE METHODS **************************************/

	
	/**
	 * New methods related to saving things that need to be pushed to server locally.
	 * @param posts
	 */
	
	public void savePushPosts(ArrayList<Post> posts) {
		SAVE_FILE = PUSH_POSTS;
		saveFilePushPosts(posts);
	}
	
	public void savePushQuestionUpvotes(HashMap<String, Integer> upvotes){
		SAVE_FILE = QUESTION_UPVOTES;
		saveFileQuestionUpvotes(upvotes);
	}
	
	public void savePushAnswerUpvotes(HashMap<String, UpvoteTuple> upvotes) {
		SAVE_FILE = ANSWER_UPVOTES;
		saveFileAnswerUpvotes(upvotes);
		
	}
	/**
	 * Saves a list of question IDs of favorite questions to cache.
	 * 
	 * @param idList
	 *            A list of strings pertaining to question IDs
	 */
	public void saveFavoritesID(ArrayList<String> idList) {

		SAVE_FILE = FAVORITE_FILE;
		saveIds(idList);
	}

	/**
	 * Saves a list of question IDs of read questions to cache.
	 * 
	 * @param idList
	 *              A list of strings pertaining to question IDs
	 */
	public void saveReadID(ArrayList<String> idList) {

		SAVE_FILE = READ_FILE;
		saveIds(idList);
	}

	/**
	 * Saves a list of question IDs of questions to be read later to cache.
	 * 
	 * @param idList
	 *            A list of strings pertaining to question IDs
	 */
	public void saveToReadID(ArrayList<String> idList)
	{

		SAVE_FILE = TO_READ_FILE;
		saveIds(idList);
	}

	/**
	 * Saves a list of question IDs of questions posted by the user to cache.
	 * 
	 * @param idList
	 *            A list of strings pertaining to question IDs
	 */
	public void savePostedQuestionsID(ArrayList<String> idList)
	{

		SAVE_FILE = POSTED_QUESTIONS_FILE;
		saveIds(idList);
	}

	/**
	 * Saves a list of Question Objects to the local memory
	 * 
	 * @param list
	 *            A list of Question Objects 
	 */
	public void saveToQuestionBank(ArrayList<Question> list)
	{

		SAVE_FILE = QUESTION_BANK;
		saveQuestions(list);
	}


	/**
	 * New methods for loading posts and upvotes that need to be pushed
	 * @return
	 */
	
	public ArrayList<Post> loadPosts() {
		SAVE_FILE = PUSH_POSTS;
		ArrayList<Post> posts = loadPushPosts();
		return posts;
		
	}
	
	public HashMap<String, Integer> loadQuestionUpvotes() {
		SAVE_FILE = QUESTION_UPVOTES;
		HashMap<String, Integer> upvotes = loadFileQuestionUpvotes();
		return upvotes;
		
	}
	
	public HashMap<String, UpvoteTuple> loadAnswerUpvotes() {
		SAVE_FILE = ANSWER_UPVOTES;
		HashMap<String, UpvoteTuple> upvotes = loadFileAnswerUpvotes();
		return upvotes;
		
	}
	
	
	/**
	 * Loads a list of question ID's of favorite questions from cache.
	 * 
	 * @return  A list of strings pertaining to question IDs
	 */
	public ArrayList<String> loadFavorites()
	{

		SAVE_FILE = FAVORITE_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}

	/**
	 * Loads a list of question ID's of read questions from cache.
	 * 
	 * @return A list of strings pertaining to question IDs
	 */
	public ArrayList<String> loadRead()
	{

		SAVE_FILE = READ_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}

	/**
	 * Loads a list of question ID's of questions to be read later from cache.
	 * 
	 * @return A list of strings pertaining to question IDs
	 */
	public ArrayList<String> loadToRead()
	{

		SAVE_FILE = TO_READ_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}

	/**
	 * Loads a list of question ID's of questions posted by the user from cache.
	 * 
	 * @return A list of strings pertaining to question IDs
	 */
	public ArrayList<String> loadPostedQuestions()
	{

		SAVE_FILE = POSTED_QUESTIONS_FILE;
		ArrayList<String> list = new ArrayList<String>();
		list = loadIds();
		return list;
	}

	/**
	 * Returns a list of Question objects from the question bank.
	 * 
	 * @return A list of Question objects.
	 */
	@Override
	public ArrayList<Question> loadQuestions()
	{

		ArrayList<Question> questionArray = new ArrayList<Question>();
		try
		{
			FileInputStream fileInputStream = context
					.openFileInput(QUESTION_BANK);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream);
			Type listType = new TypeToken<ArrayList<Question>>()
			{
			}.getType();
			GsonBuilder builder = new GsonBuilder();

			// Gson does not serialize/deserialize dates with milisecond
			// precision unless specified
			Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.create();
			builder.serializeNulls(); // Show fields with null values

			ArrayList<Question> list = gson.fromJson(inputStreamReader,
					listType);
			questionArray = list;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return questionArray;
	}

	/*********************************** PRIVATE METHODS *****************************************/

	/**
	 * Method for saving question upvotes to local file.
	 * @param upvotes
	 */
	
	private void saveFileQuestionUpvotes(HashMap<String, Integer> upvotes) {
		try
		{
			FileOutputStream fileOutputStream = context.openFileOutput(
					SAVE_FILE, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					fileOutputStream);
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			gson.toJson(upvotes, outputStreamWriter); // Serialize to Json
			outputStreamWriter.flush();
			outputStreamWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for saving answer upvotes to local file.
	 * @param upvotes
	 */
	
	private void saveFileAnswerUpvotes(HashMap<String, UpvoteTuple> upvotes) {
		try
		{
			FileOutputStream fileOutputStream = context.openFileOutput(
					SAVE_FILE, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					fileOutputStream);
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			gson.toJson(upvotes, outputStreamWriter); // Serialize to Json
			outputStreamWriter.flush();
			outputStreamWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for saving posts that need to be pushed to local file.
	 * @param posts
	 */
	
	private void saveFilePushPosts(ArrayList<Post> posts) {
		try
		{
			FileOutputStream fileOutputStream = context.openFileOutput(
					SAVE_FILE, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					fileOutputStream);
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.create();
			gson.toJson(posts, outputStreamWriter); // Serialize to Json
			builder.serializeNulls(); // Show fields with null values
			outputStreamWriter.flush();
			outputStreamWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves an array of IDs to the local drive
	 * 
	 * @param idList A list of strings pertaining to question IDs that will be saved to local drive
	 */

	private void saveIds(ArrayList<String> idList)
	{

		try
		{
			FileOutputStream fileOutputStream = context.openFileOutput(
					SAVE_FILE, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					fileOutputStream);
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			gson.toJson(idList, outputStreamWriter); // Serialize to Json
			outputStreamWriter.flush();
			outputStreamWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Saves an array of Questions to the local drive
	 * 
	 * @param list A List of question objects that will be saved to local drive
	 */

	private void saveQuestions(ArrayList<Question> list)
	{

		try
		{
			FileOutputStream fileOutputStream = context.openFileOutput(
					SAVE_FILE, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					fileOutputStream);
			GsonBuilder builder = new GsonBuilder();

			// Gson does not serialize/deserialize dates with milisecond
			// precision unless specified
			Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.create();
			builder.serializeNulls(); // Show fields with null values
			gson.toJson(list, outputStreamWriter); // Serialize to Json
			outputStreamWriter.flush();
			outputStreamWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves an array of IDs from the local drive
	 * 
	 * @return A list of strings pertaining to question IDs
	 */

	private ArrayList<String> loadIds()
	{

		ArrayList<String> idArray = new ArrayList<String>();
		try
		{
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream);
			Type listType = new TypeToken<ArrayList<String>>()
			{
			}.getType();
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			ArrayList<String> list = gson.fromJson(inputStreamReader, listType);
			idArray = list;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return idArray;
	}
	
	/**
	 * Method for loading posts that need to be pushed to the server.
	 * @return
	 */
	
	private ArrayList<Post> loadPushPosts() {
		ArrayList<Post> posts = new ArrayList<Post>();
		try
		{
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream);
			Type listType = new TypeToken<ArrayList<Post>>()
			{
			}.getType();
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.create();
			builder.serializeNulls(); // Show fields with null values
			posts = gson.fromJson(inputStreamReader, listType);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return posts;
	}
	
	/**
	 * Method for loading question upvotes that need to be pushed to the server.
	 * @return
	 */
	
	private HashMap<String, Integer> loadFileQuestionUpvotes() {
		HashMap<String, Integer> upvotes = new HashMap<String, Integer>();
		try
		{
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream);
			Type listType = new TypeToken<HashMap<String, Integer>>()
			{
			}.getType();
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			upvotes = gson.fromJson(inputStreamReader, listType);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return upvotes;
	}
	
	/**
	 * Method for loading answer upvotes that need to be pushed to the server.
	 * @return
	 */
	
	private HashMap<String, UpvoteTuple> loadFileAnswerUpvotes() {
		HashMap<String, UpvoteTuple> upvotes = new HashMap<String, UpvoteTuple>();
		try
		{
			FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream);
			Type listType = new TypeToken<HashMap<String, UpvoteTuple>>()
			{
			}.getType();
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			upvotes = gson.fromJson(inputStreamReader, listType);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return upvotes;
	}
}

// These two methods set the same mode!

/**
 * Saves a list of question id's of questions the user posted an answer to.
 * 
 * @param UUID
 * 
 * 
 *            Going to comment this one out! -- Eric
 * 
 *            public void saveQuestionsOfPostedAnswers(ArrayList<String> list) {
 *            SAVE_FILE = POSTED_ANSWERS_FILE; saveIds(list); }
 */

/**
 * Saves a list of answers posted by the user to cache.
 * 
 * @param idList
 *            TODO
 * @param UUID
 * 
 */
// public void savePostedAnswersID(ArrayList<String> idList) {
// SAVE_FILE = POSTED_ANSWERS_FILE;
// saveIds(idList);
// }

/**
 * Saves the answer ID. This should only be needed if we're saving answers
 * independent of questions.
 * 
 * @param idList
 *            TODO
 * @param UUID
 */
// public void saveFavoriteAnswerID(ArrayList<String> idList) {
// SAVE_FILE = FAVORITE_ANSWERS_ID;
// saveIds(idList);
// }

/**
 * Loads a list of question ID's of questions posted by the user from cache.
 * 
 * @return list A list of strings containing ID's.
 */
// public ArrayList<String> loadQuestionsOfPostedAnswers() {
// SAVE_FILE = POSTED_ANSWERS_FILE;
// ArrayList<String> list = new ArrayList<String>();
// list = loadIds();
// return list;
// }

/**
 * Loads a list of answers posted by the user from cache.
 * 
 * @return list A list of strings containing ID's.
 */
// public ArrayList<String> loadPostedAnswers() {
// SAVE_FILE = POSTED_ANSWERS_FILE;
// ArrayList<String> list = new ArrayList<String>();
// list = loadIds();
// return list;
// }

/**
 * Returns a list of Answer objects from the answer bank.
 * 
 * @return answerArray A list of Answer objects.
 */
// public ArrayList<Answer> loadAnswers() {
// ArrayList<Answer> answerArray = new ArrayList<Answer>();
// try {
// FileInputStream fileInputStream = context.openFileInput(ANSWER_BANK);
// InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
// Type listType = new TypeToken<ArrayList<Answer>>(){}.getType();
// GsonBuilder builder = new GsonBuilder();
//
// //Gson does not serialize/deserialize dates with milisecond precision unless
// specified
// Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
// builder.serializeNulls(); //Show fields with null values
// ArrayList<Answer> list = gson.fromJson(inputStreamReader, listType);
// answerArray = list;
// } catch(IOException e) {
// e.printStackTrace();
// }
// return answerArray;
// }