package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewComment extends Activity {

	int commentType;
	String questionID;
	String answerID;
	ArrayList<Comment> comments = new ArrayList<Comment>();

	PostController pc = new PostController(this);
	private static ArrayList<String> commentBodyList = new ArrayList<String>();
	ArrayAdapter<String> cla;
	ListView commentListView;
	Button commentButton;
	TextView commentCount;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_comment);

		Bundle extras = getIntent().getExtras();
		commentType = extras.getInt(ViewQuestion.SET_COMMENT_TYPE);
		// This is used to find which mode this is set to. Either Answer
		// comments
		// or Question comments.
		
		Log.d("click", "Comment type: " + commentType);
		questionID = extras.getString(ViewQuestion.QUESTION_ID_KEY);
		
		
		switch (commentType) {
		case 1:
			comments = pc.getCommentsToQuestion(questionID);
			
		case 2:
			answerID = extras.getString(ViewQuestion.ANSWER_ID_KEY);
			comments = pc.getCommentsToAnswer(questionID, answerID);
			
		}
		
		// Set the Title (question or answer)
				TextView commentTitle = (TextView) findViewById(R.id.comment_title);
		if (commentType == 1) {
			commentTitle.setText(pc.getQuestion(questionID).getSubject());
		} else if (commentType == 2) {
			commentTitle.setText(pc.getAnswer(answerID, questionID).getAnswer());
		}

		
		instantiateViews();
		setListeners();
		setCommentAdapter();
		
		updateCommentCount();
	}
	

	public void instantiateViews() {
		commentButton = (Button) findViewById(R.id.comment_button);
		commentCount = (TextView) findViewById(R.id.comment_count);
		commentListView = (ListView) findViewById(R.id.commentListView);
		cla = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, commentBodyList);
	}

	public void setListeners() {
		commentButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addComment();
			}
		});
	}

	public void setCommentAdapter() {
		getCommentBodiesFromComment();
		commentListView.setAdapter(cla);
	}

	public void getCommentBodiesFromComment() {
		if (comments != null) {
			commentBodyList.clear();
			for (int i = 0; i < comments.size(); i++)
				commentBodyList.add(comments.get(i).getCommentBody());
		}
	}
	
	public void updateCommentCount() {
		if(commentType == 1){
		commentCount.setText("Comments: "
				+ String.valueOf(pc.getQuestion(questionID).countComments()));
		} 
		else if(commentType == 2) {
			commentCount.setText("Comments: "
					+ String.valueOf(pc.getQuestion(questionID).getAnswerByID(answerID).countAnswerComments()));
		}
	}
	

	public void addComment() {

		LayoutInflater li = LayoutInflater.from(this);

		// Get XML file to view
		View promptsView = li.inflate(R.layout.activity_post_dialog, null);

		final EditText postBody = (EditText) promptsView
				.findViewById(R.id.postBody);

		final EditText userName = (EditText) promptsView
				.findViewById(R.id.UsernameRespondTextView);

		// Create a new AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// Link the alertdialog to the XML
		alertDialogBuilder.setView(promptsView);

		// Building the dialog for adding
		alertDialogBuilder.setPositiveButton("Comment",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						String commentBodyString = (String) postBody.getText()
								.toString();
						String userNameString = (String) userName.getText()
								.toString();

						Comment c = new Comment(commentBodyString,
								userNameString);

						if (commentType == 1) {
							pc.addCommentToQuestion(c, questionID);
							comments = pc.getCommentsToQuestion(questionID);
						} else if (commentType == 2) {
							pc.addCommentToAnswer(c, questionID, answerID);
							comments = pc.getCommentsToAnswer(questionID,
									answerID);
						}

						//setCommentAdapter();
						commentBodyList.add(commentBodyString);
						cla.notifyDataSetChanged();
						updateCommentCount(); //<-- MIGHT NOT USE THIS
					}

				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Do nothing
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();

	}

}
