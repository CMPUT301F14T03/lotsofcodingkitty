package ca.ualberta.cs.cmput301t03app.views;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.adapters.MainListAdapter;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Question;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 *         This is the activity for looking user selected set/list of questions as
 *         selected from the User Home activity. It is started by the User Home activity
 *         which passes an extra message in the intent that is used to determine
 *         which local saved list to load.  Can show a list of favorite questions,
 *         cached questions, to be read questions and user posted questions.  If a 
 *         selected list from the User Home activity is empty, then the ListView is
 *         empty in this activity
 * 
 */

public class UserListsActivity extends Activity
{

	private int userListMode;
	private TextView user_list_title;
	private PostController pc = new PostController(this);
	private MainListAdapter mla;
	private ArrayList<Question> userQuestionList;
	private ListView userListView;
	
	/**
	 * Aside from the standard onCreate, this method will also get the extra from the intent
	 * (the extra is called userListMode) and will:<br>
	 * - Display the corresponding header for the list to be displayed based on the intent extra value.<br>
	 * - Get the corresponding list from the post controller based on the intent extra value.<br><br>
	 * 
	 * A new list adaptor (activity_main_question_entity) is created and the ListView is set with this
	 * new list adaptor.
	 * 
	 * A new listener for the adaptor is also created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_lists);
		Bundle extras = getIntent().getExtras();
		userListMode = extras.getInt("userListMode");
		user_list_title = (TextView) findViewById(R.id.user_list_title);
		userListView = (ListView) findViewById(R.id.user_question_list);

		switch (userListMode)
		{
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

		mla = new MainListAdapter(this, R.layout.activity_main_question_entity,
				userQuestionList);
		userListView.setAdapter(mla);
		setListeners();
	}

	/**
	 * Setting a onItemClickListener for the adaptor which will allow the user to interact
	 * with the Question object placed inside the adaptor.
	 */
	public void setListeners()
	{

		userListView.setOnItemClickListener(new OnItemClickListener()
		{
			/**
			 * When the Question object is clicked, the position of the adaptor in
			 * the list view is determined and passed as an argument to the toQuestionActivity method.
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id)
			{

				toQuestionActivity(position);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_lists, menu);
		return true;
	}

	/**
	 * This method is called when a Question object is clicked.  The Question's position
	 * in the list is used to determine which Question is selected and to get that Question's
	 * ID.<br><br>
	 * 
	 * Checks if the Question is in the PC's (post controller) sublist of questions; if it is not,
	 * then the Question is added to the sublist.  NOTE: This is a quick work-around for a bug, as
	 * the question needs to exist in the PC's sublist in order to view it.  BEING WORKED ON.<br><br>
	 * 
	 * Starts the ViewQuestion activity with the ID passed as an extra in the intent (the ID is used
	 * to determine which Question's detail to display).
	 * 
	 * @param position is the position of the Question in the ListView that was clicked on
	 */

	public void toQuestionActivity(int position)
	{

		Intent i = new Intent(this, ViewQuestion.class);
		String qId = userQuestionList.get(position).getId();
		i.putExtra("question_id", qId);
		if (pc.getQuestion(qId) == null)
		{
			pc.addQuestion(userQuestionList.get(position));
		}
		pc.addReadQuestion(userQuestionList.get(position));
		startActivity(i);

	}

}
