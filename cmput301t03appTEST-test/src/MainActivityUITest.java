/*this test tests our main activity UI functionality*/

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.adapters.MainListAdapter;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import ca.ualberta.cs.cmput301t03app.views.ViewQuestion;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.DialogInterface;
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
	ActivityMonitor monitor;	//this monitors any newly opened activities
	
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
	
	/**
	 * Testing to see if dialog box opens up when the "Ask question" button is clicked
	 */
	@UiThreadTest
	public void testmakeQuestionDialogBox(){
		assertNotNull(activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));//making sure that button is visible on screen
		((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
	    AlertDialog dialog = activity.getDialog(); // I create getLastDialog method in MyActivity class. Its return last created AlertDialog
	    assertTrue("dialogbox is showing :)",dialog.isShowing());
		
	}
	
	/**
	 * This is used as part of the test for integration of UI and PC.
	 * Ensures that the "Ask question" button exists in the UI and allows other test cases to use this method
	 * to add Question objects. 
	 * @param title Title of the question
	 * @param body Body of the question
	 * @param name Name of the author
	 */
	@UiThreadTest
	public void makeQuestion(String title, String body, String name){	
		assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
	    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
	     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
				dialog.findViewById(R.id.questionTitle);
		 EditText questionBody = (EditText) 
				dialog.findViewById(R.id.questionBody);
		 EditText userName = (EditText) 
				dialog.findViewById(R.id.UsernameRespondTextView);
		questionTitle.setText(title);	//setting them up with arguments
		questionBody.setText(body);
		userName.setText(name);
		dialog.getButton(
				 DialogInterface.BUTTON_POSITIVE).performClick();	//clicking to add the question
	}
	
	/**
	 * Testing if adding a question works
	 */
	@UiThreadTest
	public void testAddQuestion(){		
		assertNotNull(activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		int oldCount = adapter.getCount();
		makeQuestion("why?","why?","rjynn");
		int newCount = adapter.getCount();
		assertEquals("new question added", newCount, oldCount); 
	}
	public void testListView(){
		//testing that the listview is actually visible on the screen
		Intent intent = new Intent();
		setActivityIntent(intent);
		View view = (View) this.activity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(view, listview);
	
	}

	public void testEmptyServer(){
		//TODO: create test to see if empty server is being dealt with correctly
	}
	
	public void testzQuestionDetailClick(){
		//this tests that if you click on an item a new activity opens up that is ViewQuestion activiy
		 ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewQuestion.class.getName(), null, false);
		assertNotNull(listview);
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
			@Override
			public void run() {
				listview.performItemClick(listview.getAdapter().getView(1,null,null),
						1, listview.getAdapter().getItemId(1));		//clicking on second item on list
			}
			});
	instrumentation.waitForIdleSync();
	  ViewQuestion newActivity = (ViewQuestion) instrumentation.waitForMonitorWithTimeout(activityMonitor, 5);
	  assertNotNull(newActivity);	//check that new activity has been opened
	  newActivity.finish();	//close activity
	  //viewActivityUItest should be testing that the intent that has been passed to this new activity is correct
	}
	
	
	
}


