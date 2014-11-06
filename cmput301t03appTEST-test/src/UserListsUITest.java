import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.views.UserListsActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class UserListsUITest extends ActivityInstrumentationTestCase2<UserListsActivity> {

	public UserListsUITest() {
		super(UserListsActivity.class);
		
	}
	
	public void testItemsOnList(){
			UserListsActivity activity = (UserListsActivity) getActivity();
			ListView answerListView;
			ImageButton favIcon;
			ImageButton upvoteButton;
			ImageButton commentButton;
			TextView upvote_score;
			Button answerButton;
			TextView answerCounter;
			TextView commentCounter;

			answerListView = (ListView) activity.findViewById(R.id.answerListView);
			favIcon = (ImageButton) activity.findViewById(R.id.question_fav_icon);
			upvoteButton = (ImageButton) activity
					.findViewById(R.id.question_upvote_button);
			commentButton = (ImageButton) activity
					.findViewById(R.id.question_comment_icon);
			upvote_score = (TextView) activity
					.findViewById(R.id.question_upvote_score);
			answerButton = (Button) activity
					.findViewById(R.id.question_answer_button);
			answerCounter = (TextView) activity.findViewById(R.id.answer_count);
			commentCounter = (TextView) activity
					.findViewById(R.id.question_comment_count);
			answerListView = (ListView) activity.findViewById(R.id.answerListView);

			// Assert that none of the views are null

			assertNotNull("Item not created for question view", answerListView);
			assertNotNull("Item not created for question view", favIcon);
			assertNotNull("Item not created for question view", upvoteButton);
			assertNotNull("Item not created for question view", commentButton);
			assertNotNull("Item not created for question view", upvote_score);
			assertNotNull("Item not created for question view", answerCounter);
			assertNotNull("Item not created for question view", answerButton);
			assertNotNull("Item not created for question view", commentCounter);

			View mainView = (View) activity.getWindow().getDecorView()
					.findViewById(android.R.id.content);

			// Assert that all of the views are displayed on screen

			ViewAsserts.assertOnScreen(mainView, answerListView);
			ViewAsserts.assertOnScreen(mainView, favIcon);
			ViewAsserts.assertOnScreen(mainView, upvoteButton);
			ViewAsserts.assertOnScreen(mainView, commentButton);
			ViewAsserts.assertOnScreen(mainView, upvote_score);
			ViewAsserts.assertOnScreen(mainView, answerCounter);
			ViewAsserts.assertOnScreen(mainView, answerButton);
			ViewAsserts.assertOnScreen(mainView, commentCounter);

		}
	}

}
