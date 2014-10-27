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
		TextView q_body = (TextView) findViewById(R.id.question_text_body);
		TextView q_author = (TextView) findViewById(R.id.question_author);
		TextView q_date = (TextView) findViewById(R.id.post_timestamp);
		q_title.setText(q.getSubject());
		q_body.setText(q.getBody());
		q_author.setText(q.getAuthor());
		q_date.setText(q.getDate().toString());
		
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
