/**
 * THIS IS FOR TESTING PURPOSES ONLY!!!
 * 
 * This class is used to mimic the server until we get the server up and running.  The purpose is to
 * generate a list of questions, answers and comments so that we can test loading, saving, getters, etc.
 * 
 * For now, please test populating the lists into the views with the data that is created within this
 * class.
 * 
 * YOU DO NOT NEED TO CALL ANYTHING FROM THIS CLASS.  THE MAIN ACTIVITY HAS TEST CODE THAT WILL INITIALIZE
 * THE QUESTION LIST.  THIS CLASS CANNOT BE INSTANTIATED.  ALL I WANT TO DO IS CREATE AND POPULATE THE SAVE 
 * FILE IF IT DOES NOT EXIST; LOAD THE SAVE FILE IF IT DOES!
 * 
 * Direct any questions to Eric.
 * 
 */
package ca.ualberta.cs.cmput301t03app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class mockServer {

	private static ArrayList<Question> mockQuestionList = new ArrayList<Question>();
	private static String mockSave = "fake.sav";
	private static Context serverContext;
	private static ArrayList<Question> questionList = new ArrayList<Question>();
	
	public static void initServer(Context context) {
		serverContext = context;
		loadExisting();
	}
	
	private static void populateQuestions() {
		Question q1 = new Question("Is this a mock server?", "This server isn't real", "Dan");
		Question q2 = new Question("What is this?", "I don't understand the purpose of this class", "Guss");
		Question q3 = new Question("Is this a free app?", "Do I need to pay to use this app or is it for free?", "Peter");
		Question q4 = new Question("Victor's cookies", "They were OK.  I can definitely make better cookies than Victor can.  BAKE OFF!", "Ruby");
		
		mockQuestionList.add(q1);
		mockQuestionList.add(q2);
		mockQuestionList.add(q3);
		mockQuestionList.add(q4);
	}
	
	public static ArrayList<Question> getMainList() {
		return questionList;
	}
	
	public static void acceptPush(ArrayList<Question> qList) {
		try {
			FileOutputStream fOut = serverContext.openFileOutput(mockSave, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);      	
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			
			gson.toJson(qList, outputStreamWriter); //Serialize to Json
			outputStreamWriter.flush();
			outputStreamWriter.close();        
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	private static void populateServer() {
		try {
			FileOutputStream fOut = serverContext.openFileOutput(mockSave, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);      	
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			
			gson.toJson(mockQuestionList, outputStreamWriter); //Serialize to Json
			outputStreamWriter.flush();
			outputStreamWriter.close();        
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	private static void loadExisting() {
		try {
			FileInputStream fIn = serverContext.openFileInput(mockSave);
			InputStreamReader isr = new InputStreamReader(fIn);
			Gson gson = new GsonBuilder().create();
			questionList = gson.fromJson(isr, new TypeToken<ArrayList<Question>>() {}.getType());
		} catch (FileNotFoundException e) {
			populateQuestions();
			populateServer();
			loadExisting();
		}
	}	
	
}
