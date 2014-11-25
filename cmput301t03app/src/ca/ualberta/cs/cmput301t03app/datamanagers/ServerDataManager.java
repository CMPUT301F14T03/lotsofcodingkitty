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
import ca.ualberta.cs.cmput301t03app.models.Post;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.SearchHit;
import ca.ualberta.cs.cmput301t03app.models.SearchResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
	 * Iterates through an array of posts, looking for three cases 
	 * 
	 * 1) The parent of the post is a question (check if it is an answer or comment, 
	 * append the answer or comment)
	 * 
	 * 2) The parent of the post is an answer (it is a comment, find the answer
	 * and append the comment)
	 * 
	 * 3) There is not parent of the post (it is a new question, add it to the
	 * server)
	 * 
	 * Included in the post array is the parentId which is what allows us to
	 * easily append to a question or answer without too many comparison.
	 * 
	 * @param posts
	 */
	
	public void pushPosts(final ArrayList<Post> posts) {
		try {
			for (int i = 0; i < posts.size(); i++) {
				Post post = posts.get(i);
				Question q = null;

				// If the parent is a Question

				if (post.getClassofParent().equals(Question.class)) {

					// If the post is an Answer

					if (post.getSelf().getClass().equals(Answer.class)) {
						q = getQuestion(post.getParentId());
						Log.i("AnswerToQuestion", post.getParentId());
						q.addAnswer((Answer) post.getSelf());
						updateQuestion(q);

					}

					// If the post is a Comment

					else if (post.getSelf().getClass().equals(Comment.class)) {
						q = getQuestion(post.getParentId());
						Log.i("CommentToQuestion", post.getParentId());
						q.addComment((Comment) post.getSelf());
						updateQuestion(q);
					}

					// If the parent is an Answer

				} else if (post.getClassofParent().equals(Answer.class)) {
					q = getQuestion(post.getQuestionParentId());
					Log.i("CommentToAnswer", post.getParentId());
					q.addComment((Comment) post.getSelf());
					updateQuestion(q);
				}

				// If the post is a Question

				else {
					q = (Question) post.getSelf();
					Log.i("Question", q.getId());
					addQuestion(q);
				}
			}
		} catch (Exception e) {
				//Log.i("", e.getMessage());
		}
	}
	
	public void pushQuestionUpvote(String questionId, Integer amount) {
		try {
			Question q = getQuestion(questionId);
			for (int i = 0; i < amount; i++) {
				q.upRating();
			}
			updateQuestion(q);
		} catch (Exception e) {
			//Log.i("", e.getMessage());
		}
	}
	
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
			//Log.i("", e.getMessage());
		}
	}
	
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
	 * Adds a new question
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
	 * Updates an existing question
	 */
	
	public void updateQuestion(Question question) {
		deleteQuestion(question.getId());
		addQuestion(question);
	}
	
	/**
	 * Deletes the question with the specified id
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
