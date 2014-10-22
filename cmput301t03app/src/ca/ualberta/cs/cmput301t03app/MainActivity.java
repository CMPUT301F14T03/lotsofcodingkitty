package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
		Toast.makeText(this, "Please write your question", Toast.LENGTH_SHORT).show();
	}
}
