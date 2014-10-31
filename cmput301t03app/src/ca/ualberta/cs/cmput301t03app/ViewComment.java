package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewComment extends Activity {

	int commentType;
	String questionID;
	String answerID;
	ArrayList<Comment> comments = new ArrayList<Comment>();
	PostController pc = new PostController(this);
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_view_comment);
	    
	    Bundle extras = getIntent().getExtras();
		commentType = extras.getInt(ViewQuestion.SET_COMMENT_TYPE);
		// This is used to find which mode this is set to. Either Answer comments
		// or Question comments.
		switch (commentType) {
			case 1:
				questionID = extras.getString(ViewQuestion.QUESTION_ID_KEY);
				comments = pc.getCommentsToQuestion(questionID);
			case 2:
				questionID = extras.getString(ViewQuestion.QUESTION_ID_KEY);
				answerID= extras.getString(ViewQuestion.ANSWER_ID_KEY);
				comments = pc.getCommentsToAnswer(questionID, answerID);

		}
		
		TextView questionTitle = (TextView) findViewById(R.id.comments_question_title);
		//Title.setText(pc.getQuestion(question_or_answer_id).getSubject());

	}
	
	public void setCommentAdapter() {
		
		
	}
	
	
	

}
