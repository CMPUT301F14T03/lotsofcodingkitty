
package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/*
 * This class is the main access point to the application. 
 * 
 * @author
 * 
 * */


public class MainActivity extends Activity {
	ListView lv;
	MainListAdapter mla;
	PostController pc = new PostController(this);
	

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
		
		setupAdapter();
		
		/* FOR TESTING PURPOSES ONLY
		 */
		mockServer.initServer(this);
		ArrayList<Question> qList = mockServer.getMainList();
		for (int i=0; i < qList.size(); i++) {
			pc.getQuestionsInstance().add(qList.get(i));
		}
		mla.updateAdapter(pc.getQuestionsInstance());
		/* END TEST BLOCK
		 */
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mla.updateAdapter(pc.getQuestionsInstance());
		
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
		pc.addReadQuestion(pc.getQuestionsInstance().get(position));
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
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

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
						pc.addUserPost(q);
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
		
		TextWatcher textwatcher = new TextWatcher(){
			private void handleText(){
				final Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				if(questionTitle.getText().length() == 0){
					button.setEnabled(false);
				}
				else if(questionBody.getText().length() == 0){
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
				// TODO Auto-generated method stub
				handleText();
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				// TODO Auto-generated method stub
				
			}
		};
			
		
				questionTitle.addTextChangedListener(textwatcher);
				questionBody.addTextChangedListener(textwatcher);
				userName.addTextChangedListener(textwatcher);
				
				
		Toast.makeText(this, "Please write your question", Toast.LENGTH_SHORT)
				.show();
	}
}


