package ca.ualberta.cs.cmput301t03app.views;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.R.id;
import ca.ualberta.cs.cmput301t03app.R.layout;
import ca.ualberta.cs.cmput301t03app.R.menu;
import ca.ualberta.cs.cmput301t03app.adapters.MainListAdapter;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Question;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UserListsActivity extends Activity {
	
	private int userListMode;
	private TextView user_list_title;
	private PostController pc = new PostController(this);
	private MainListAdapter mla;
	private ArrayList<Question> userQuestionList;
	private ListView userListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_lists);
		Bundle extras = getIntent().getExtras();
		userListMode = extras.getInt("userListMode");
		user_list_title = (TextView) findViewById(R.id.user_list_title);
		userListView = (ListView) findViewById(R.id.user_question_list);
		
		switch (userListMode) {
		case 0:
			user_list_title.setText("F A V O R I T E S");
			userQuestionList = pc.getFavoriteQuestions();
			break;
		case 1:
			user_list_title.setText("C A C H E D");
			userQuestionList = pc.getReadQuestions();
			break;
		case 2:
			user_list_title.setText("T O  R E A D");
			userQuestionList = pc.getToReadQuestions();
			break;
		case 3:
			user_list_title.setText("M Y  Q U E S T I O N S");
			userQuestionList = pc.getUserPostedQuestions();
			break;
		default:
			user_list_title.setText("F A V O R I T E S");
			userQuestionList = pc.getFavoriteQuestions();
			break;
		}
		
		mla = new MainListAdapter(this, R.layout.activity_main_question_entity, userQuestionList);
		userListView.setAdapter(mla);
		setListeners();
	}
	
	public void setListeners() {
		userListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				toQuestionActivity(position);
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_lists, menu);
		return true;
	}
	
	
	public void toQuestionActivity(int position) {
		Intent i = new Intent( this, ViewQuestion.class );
		i.putExtra("question_id", userQuestionList.get(position).getId());
		if (pc.getQuestion(userQuestionList.get(position).getId()) == null) {
			pc.addQuestion(userQuestionList.get(position));
		}
		pc.addReadQuestion(userQuestionList.get(position));
		startActivity(i);
		
	}
	

}
