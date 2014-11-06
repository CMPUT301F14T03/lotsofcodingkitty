import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.UserListsActivity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


public class UserListsUITest extends ActivityInstrumentationTestCase2<UserListsActivity> {
	Instrumentation instru;
	public UserListsUITest() {
		super(UserListsActivity.class);
		
	}

	public void testItemsOnList(){
			instru = getInstrumentation();
			UserListsActivity activity = (UserListsActivity) getActivity();
			ListView userListView;
			TextView title;

			userListView = (ListView) activity.findViewById(R.id.user_question_list);
			title = (TextView) activity.findViewById(R.id.user_list_title);

			// Assert that none of the views are null

			assertNotNull("Item not created for question view", userListView);
			assertNotNull("Item not created for question view", title);
			
			View mainView = (View) activity.getWindow().getDecorView()
					.findViewById(android.R.id.content);

			// Assert that all of the views are displayed on screen
			assertNotNull(mainView);
			ViewAsserts.assertOnScreen(mainView, userListView);
			ViewAsserts.assertOnScreen(mainView, title);
			
			//making sure that the title is correct
			assertEquals("Title is not the same","F A V O R I T E S", title.getText());
			
			

		}
}
