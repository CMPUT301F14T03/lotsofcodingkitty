import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.views.UserHome;
import ca.ualberta.cs.cmput301t03app.views.UserListsActivity;

/**
 * This tests that the UserHome User Interface is working properly to integrate all 
 * functionalities of the app.
 * 
 * 
 * @author rdejesus
 * @category Integration/System Testing
 */

public class UserHomeUITest extends ActivityInstrumentationTestCase2<UserHome> {
	Instrumentation instrumentation;
	UserHome activity;
	ActivityMonitor monitor; // this monitors any newly opened activities

	public UserHomeUITest() {
		super(UserHome.class);
	}

	/**
	 * This sets up the variables needed for the rest of the tests.
	 * 
	 */
	public void setUp() throws Exception {
		// just setting up the things for tests
		super.setUp();
		this.activity = (UserHome) getActivity();
		this.instrumentation = getInstrumentation();
	}

	/**
	 * Test to make sure that View items in the layout are not null and can be
	 * seen on the display.
	 */
	public void testUIviewsShowUp() {
		TextView title;
		TextView description;
		Button favoriteb;
		Button cacheb;
		Button toReadb;
		Button qButton;

		title = (TextView) activity.findViewById(R.id.user_home_title);
		description = (TextView) activity
				.findViewById(R.id.user_home_description);
		favoriteb = (Button) activity.findViewById(R.id.user_fav_button);
		cacheb = (Button) activity.findViewById(R.id.user_cached_button);
		toReadb = (Button) activity.findViewById(R.id.user_toRead_button);
		qButton = (Button) activity.findViewById(R.id.user_questions_button);

		assertNotNull("Title not created for question view", title);
		assertNotNull("Description not created for question view", description);
		assertNotNull("Favorite button not created for question view", favoriteb);
		assertNotNull("Cache button not created for question view", cacheb);
		assertNotNull("ToRead button not created for question view", toReadb);
		assertNotNull("question button not created for question view", qButton);

		View mainView = (View) activity.getWindow().getDecorView()
				.findViewById(android.R.id.content);

		// Assert that all of the views are displayed on screen
		assertNotNull(mainView);
		ViewAsserts.assertOnScreen(mainView, title);
		ViewAsserts.assertOnScreen(mainView, description);
		ViewAsserts.assertOnScreen(mainView, favoriteb);
		ViewAsserts.assertOnScreen(mainView, cacheb);
		ViewAsserts.assertOnScreen(mainView, toReadb);
		ViewAsserts.assertOnScreen(mainView, qButton);

	}

	/**
	 * Testing that when the favorites button is clicked, a new activity pops up.<br>
	 * Also tests that the mode sent to that activity (through intent) is the correct
	 * one (in this case 0 meaning favorites).
	 * 
	 */
	public void testUserHomeButtonClick() {
		// this tests that if you click on an item a new activity opens up that
		// is ViewQuestion activiy
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(
				UserListsActivity.class.getName(), null, false);
		assertNotNull("The button is being called and returning a null",activity.findViewById(R.id.user_fav_button));
		getInstrumentation().runOnMainSync(new Runnable() { // clicking on an
															// item
															// automatically
					@Override
					public void run() {
						((Button) activity.findViewById(R.id.user_fav_button))
								.performClick();
					}
				});
		instrumentation.waitForIdleSync();
		UserListsActivity newActivity = (UserListsActivity) instrumentation
				.waitForMonitorWithTimeout(activityMonitor, 5);
		assertNotNull(newActivity); // check that new activity has been opened
		Bundle extras = newActivity.getIntent().getExtras();
		int button_id = extras.getInt("userListMode");
		assertEquals("The button did not send correct information", 0, button_id);
		// viewActivityUItest should be testing that the intent that has been
		// passed to this new activity is correct
	}
}