package ca.ualberta.cs.cmput301t03app;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView lv;
	MainListAdapter mla;
	public ArrayList<Question> questions = new ArrayList<Question>();
	PostController pc = new PostController();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView questionList = (ListView) findViewById(R.id.activity_main_question_list);

		questionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub

				Log.d("click", "click " + position);
				
				toQuestionActivity(position);
				
			}
		});

		populatequestions();
		setupAdapter();

	}

	public void populatequestions() {
		// This is a temporary method that hardcodes some questions for testing.

		PostController pc = new PostController();
		
		Question q = new Question(
				"Test question 0: What does this question ask?",
				"Said groove sucka", "Test Author 0");
		Question q1 = new Question(
				"Test question 1: This is a longer test question to see how it will look when wrapped on multiple lines. How do I post question?",
				"What about the body", "Test Author 1");
		Question q2 = new Question(
				"Test question 2: What does this question ask? This is slightly longer",
				"Said groove sucka", "Test Author 2");
		Question q3 = new Question(
				"Test question 3: What does this question ask? Some variety",
				"Said groove sucka", "Test Author 3");
		Question q4 = new Question(
				"Test question 4: What does this question ask? How about this one?",
				"Said groove sucka", "Test Author 4");
		Question q5 = new Question("Test question 5: Boolean?",
				"Said groove sucka", "Test Author 5");
		Question q6 = new Question("Test question 6: How are you?",
				"Said groove sucka", "Test Author 6");
		Question q7 = new Question("Test question 7: Boolean == Boolean?",
				"Said groove sucka", "Test Author 7");

		for (int i = 0; i < 130; i++) {
			q.upRating();
		}

		for (int i = 0; i < 97; i++) {
			q1.upRating();
		}

		for (int i = 0; i < 98; i++) {
			q2.upRating();
		}

		for (int i = 0; i < 99; i++) {
			q3.upRating();
		}
		for (int i = 0; i < 200; i++) {
			q4.upRating();
		}
		for (int i = 0; i < 201; i++) {
			q5.upRating();
		}
		for (int i = 0; i < 17; i++) {
			q6.upRating();
		}

		for (int i = 0; i < 209; i++) {
			q7.upRating();
		}

		//pc.addQuestion(q);
		
//		pc.addQuestion(q1);
//		pc.addQuestion(q2);
//		pc.addQuestion(q3);
//		pc.addQuestion(q4);
//		pc.addQuestion(q5);
//		pc.addQuestion(q6);
//		pc.addQuestion(q7);
		
		questions.add(q);
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
		questions.add(q4);
		questions.add(q5);
		questions.add(q6);
		questions.add(q7);

	}

	public void setupAdapter() {
		lv = (ListView) findViewById(R.id.activity_main_question_list);
		mla = new MainListAdapter(this, R.layout.activity_main_question_entity,
				pc.getQuestionsInstance());
		lv.setAdapter(mla);
	}

	public void toQuestionActivity(int position) {
		
		Intent i = new Intent( this, ViewQuestion.class );
		i.putExtra("question_id", pc.getQuestionsInstance().get(position).getId());
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	public void addQuestionButtonFunction(View view) {
		// Pops up dialog box for adding a question

		LayoutInflater li = LayoutInflater.from(this);

		// Get XML file to view
		View promptsView = li.inflate(R.layout.activity_respond, null);

		final EditText questionTitle = (EditText) promptsView
				.findViewById(R.id.questionTitle);

		final EditText questionBody = (EditText) promptsView
				.findViewById(R.id.questionBody);

		final EditText userName = (EditText) promptsView
				.findViewById(R.id.UsernameRespondTextView);

		// Intent intent = new Intent(this, Respond.class);
		// startActivity(intent);

		// Create a new AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// Link the alertdialog to the XML
		alertDialogBuilder.setView(promptsView);

		// Building the dialog for adding
		alertDialogBuilder.setPositiveButton("Ask!",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						String questionTitleString = (String) questionTitle
								.getText().toString();
						String questionBodyString = (String) questionBody
								.getText().toString();
						String userNameString = (String) userName.getText()
								.toString();

						Question q = new Question(questionTitleString,
								questionBodyString, userNameString);
						pc.getQuestionsInstance().add(q);

						mla.updateAdapter(pc.getQuestionsInstance());
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

		Toast.makeText(this, "Please write your question", Toast.LENGTH_SHORT)
				.show();
	}
}
