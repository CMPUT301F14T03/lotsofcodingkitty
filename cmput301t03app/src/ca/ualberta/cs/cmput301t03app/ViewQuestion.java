package ca.ualberta.cs.cmput301t03app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewQuestion extends Activity {
	PostController pc = new PostController();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_question);
		
		Bundle extras = getIntent().getExtras();
		String question_title = extras.getString("question_id");
		
		setQuestionText(question_title);
	}

	public void setQuestionText(String ID) {
		Question q=pc.getQuestion(ID);
		TextView q_title = (TextView) findViewById(R.id.question_title);
		q_title.setText(q.getSubject());
		
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
