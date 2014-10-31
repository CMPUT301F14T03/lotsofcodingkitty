package ca.ualberta.cs.cmput301t03app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewQuestion extends Activity {
	PostController pc = new PostController(this);
	UserPostCollector upc = new UserPostCollector();
	ArrayList<Answer> answerList = new ArrayList<Answer>();
	public static final String SET_COMMENT_TYPE="0";
	public static final int COMMENT_ON_QUESTION_KEY = 1;
	public static final int COMMENT_ON_ANSWER_KEY = 2;
	public static final String QUESTION_ID_KEY = "3";
	public static final String ANSWER_ID_KEY = "4";
	AnswerListAdapter ala;
	ListView answerListView;
	ImageButton favIcon;
	ImageButton upvoteButton;
	TextView upvote_score;
	Button answerButton;
	String question_id;
	TextView answerCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_question);

		Bundle extras = getIntent().getExtras();
		question_id = extras.getString("question_id");
		
		instantiateViews();
		setQuestionText(question_id);
		updateAnswerCount();
		setListeners();
		setAnswerAdapter();
	}

	public void setListeners() {

		answerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub

				Log.d("click", "click Answer" + position);
				
				toCommentActivityAnswer(view);
				
			}
		});
		
		answerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				answerQuestion();
			}
		});
		
		favIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setFavorited();
				favIcon.setImageResource(R.drawable.ic_fav_yes);
			}
		});

		upvoteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer.toString(pc.getQuestion(question_id).getRating());
				increment_upvote();
			}
		});
		
	}
	
	@SuppressWarnings("unchecked")
	public void setAnswerAdapter() {
		answerListView = (ListView) findViewById(R.id.answerListView);
		populateThisQuestionsAnswers(question_id);
		ala = new AnswerListAdapter(this, R.id.answerListView, answerList);
		answerListView.setAdapter(ala);
	}
	
	public void populateThisQuestionsAnswers(String question_id) {
		answerList.clear();
		answerList.addAll(pc.getQuestion(question_id).getAnswers());
		
	}
	
	public void setQuestionText(String ID) {
		Question q = pc.getQuestion(ID);
		TextView q_title = (TextView) findViewById(R.id.question_title);
		TextView upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		TextView q_body = (TextView) findViewById(R.id.question_text_body);
		TextView q_author = (TextView) findViewById(R.id.question_author);
		TextView q_date = (TextView) findViewById(R.id.post_timestamp);
		upvote_score.setText(Integer.toString(pc.getQuestion(question_id)
				.getRating()));
		q_title.setText(q.getSubject());
		q_body.setText(q.getBody());
		q_author.setText("By: " + q.getAuthor());
		//Date to string
		// http://javarevisited.blogspot.ca/2011/09/convert-date-to-string-simpledateformat.html
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date_to_string= sdf.format(q.getDate());
		q_date.setText("Posted: "+ date_to_string);

		// Log.d("click", "Contains? " +
		// upc.getFavoriteQuestions().contains(pc.getQuestion(question_id)));
		//
		// if(upc.getFavoriteQuestions().contains(pc.getQuestion(question_id)))
		// {
		// favIcon.setImageResource(R.drawable.ic_fav_yes);
		// }

	}
	
	public void instantiateViews(){
		
	//	thisQuestion = (TextView) findViewById(R.id.question_title);
		answerListView = (ListView) findViewById(R.id.answerListView);
		favIcon = (ImageButton) findViewById(R.id.question_fav_icon);
		upvoteButton = (ImageButton) findViewById(R.id.question_upvote_button);
		upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		answerButton = (Button) findViewById(R.id.question_answer_button);
		answerCounter = (TextView) findViewById(R.id.answer_count);
		answerListView= (ListView) findViewById(R.id.answerListView);
	}
	
	// This function sends the user the view comment activity to comment on a question
	public void toCommentActivityQuestion(View v) {
		/* This method takes user to ViewComment activity */ 
		Intent i = new Intent( this, ViewComment.class );
		i.putExtra(SET_COMMENT_TYPE,COMMENT_ON_QUESTION_KEY);
		i.putExtra(QUESTION_ID_KEY, question_id);
		startActivity(i);
	}
	// This function sends the user the view comment activity to comment on an answer
	public void toCommentActivityAnswer(View v) {
		/* This method takes user to ViewComment activity */ 
		Answer answer=(Answer) v.getTag();
		Intent i = new Intent( this, ViewComment.class );
		i.putExtra(SET_COMMENT_TYPE,COMMENT_ON_ANSWER_KEY);
		i.putExtra(QUESTION_ID_KEY, question_id);
		i.putExtra(ANSWER_ID_KEY, answer.getId());
		
		startActivity(i);
	}
	
	
	public void answerQuestion() {
		
		LayoutInflater li = LayoutInflater.from(this);

		// Get XML file to view
		View promptsView = li.inflate(R.layout.activity_answer_dialog, null);

		final EditText answerBody = (EditText) promptsView
				.findViewById(R.id.answerBody);

		final EditText userName = (EditText) promptsView
				.findViewById(R.id.UsernameRespondTextView);


		// Create a new AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// Link the alertdialog to the XML
		alertDialogBuilder.setView(promptsView);

		// Building the dialog for adding
		alertDialogBuilder.setPositiveButton("Answer",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						String answerBodyString = (String) answerBody
								.getText().toString();
						String userNameString = (String) userName.getText()
								.toString();

						Answer a = new Answer(answerBodyString, userNameString, question_id);
						
						pc.getQuestion(question_id).addAnswer(a);
						populateThisQuestionsAnswers(question_id);
						
						ala.updateAdapter(answerList);
						updateAnswerCount();
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


	public void setFavorited() {
		upc.addFavoriteQuestion(question_id);
		//Log.d("click", "Favs: " + upc.getFavoriteQuestions());
	}

	public void increment_upvote() {
		pc.getQuestion(question_id).upRating();
		TextView upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		upvote_score.setText(Integer.toString(pc.getQuestion(question_id)
				.getRating()));
	}
	
	public void updateAnswerCount() {
		Log.d("click", "Count" + String.valueOf(pc.getQuestion(question_id).countAnswers()));
		answerCounter.setText("Answers: " + String.valueOf(pc.getQuestion(question_id).countAnswers()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// These button listeners are for the answers
	
	// This on upvotes an answer
	public void answerUpvote(View v){
		Answer answer=(Answer) v.getTag();
		answer.upRating();
		ala.notifyChange();
		
	}


}
