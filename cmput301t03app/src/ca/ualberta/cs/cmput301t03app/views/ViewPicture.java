package ca.ualberta.cs.cmput301t03app.views;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.R.layout;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This activity includes the details of the selected question
 * or answer, the details of the comment, and a view of the
 * image attached to the question or answer. This activity will 
 * run when the user clicks on the image button on in the 
 * ViewQuestion activity
 *
 */
public class ViewPicture extends Activity {

	int commentType;
	Question question;
	String answerID;
	String questionID;
	TextView pictureTitle;
	TextView timeStamp;
	TextView author;
	ImageView picture;
	PostController pc = new PostController(this);
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_display_picture);
	    
	    /* Removes the actionbar title text */
		getActionBar().setDisplayShowTitleEnabled(false);
	    
	    Log.d("click", "in picture activity");
	    
	    Bundle extras = getIntent().getExtras();
	    commentType = extras.getInt(ViewQuestion.SET_COMMENT_TYPE);
	    
	    Log.d("click", "Coment type:  " + commentType);
	    
	    
	    questionID = extras.getString(ViewQuestion.QUESTION_ID_KEY);
	    question = pc.getQuestion(questionID);
	    
	    if (commentType == 2)
				answerID = extras.getString(ViewQuestion.ANSWER_ID_KEY);
		
	
	    Log.d("click", "** BEFORE ISNTANTIATION");
	    
	    instantiateViews();
	    setPostDetails();
	    
	    // TODO Auto-generated method stub
	}
	
	/**
	 * This method instantiates the view objects
	 */
	public void instantiateViews() {
		pictureTitle = (TextView) findViewById(R.id.picture_title);
		timeStamp = (TextView) findViewById(R.id.picture_post_timestamp);
		author = (TextView) findViewById(R.id.picture_post_author);
		picture = (ImageView) findViewById(R.id.displayPicture);
		
	}
	
	
	/**
	 *  Sets the question body,author and date from the question clicked from the
	 *  ViewQuestions activity. If an answer was clicked it also sets the answers
	 *  body.
	 */
	public void setPostDetails()	{
	    Log.d("click", " SETTING DETAILS");
	    
		if (commentType == 1) 	{ // comment for questions
			
		    Log.d("click", "********  Setting question stuff");
			
			pictureTitle.setText("Q: "
					+ pc.getQuestion(questionID).getSubject());
			timeStamp
					.setText("Posted: " + pc.getQuestion(questionID).getDate());
			author.setText("By: " + pc.getQuestion(questionID).getAuthor());
			Bitmap _bitmapPreScale = BitmapFactory.decodeByteArray(question.getPicture(), 0, question.getPicture().length);
			picture.setImageBitmap(_bitmapPreScale);
		} else if (commentType == 2)	{ // comment for answers
			
		    Log.d("click", "********  Setting ANSWER stuff");

		    
			pictureTitle.setText("Q: "
					+ pc.getQuestion(questionID).getSubject()
					+ System.getProperty("line.separator") + "A: "
					+ pc.getAnswer(answerID, questionID).getAnswer());
			timeStamp.setText("Posted: "
					+ pc.getAnswer(answerID, questionID).getDate());
			author.setText("By: "
					+ pc.getAnswer(answerID, questionID).getAuthor());
			Bitmap _bitmapPreScale = BitmapFactory.decodeByteArray(pc.getAnswer(answerID, questionID).getPicture(), 0, pc.getAnswer(answerID, questionID).getPicture().length);
			picture.setImageBitmap(_bitmapPreScale);
		}
		
		timeStamp.setText("Posted: " + pc.getQuestion(questionID).getDate());
		author.setText("By: " + pc.getQuestion(questionID).getAuthor());
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_question, menu);
		getActionBar().setHomeButtonEnabled(true);
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		switch (item.getItemId()) {
		case android.R.id.home:
			runOnUiThread(doFinish);
			break;
		}
		
		return (super.onOptionsItemSelected(item));
	}
	
	private Runnable doFinish = new Runnable() {
		public void run() {
			finish();
		}
	};

}
