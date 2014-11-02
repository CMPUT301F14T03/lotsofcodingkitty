/*this test tests our main activity UI functionality*/

import ca.ualberta.cs.cmput301t03app.MainActivity;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivityUITest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	Instrumentation instrumentation;
	Activity activity;
	ListView listview;
	Activity myactivity;
	public MainActivityUITest() {
		super(MainActivity.class);
		this.myactivity = super.getActivity(); 
	}
	
	
	public void setUp() throws Exception{
		//just setting up the things we will need for tests
		super.setUp();
		instrumentation = getInstrumentation();
		activity = getActivity();
		listview = ((ListView) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_list));
		
	}
	public void testmakeQuestion(){
		//testing to see if dialogbox opens up first
		assertNotNull(listview);
		((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
		instrumentation.waitForIdleSync();
		assertTrue((myactivity.getDialog().isShowing())); //issue here :(
		
	}
	
	public void testListView(){
		//testing that the listview is actually visible on the screen
		Intent intent = new Intent();
		setActivityIntent(intent);
		View view = (View) this.activity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(view, listview);
	
	}
	

	
	
}
