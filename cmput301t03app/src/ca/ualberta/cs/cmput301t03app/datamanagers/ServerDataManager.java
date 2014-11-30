package ca.ualberta.cs.cmput301t03app.datamanagers;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ca.ualberta.cs.cmput301t03app.interfaces.IDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Hits;
//import ca.ualberta.cs.cmput301t03app.models.Post;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.SearchHit;
import ca.ualberta.cs.cmput301t03app.models.SearchResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * Manages the saving and loading of gson files to elastic search server.
 * <p>
 * All questions will be saved to server.
 *
 */
public class ServerDataManager implements IDataManager{
	
	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t03/question/_search?size=1000000";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t03/question/";
	private static final String TAG = "QuestionSearch";

	private Gson gson;
	private GsonBuilder builder;

	public ServerDataManager() {
		builder = new GsonBuilder();
		gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.create();
		builder.serializeNulls(); // Show fields with null values
	}

	/**
	 * Get a question with the specified id
	 */
	
	@Override
	public ArrayList<Question> loadQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveAnswers(ArrayList<Answer> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveToQuestionBank(ArrayList<Question> list) {
		// TODO Auto-generated method stub
		
	}	
	
	/**
	 * Updates the number of upvotes for a question on the server.
	 * @param questionId - The question that is being updated
	 * @param amount - The number of upvotes to be added to the question upvotes
	 */
	public void pushQuestionUpvote(String questionId, Integer amount) {
		try {
			Question q = getQuestion(questionId);
			for (int i = 0; i < amount; i++) {
				q.upRating();
			}
			updateQuestion(q);
		} catch (Exception e) {
			
		}
	}

	/**
	 * Updates the number of upvotes for an answer on the server.
	 * @param answerId - the answer that is being updated
	 * @param questionId - the question that the answer is pertaining to
	 * @param amount - the number of upvotes to be added to the answer upvotes
	 */
	public void pushAnswerUpvote(String answerId, String questionId, Integer amount) {
		try {
			Question q = getQuestion(questionId);
			ArrayList<Answer> answers = q.getAnswers();
			for (int i = 0; i < answers.size(); i++) {
				if (answers.get(i).getId().equals(answerId)) {
					for (int j = 0; j < amount; j++) {
						answers.get(i).upRating();
					}
				}
			}
			q.setAnswers(answers);
			updateQuestion(q);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Gets the question from server using the questionId
	 * @param questionId - the id of the question to be returned
	 * @return - the question
	 */
	
	public Question getQuestion(String questionId) {
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpGet = new HttpGet(RESOURCE_URL + questionId);

		HttpResponse response;
		

		try {
			response = httpClient.execute(httpGet);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);
			SearchHit<Question> sr = parseQuestionHit(response);
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	

	/**
	 * Get questions with the specified search string. If the search does not
	 * specify fields, it searches on all the fields.
	 */
	public ArrayList<Question> searchQuestions(String searchString, String field) {
		ArrayList<Question> result = new ArrayList<Question>();

		
		if (searchString.equals(null) || searchString.equals("")) {
			searchString = "*";
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		try
		{
			HttpPost searchRequest = createSearchRequest(searchString, field);
			HttpResponse response = httpClient.execute(searchRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

			SearchResponse<Question> esResponse = parseSearchResponse(response);
			
			Hits<Question> hits = esResponse.getHits();
			if (hits != null) {
				if (hits.getHits() != null) {
					for (int i = 0; i < hits.getHits().size(); i++) {
						SearchHit<Question> sesr = hits.getHits().get(i);
						Question q = sesr.getSource();
						q.setId(sesr.get_id()); // for some reason q.getId() was returning null
												// so I added this.
						result.add(q);
					}
				}
			}
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * adds a question to server
	 * @param question - the question to be added to the server
	 */
	public void addQuestion(Question question) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			Log.d("push", question.getId());
			HttpPost addRequest = new HttpPost(RESOURCE_URL + question.getId());

			StringEntity stringEntity = new StringEntity(gson.toJson(question));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.d("push", status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates a question in server
	 * @param question - the question to be updated
	 */
	public void updateQuestion(Question question) {
		deleteQuestion(question.getId());
		addQuestion(question);
	}
	
	/**
	 * Deletes the question with the specified id
	 * @param questionId - the id of the question to be deleted
	 */
	public void deleteQuestion(String questionId) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpDelete deleteRequest = new HttpDelete(RESOURCE_URL + questionId);
			deleteRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates a searchRequest that will be used by elastic search to return the results of the search
	 * @param searchString - the word that will be used to search through the server
	 * @param field - the field that the word will be searched through
	 * @return HttpPost - an httpPost that will be needed to be parsed
	 * @throws UnsupportedEncodingException
	 */
	private HttpPost createSearchRequest(String searchString, String field)	throws UnsupportedEncodingException {
		
		HttpPost searchRequest = new HttpPost(SEARCH_URL);

		String[] fields = null;
		if (field != null) {
			fields = new String[1];
			fields[0] = field;
		}
		
		SimpleSearchCommand command = new SimpleSearchCommand(searchString,	fields);
		
		String query = command.getJsonCommand();
		Log.i(TAG, "Json command: " + query);

		StringEntity stringEntity;
		stringEntity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);

		return searchRequest;
	}
	
	/**
	 * Returns the search hit from elastic search server
	 * @param response -the httpResponse that is returned when connected to server
	 * @return the search hit from elastic search server
	 */
	private SearchHit<Question> parseQuestionHit(HttpResponse response) {
		
		try {
			String json = getEntityContent(response);
			Type searchHitType = new TypeToken<SearchHit<Question>>() {}.getType();
			
			SearchHit<Question> sr = gson.fromJson(json, searchHitType);
			return sr;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Returns a SearchResponse after being parsed 
	 * @param response - the httpResponse that is returned when connected to server
	 * @return a search response 
	 * @throws IOException
	 */
	private SearchResponse<Question> parseSearchResponse(HttpResponse response) throws IOException {
		String json;
		json = getEntityContent(response);

		Type searchResponseType = new TypeToken<SearchResponse<Question>>() {
		}.getType();
		
		SearchResponse<Question> esResponse = gson.fromJson(json, searchResponseType);

		return esResponse;
	}

	/**
	 * Returns the content of a http response
	 * @param response - the http response to be parsed
	 * @return a string that pertains to the content of the http response
	 * @throws IOException
	 */
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}
	
}


