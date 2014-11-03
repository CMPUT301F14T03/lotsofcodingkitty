package ca.ualberta.cs.cmput301t03app;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerDataManager implements iDataManager{
	
	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t03/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t03/";
	private static final String TAG = "QuestionSearch";

	private Gson gson;

	public ServerDataManager() {
		gson = new Gson();
	}

	/**
	 * Get a movie with the specified id
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
	
	public void clearLocalNonPushed(){
		
	}
	
	public Question getQuestion(String questionId) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + questionId);

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			SearchHit<Question> sr = parseQuestionHit(response);
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	

	/**
	 * Get movies with the specified search string. If the search does not
	 * specify fields, it searches on all the fields.
	 */
	public ArrayList<Question> searchMovies(String searchString, String field) {
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
					for (SearchHit<Question> sesr : hits.getHits()) {
						result.add(sesr.getSource());
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
	 * Adds a new movie
	 */
	public void addQuestion(Question question) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + question.getId());

			StringEntity stringEntity = new StringEntity(gson.toJson(question));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the movie with the specified id
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
	 * Creates a search request from a search string and a field
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
	 * Parses the response of a search
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
	 * Gets content from an HTTP response
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
