package ca.ualberta.cs.cmput301t03app.views;

import java.util.ArrayList;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.mockserver.mockServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewComment extends Activity {

	int commentType;
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
	mockServerDataManager mockDataManage; // FOR TESTING PURPOSES ONLY

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_comment);
		Bundle extras = getIntent().getExtras();
		commentType = extras.getInt(ViewQuestion.SET_COMMENT_TYPE);	//answer or question comments
		Log.d("click", "Comment type: " + commentType);
		questionID = extras.getString(ViewQuestion.QUESTION_ID_KEY);
		/* FOR TESTING PURPOSES ONLY		 */
		mockDataManage = new mockServerDataManager(this);
		/* END TESTING BLOCK		 */
		switch (commentType) {
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

	public void instantiateViews() {
		/**
		 * sets the views to correct fields
		 */
		commentButton = (Button) findViewById(R.id.comment_button);
		commentCount = (TextView) findViewById(R.id.comment_count);
		timeStamp = (TextView) findViewById(R.id.comment_post_timestamp);
		author = (TextView) findViewById(R.id.comment_post_author);
		commentListView = (ListView) findViewById(R.id.commentListView);
		cla = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, commentBodyList);
	}

	public void setListeners() {
		commentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addComment();
			}
		});
	}
	
	public void setPostDetails() {	
		TextView commentTitle = (TextView) findViewById(R.id.comment_title);
		if (commentType == 1) {		//comment for questions
			commentTitle.setText("Q: " + pc.getQuestion(questionID).getSubject());
			timeStamp.setText("Posted: " + pc.getQuestion(questionID).getDate());
			author.setText("By" + pc.getQuestion(questionID).getAuthor());
		} 
		else if (commentType == 2) {	//comment for answers 
			commentTitle
					.setText("Q: " + pc.getQuestion(questionID).getSubject() +System.getProperty("line.separator")+  "A: " + pc.getAnswer(answerID, questionID).getAnswer());
			timeStamp.setText("Posted: " + pc.getQuestion(questionID).getAnswerByID(answerID).getDate());
			author.setText("By: " + pc.getQuestion(questionID).getAnswerByID(answerID).getAuthor());
		}
	}

	public void setCommentAdapter() {
		commentListView.setAdapter(cla);
		getCommentBodiesFromComment();
		cla.notifyDataSetChanged();
	}

	public void getCommentBodiesFromComment() {	//used for showing in the view
		if (comments != null) {
			commentBodyList.clear();
			for (int i = 0; i < comments.size(); i++)
				commentBodyList.add(comments.get(i).getCommentBody());
		}
	}

	public void updateCommentCount() {
		if (commentType == 1) {
			commentCount
					.setText("Comments: "
							+ String.valueOf(pc.getQuestion(questionID)
									.countComments()));
		} else if (commentType == 2) {
			commentCount.setText("Comments: "
					+ String.valueOf(pc.getQuestion(questionID)
							.getAnswerByID(answerID).countAnswerComments()));
		}
	}

	public void addComment() {
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.activity_post_dialog, null);
		final EditText postBody = (EditText) promptsView
				.findViewById(R.id.postBody);
		final EditText userName = (EditText) promptsView
				.findViewById(R.id.UsernameRespondTextView);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);	// Create a new AlertDialog
		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder
			.setPositiveButton("Comment",
				new DialogInterface.OnClickListener() {
				// Building the dialog for adding
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
						} 
						else if (commentType == 2) {
							pc.addCommentToAnswer(c, questionID, answerID);
							comments = pc.getCommentsToAnswer(questionID,
									answerID);
						}
						// setCommentAdapter();
						commentBodyList.add(commentBodyString);
						cla.notifyDataSetChanged();
						updateCommentCount(); // <-- MIGHT NOT USE THIS
						/* THIS BLOCK OF CODE IS FOR MOCK SERVER TESTING ONLY */
						mockDataManage.mockPushQuestionToServer(pc.getQuestion(questionID));
					}

				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Do nothing
						dialog.cancel();
					}
				});

		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		alertDialog.getButton(AlertDialog.BUTTON1).setEnabled(false);

		//creating a listener to see if any changes to edit text in dialog
		TextWatcher textwatcher = new TextWatcher(){
			private void handleText(){
				final Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				if(postBody.getText().length() == 0){	//these checks the edittext to make sure not empty edit text
					button.setEnabled(false);
				}
				else if(userName.getText().length() == 0){
					button.setEnabled(false);
				}
				else{
					button.setEnabled(true);
				}	
			}
			@Override
			public void afterTextChanged(Editable s) {
				handleText();
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				//do nothing
			}
			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				//do nothing
			}
		};
		postBody.addTextChangedListener(textwatcher);	//adding listeners to the edittexts
		userName.addTextChangedListener(textwatcher);
		Toast.makeText(this, "Please write your comment", Toast.LENGTH_SHORT)
				.show();
	}
}
