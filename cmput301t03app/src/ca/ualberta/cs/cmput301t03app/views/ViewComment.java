package ca.ualberta.cs.cmput301t03app.views;

import java.util.ArrayList;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.controllers.GeoLocationTracker;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.GeoLocation;
import ca.ualberta.cs.cmput301t03app.models.Question;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 *         ViewComment activity is the activity responsible for showing comments
 *         to either a given question or a given answer.
 * 
 */

public class ViewComment extends Activity
{

	int commentType;
	protected boolean hasLocation = false;
	protected GeoLocation geoLocation;
	protected String cityName;
	String questionID;
	String answerID;
	ArrayList<Comment> comments = new ArrayList<Comment>();
	PostController pc = new PostController(this);
	ArrayList<String> commentBodyList = new ArrayList<String>();
	ArrayAdapter<String> cla;
	ListView commentListView;
	Button commentButton;
	TextView commentCount;
	TextView timeStamp;
	TextView author;
	TextView location;
	AlertDialog dialog;
	private static ServerDataManager sdm = new ServerDataManager();

	/**
	 * Called when the activity is first created.
	 * 
	 * Determines through the intent whether the view was called through a
	 * question or an answer, and then populates the view accordingly.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_comment);
		
		// enables the activity icon as a 'home' button. required if
		// "android:targetSdkVersion" > 14
		getActionBar().setHomeButtonEnabled(true);
		
		Bundle extras = getIntent().getExtras();
		commentType = extras.getInt(ViewQuestion.SET_COMMENT_TYPE); // answer or
																	// question
																	// comments
		Log.d("click", "Comment type: " + commentType);
		questionID = extras.getString(ViewQuestion.QUESTION_ID_KEY);
		switch (commentType)
		{
			case 1:
				comments = pc.getCommentsToQuestion(questionID);
				break;
			case 2:
				answerID = extras.getString(ViewQuestion.ANSWER_ID_KEY);
				comments = pc.getCommentsToAnswer(questionID, answerID);
				break;
		}
		instantiateViews();
		setListeners();
		setPostDetails();
		setCommentAdapter();
		updateCommentCount();
	}

	/**
	 * sets the views to correct fields
	 */
	public void instantiateViews()
	{


		commentButton = (Button) findViewById(R.id.comment_button);
		commentCount = (TextView) findViewById(R.id.comment_count);
		timeStamp = (TextView) findViewById(R.id.comment_post_timestamp);
		author = (TextView) findViewById(R.id.comment_post_author);
		commentListView = (ListView) findViewById(R.id.commentListView);
		cla = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, commentBodyList);
		location = (TextView) findViewById(R.id.comment_location);
	}

	public void setListeners()
	{

		commentButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				addComment();
			}
		});
	}

	/**
	 *  Sets the question body,author and date from the question clicked from the
	 *  ViewQuestions activity. If an answer was clicked it also sets the answers
	 *  body.
	 */
	public void setPostDetails()
	{

		TextView commentTitle = (TextView) findViewById(R.id.comment_title);
		if (commentType == 1)
		{ // comment for questions
			commentTitle.setText("Q: "
					+ pc.getQuestion(questionID).getSubject());
			timeStamp
					.setText("Posted: " + pc.getQuestion(questionID).getDate());
			author.setText("By: " + pc.getQuestion(questionID).getAuthor());
			
			//Set location
			if (pc.getQuestion(questionID).getGeoLocation() != null) {
				location.setText("Location: " + pc.getQuestion(questionID).getGeoLocation().getCityName());
			}
			
		} else if (commentType == 2)
		{ // comment for answers
			commentTitle.setText("Q: "
					+ pc.getQuestion(questionID).getSubject()
					+ System.getProperty("line.separator") + "A: "
					+ pc.getAnswer(answerID, questionID).getAnswer());
			timeStamp.setText("Posted: "
					+ pc.getAnswer(answerID, questionID).getDate());
			author.setText("By: "
					+ pc.getAnswer(answerID, questionID).getAuthor());
			//Set location
			if (pc.getAnswer(answerID, questionID).getGeoLocation() != null) {
				location.setText("Location: " + pc.getAnswer(answerID, questionID).getGeoLocation().getCityName());
			}
		}
	}
	
	/**
	 *  This sets up the comment adapter by grabbing the comments
	 *  from the question or answer objects and feeding them to
	 *  the adapter.
	 */

	public void setCommentAdapter()
	{

		commentListView.setAdapter(cla);
		getCommentBodiesFromComment();
		cla.notifyDataSetChanged();
	}
	
	/**
	 * This extracts the comment bodies from the
	 * Comment object to be able to place them in the
	 * adapter.
	 */
	public void getCommentBodiesFromComment()
	{ // used for showing in the view

		if (comments != null)
		{
			commentBodyList.clear();
			for (int i = 0; i < comments.size(); i++)
				commentBodyList.add(comments.get(i).getCommentBody());
		}
	}

	
	/**
	 *  This updates the comment count for the view for either
	 *  answer or question.
	 * 
	 */
	public void updateCommentCount()
	{

		if (commentType == 1)
		{
			commentCount
					.setText("Comments: "
							+ String.valueOf(pc.getQuestion(questionID)
									.countComments()));
		} else if (commentType == 2)
		{
			commentCount.setText("Comments: "
					+ String.valueOf(pc.getAnswer(answerID, questionID)
							.countAnswerComments()));
		}
	}

	/**
	 * onClick method for adding comments, prompts the user with a dialog box
	 * which takes in a username (userNameString) and comment
	 * (commentBodyString) and then adds that comment to the either a question
	 * or an answer depending on the context.
	 */

	public void addComment()
	{

		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.comment_dialog, null);
		final EditText postBody = (EditText) promptsView
				.findViewById(R.id.comment_body);
		final EditText userName = (EditText) promptsView
				.findViewById(R.id.UsernameRespondTextView);
		final EditText userLocation = (EditText) promptsView
				.findViewById(R.id.userLocation3);
		
		CheckBox check = (CheckBox) promptsView
				.findViewById(R.id.enableLocation3);
		check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hasLocation=!hasLocation;
				
				if (hasLocation) {
					
					geoLocation = new GeoLocation();
					GeoLocationTracker locationTracker = new GeoLocationTracker(ViewComment.this, geoLocation);
					locationTracker.getLocation();
					
//					location.setLatitude(53.53333);
//					location.setLongitude(-113.5);
					
					//Delay for 5 seconds
				    Handler handler = new Handler(); 
				    handler.postDelayed(new Runnable() { 
				         public void run() { 
				        	 cityName = pc.getCity(geoLocation);
				        	 Log.d("Loc","Timer is done");
							if (cityName != null) {
								userLocation.setText(cityName);
							} else {
								userLocation.setText("Location not found.");
							}
				         } 
				    }, 5000); 
				}
				
			}
		});
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); // Create
																				// a
																				// new
																				// AlertDialog
		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder.setPositiveButton("Comment",
				new DialogInterface.OnClickListener()
				{

					// Building the dialog for adding
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

						String commentBodyString = (String) postBody.getText()
								.toString();
						String userNameString = (String) userName.getText()
								.toString();
						String userLocationString = (String) userLocation.getText()
								.toString();
						Comment c = new Comment(commentBodyString,
								userNameString);
						if(hasLocation){
							
							//Set location if location typed by user is same as location found
							if (userLocationString.equals(cityName)){
								c.setGeoLocation(geoLocation);
							}
							//Find the coordinates of place entered by user and set location
							else{
								c.setGeoLocation(pc.turnFromCity(userLocationString));
								//Testing
								GeoLocation testlocation= pc.turnFromCity(userLocationString);
								Log.d("Location",Double.toString(testlocation.getLatitude()));
								Log.d("Location",Double.toString(testlocation.getLongitude()));
								
							}
						}
						if (commentType == 1)
						{
							pc.addCommentToQuestion(c, questionID);
							comments = pc.getCommentsToQuestion(questionID);
							Thread thread = new AddCommentThread(c, questionID);
							thread.start();
						} else if (commentType == 2)
						{
							pc.addCommentToAnswer(c, questionID, answerID);
							comments = pc.getCommentsToAnswer(questionID,
									answerID);
							Thread thread = new AddCommentThread(c, questionID, answerID);
							thread.start();
						}
						// setCommentAdapter();
						
						commentBodyList.add(commentBodyString);
						cla.notifyDataSetChanged();
						updateCommentCount(); // <-- MIGHT NOT USE THIS
					}

				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						// Do nothing
						hasLocation = false;
						dialog.cancel();
					}
				});

		final AlertDialog alertDialog = alertDialogBuilder.create();
		dialog = alertDialog;
		alertDialog.show();
		alertDialog.getButton(AlertDialog.BUTTON1).setEnabled(false);

		// creating a listener to see if any changes to edit text in dialog
		TextWatcher textwatcher = new TextWatcher()
		{

			private void handleText()
			{

				final Button button = alertDialog
						.getButton(AlertDialog.BUTTON_POSITIVE);
				if (postBody.getText().length() == 0)
				{ // these checks the edittext to make sure not empty edit text
					button.setEnabled(false);
				} else if (userName.getText().length() == 0)
				{
					button.setEnabled(false);
				} else
				{
					button.setEnabled(true);
				}
			}

			@Override
			public void afterTextChanged(Editable s)
			{

				handleText();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{

				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{

				// do nothing
			}
		};
		postBody.addTextChangedListener(textwatcher); // adding listeners to the
														// edittexts
		userName.addTextChangedListener(textwatcher);
		Toast.makeText(this, "Please write your comment", Toast.LENGTH_SHORT)
				.show();
	}

	//Used for testing.
	public AlertDialog getDialog()
	{

		return dialog;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.view_question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		if (id == R.id.home) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			
		} else if (id == R.id.user_home) {
			Intent intent = new Intent(this, UserHome.class);
			startActivity(intent);
		}
		

		return (super.onOptionsItemSelected(item));
	}
	
	class AddCommentThread extends Thread {
    	private String qID;
    	private String aID;
    	private Comment comment;
    	
    	public AddCommentThread(Comment comment, String qID) {
    		this.qID = qID;
    		this.aID = null;
    		this.comment = comment;
    		//Log.d("push", this.question.getSubject());
    	}
    	
    	public AddCommentThread(Comment comment, String qID, String aID) {
    		this.qID = qID;
    		this.aID = aID;
    		this.comment = comment;
    	}
    	
    	@Override
    	public void run() {
    		if (this.aID == null) {
    			pc.commentAQuestion(this.comment, this.qID);
    		} else {
    			pc.commentAnAnswer(this.comment, this.aID, this.qID);
    		}
    		try {
    			Thread.sleep(500);
    		} catch(InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    	}
    }
	
}
