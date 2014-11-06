import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.views.UserHome;
import ca.ualberta.cs.cmput301t03app.views.UserListsActivity;


public class UserHomeUITest extends ActivityInstrumentationTestCase2<UserHome>{
	Instrumentation instrumentation;
	UserHome activity;
	ActivityMonitor monitor;	//this monitors any newly opened activities
	
	
	public UserHomeUITest() {
		super(UserHome.class);
	}

	public void setUp() throws Exception{
		//just setting up the things for tests
		super.setUp();
		this.activity = (UserHome) getActivity();
		this.instrumentation = getInstrumentation();
	}

	public void testUserHomeButtonClick(){
		//this tests that if you click on an item a new activity opens up that is ViewQuestion activiy
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(UserListsActivity.class.getName(), null, false);
		assertNotNull(activity.findViewById(R.id.user_fav_button));
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
			@Override
			public void run() {
				((Button)activity.findViewById(R.id.user_fav_button)).performClick();
			}
			});
	instrumentation.waitForIdleSync();
	 UserListsActivity newActivity = (UserListsActivity) instrumentation.waitForMonitorWithTimeout(activityMonitor, 5);
	 assertNotNull(newActivity);	//check that new activity has been opened
	 Bundle extras = newActivity.getIntent().getExtras();
	 int button_id = extras.getInt("userListMode");
	 Log.v("this is id", Integer.toString(button_id));
	 assertEquals("The button sent correct information", 0, button_id);
	  //viewActivityUItest should be testing that the intent that has been passed to this new activity is correct
	} 
}