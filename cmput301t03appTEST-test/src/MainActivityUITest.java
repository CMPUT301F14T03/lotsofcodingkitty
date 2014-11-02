/*this test tests our main activity UI functionality*/

import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivityUITest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	Instrumentation instrumentation;
	MainActivity activity;
	ListView listview;
	ActivityMonitor monitor;
	
	
	public MainActivityUITest() {
		super(MainActivity.class);

	}
	
	
	public void setUp() throws Exception{
		//just setting up the things for tests
		super.setUp();
		this.activity = (MainActivity) getActivity();
		this.instrumentation = getInstrumentation();
		this.listview = ((ListView) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_list));
		
	}
	@UiThreadTest
	public void testmakeQuestionDialogBox(){
		assertNotNull(activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
	    AlertDialog dialog = activity.getDialog(); // I create getLastDialog method in MyActivity class. Its return last created AlertDialog
	    assertTrue("dialogbox is showing :)",dialog.isShowing());
		//testing to see if dialogbox opens up
	}
	
	public void testListView(){
		//testing that the listview is actually visible on the screen
		Intent intent = new Intent();
		setActivityIntent(intent);
		View view = (View) this.activity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(view, listview);
	
	}
	

	
	
}
