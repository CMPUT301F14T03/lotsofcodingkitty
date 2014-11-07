/*this test tests our main activity UI functionality*/

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.adapters.MainListAdapter;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import ca.ualberta.cs.cmput301t03app.views.ViewQuestion;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
	 * Testing that when cancel is clicked, but fields are not empty No question should be added
	 */
	public void testAddnoQuestion(){		//testing if clicking cancel works if adding a question
		activity = (MainActivity) getActivity();
		assertNotNull("button not null",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		int oldCount = adapter.getCount();
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
				@Override
				public void run() {
					assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
				    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
				     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
							dialog.findViewById(R.id.questionTitle);
					 EditText questionBody = (EditText) 
							dialog.findViewById(R.id.questionBody);
					 EditText userName = (EditText) 
							dialog.findViewById(R.id.UsernameRespondTextView);
					questionTitle.setText("poopoo");	//setting them up with arguments
					questionBody.setText("1newQuestion");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_NEGATIVE).performClick();
				}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("new question added", oldCount, newCount); 

	}
	
	/**
	 * Testing adding 1 question. The question should then show up on the listView
	 */
	public void testAddQuestion(){		//testing if adding 1 question works
		activity = (MainActivity) getActivity();
		assertNotNull("button not null",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		int oldCount = adapter.getCount();
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
				@Override
				public void run() {
					assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
				    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
				     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
							dialog.findViewById(R.id.questionTitle);
					 EditText questionBody = (EditText) 
							dialog.findViewById(R.id.questionBody);
					 EditText userName = (EditText) 
							dialog.findViewById(R.id.UsernameRespondTextView);
					questionTitle.setText("poopoo");	//setting them up with arguments
					questionBody.setText("1newQuestion");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
				}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("new question added", oldCount+1, newCount); 

	}
	
	/**
	 * Testing adding 2 Questions at once.
	 */
	public void testAdd2Question(){		//testing if adding 1 question works
		activity = (MainActivity) getActivity();
		assertNotNull("button not null",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		int oldCount = adapter.getCount();
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
				@Override
				public void run() {
					assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
				    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
				     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
							dialog.findViewById(R.id.questionTitle);
					 EditText questionBody = (EditText) 
							dialog.findViewById(R.id.questionBody);
					 EditText userName = (EditText) 
							dialog.findViewById(R.id.UsernameRespondTextView);
					questionTitle.setText("poopoo");	//setting them up with arguments
					questionBody.setText("1newQuestion");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
					assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
					questionTitle.setText("poopoo");	//setting them up with arguments
					questionBody.setText("1newQuestion");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
				}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("new question added", oldCount+2, newCount); 

	}
	
	/**
	 * Testing adding 30 questions 
	 */
	public void testAdd30Question(){		//testing if adding 1 question works
		activity = (MainActivity) getActivity();
		assertNotNull("button not null",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		int oldCount = adapter.getCount();
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
				@Override
				public void run() {
					assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
				    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
				     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
							dialog.findViewById(R.id.questionTitle);
					 EditText questionBody = (EditText) 
							dialog.findViewById(R.id.questionBody);
					 EditText userName = (EditText) 
							dialog.findViewById(R.id.UsernameRespondTextView);
					questionTitle.setText("poopoo");	//setting them up with arguments
					questionBody.setText("1newQuestion");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
					for(int i=1; i <= 29; i++){
					assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
					questionTitle.setText("poopoo");	//setting them up with arguments
					questionBody.setText("1newQuestion");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
				}}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("new question added", oldCount+30, newCount); 

	}
	/**
	 * Making sure ListView is showing up on the screen
	 */
	public void testListView(){
		//testing that the listview is actually visible on the screen
		Intent intent = new Intent();
		setActivityIntent(intent);
		View view = (View) this.activity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(view, listview);
	
	}
	
	/**
	 * Testing that when an question is clicked in the ListView it opens up a new activity with that question 
	 * sent as an intent to be shown.
	 */
	public void testzQuestionDetailClick(){
		//this tests that if you click on an item a new activity opens up that is ViewQuestion activiy
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewQuestion.class.getName(), null, false);
		PostController pc = new PostController(activity);
		int i = pc.getQuestionsInstance().size();
		final int lastquestionindex = i - 1;
		assertNotNull(listview);
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
			@Override
			public void run() {
				listview.performItemClick(listview.getAdapter().getView(lastquestionindex,null,null),
						lastquestionindex, listview.getAdapter().getItemId(lastquestionindex));		//clicking on second item on list
			}
			});
	instrumentation.waitForIdleSync();
	  ViewQuestion newActivity = (ViewQuestion) instrumentation.waitForMonitorWithTimeout(activityMonitor, 5);
	  assertNotNull(newActivity);	//check that new activity has been opened
	  Bundle extras = newActivity.getIntent().getExtras();
	  String question_id = extras.getString("question_id");
	  String title = pc.getQuestion(question_id).getSubject();
	  assertEquals("correct Question being sent to be viewed", title, "poopoo");//checking correct question sent
	  newActivity.finish();	//close activity
	  //viewActivityUItest should be testing that the intent that has been passed to this new activity is correct
	}
	
	
	
}


