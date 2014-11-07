import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.views.UserListsActivity;
/**
 * This tests the UserList UI
 * shows that the lists of the items that the
 * user has saved locally.
 * 
 *@category Integration/System Testing
 */
public class UserListsUITest extends
	ActivityInstrumentationTestCase2<UserListsActivity> {
	Instrumentation instrumentation;
	UserListsActivity activity;
	ActivityMonitor monitor; // this monitors any newly opened activities

	public UserListsUITest() {
		super(UserListsActivity.class);
	}
	/**
	 * setting up the variables needed for the test
	 */
	public void setUp() throws Exception {
		// just setting up the things for tests
		super.setUp();
		Intent intent = new Intent();
		intent.putExtra("userListMode", 0);
		setActivityIntent(intent);
		this.activity = (UserListsActivity) getActivity();
		this.instrumentation = getInstrumentation();
	}

	/**
	 * Testing views on UserLists View exist and can be seen. <br> Also tests that 
	 * the title at top of the activity is
	 * correct.
	 */
	@UiThreadTest
	public void testItemsOnList() {
		UserListsActivity activity = (UserListsActivity) getActivity();
		ListView userListView;
		TextView title;

		userListView = (ListView) activity
				.findViewById(R.id.user_question_list);
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

		// making sure that the title is correct
		assertEquals("Title is not the same", "F A V O R I T E S",
				title.getText());

	}
}
