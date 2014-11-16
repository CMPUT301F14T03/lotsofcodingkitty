package ca.ualberta.cs.cmput301t03app.views;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.R.layout;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Question;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPicture extends Activity {

	Question question;
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
	    
	    Log.d("click", "in picture activity");
	    
	    Bundle extras = getIntent().getExtras();
	    questionID = extras.getString(ViewQuestion.QUESTION_ID_KEY);
	    question = pc.getQuestion(questionID);
	
	    instantiateViews();
	    setPostDetails();
	    
	    // TODO Auto-generated method stub
	}
	
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
	public void setPostDetails()
	{
		pictureTitle.setText("Q: " + pc.getQuestion(questionID).getSubject());
		timeStamp.setText("Posted: " + pc.getQuestion(questionID).getDate());
		author.setText("By: " + pc.getQuestion(questionID).getAuthor());
		
		/* Set the picture here, after dealing with title */
		picture.setImageDrawable(Drawable.createFromPath(question.getPicture()));
		
	}

}
