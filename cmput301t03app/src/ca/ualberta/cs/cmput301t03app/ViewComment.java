package ca.ualberta.cs.cmput301t03app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewComment extends Activity {

	String question_or_answer_id;
	PostController pc = new PostController();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_view_comment);
	    
	    Bundle extras = getIntent().getExtras();
		question_or_answer_id = extras.getString("question_id");
		
		TextView questionTitle = (TextView) findViewById(R.id.comments_question_title);
		questionTitle.setText(pc.getQuestion(question_or_answer_id).getSubject());

	}
	
	public void setCommentAdapter() {
		
		
	}
	
	
	

}
