package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.LayoutInflater;

public class MainActivity extends Activity {
	ListView lv;
	MainListAdapter mla;
	public ArrayList<Question> questions=new ArrayList<Question>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		populatequestions();
		lv=(ListView) findViewById(R.id.activity_main_question_list);
		mla=new MainListAdapter(this,R.layout.activity_main_question_entity,questions);
		lv.setAdapter(mla);
		
		
	}
	
	public void startup() {
		Button addQuestionButton = (Button) findViewById(R.id.activity_main_question_button);
		
		addQuestionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addQuestionButtonFunction(v);
			}
		});
		
	}
	
	public void populatequestions(){
		Question q=new Question("Dance sucka","Said groove sucka","Jam kick out");
		Question q1=new Question("How do I post question?","What about the body"
						,"Noobsauce");
		q1.upRating();
		questions.add(q);
		questions.add(q1);
		
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
	
	public void addQuestionButtonFunction(View view){
		// Pops up dialog box for adding a question
		
		
		
		LayoutInflater li = LayoutInflater.from(this);
		
		// Get XML file to view
		View promptsView = li.inflate(R.layout.activity_respond, null);
		
		final EditText questionTitle = (EditText) promptsView
				.findViewById(R.id.questionTitle);
		
		final EditText questionBody = (EditText) promptsView
				.findViewById(R.id.questionBody);
		
		// Create a new AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// Link the alertdialog to the XML
		alertDialogBuilder.setView(promptsView);

		// Building the dialog for adding
		alertDialogBuilder
			.setPositiveButton("Ask!", 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {

							String questionTitleString = (String) questionTitle.getText().toString();
							String questionBodyString = (String) questionBody.getText().toString();
							
							Question q = new Question(questionTitleString,questionBodyString, "Test Author");
							questions.add(q);
							
						}
						
					});
		Toast.makeText(this, "Please write your question", Toast.LENGTH_SHORT).show();
	}
}
