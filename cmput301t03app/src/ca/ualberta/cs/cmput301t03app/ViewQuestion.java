package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewQuestion extends Activity {
	PostController pc = new PostController();
	UserPostCollector upc = new UserPostCollector();
	ArrayList answerList = new ArrayList<Answer>();
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
		ArrayList<String> answerIdList = pc.getQuestion(question_id).getAnswers();
		for (int i = 0; i<answerIdList.size(); i++) {
			Answer a = pc.getAnswer(answerIdList.get(i));
			answerList.add(a);
		}
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
		q_date.setText(q.getDate().toString());

		// Log.d("click", "Contains? " +
		// upc.getFavoriteQuestions().contains(pc.getQuestion(question_id)));
		//
		// if(upc.getFavoriteQuestions().contains(pc.getQuestion(question_id)))
		// {
		// favIcon.setImageResource(R.drawable.ic_fav_yes);
		// }

	}
	
	public void instantiateViews(){
		favIcon = (ImageButton) findViewById(R.id.question_fav_icon);
		upvoteButton = (ImageButton) findViewById(R.id.question_upvote_button);
		upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		answerButton = (Button) findViewById(R.id.question_answer_button);
		answerCounter = (TextView) findViewById(R.id.answer_count);
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
						
						pc.getQuestion(question_id).addAnswer(a.getId());
						pc.getAnswersInstance().add(a);
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
		upc.addFavoriteQuestions(pc.getQuestion(question_id));
		//Log.d("click", "Favs: " + upc.getFavoriteQuestions());
	}

	public void increment_upvote() {
		pc.getQuestion(question_id).upRating();
		Log.d("click",
				Integer.toString(pc.getQuestion(question_id).getRating()));
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

}
