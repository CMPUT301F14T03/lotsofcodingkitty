
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.adapters.MainListAdapter;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Question;
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
/**
 * This tests the MainActivity User Interface
 * it uses the buttons and views to add questions and view questions.
 * @category Integration/System Testing
 */

public class MainActivityUITest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	Instrumentation instrumentation;
	MainActivity activity;
	ListView listview;
	ActivityMonitor monitor;	//this monitors any newly opened activities
	
	public MainActivityUITest() {
		super(MainActivity.class);
	}
	/**
	 * This sets up all the required fields for this test
	 */
	public void setUp() throws Exception{
		super.setUp();
		this.activity = (MainActivity) getActivity();
		this.instrumentation = getInstrumentation();
		this.listview = ((ListView) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_list));
	}
	
	/**
	 * Testing to see if dialog box pops up and is visible on the screen
	 *  when the "Ask question" button is clicked.
	 * 
	 */
	@UiThreadTest
	public void testmakeQuestionDialogBox(){
		assertNotNull(activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));//making sure that button is visible on screen
		((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
	    AlertDialog dialog = activity.getDialog(); // I create getLastDialog method in MyActivity class. Its return last created AlertDialog
	    assertTrue("The dialog is not showing up when button is clicked",dialog.isShowing());
	}
	
	/**Testing that when the dialog pops up to add a question 
	 * and you enter information, but click cancel there should be
	 * no added question to the listView
	 * @throws InterruptedException 
	 * 
	 */
	public void testAddnoQuestion() throws InterruptedException{		//testing if clicking cancel works if adding a question
		activity = (MainActivity) getActivity();
		assertNotNull("The button is null when returned",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		Thread.sleep(2000);
		int oldCount = adapter.getCount();
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
				@Override
				public void run() {
					assertNotNull("the button is null when returned",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
				    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
				     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
							dialog.findViewById(R.id.questionTitle);
					 EditText questionBody = (EditText) 
							dialog.findViewById(R.id.questionBody);
					 EditText userName = (EditText) 
							dialog.findViewById(R.id.UsernameRespondTextView);
					questionTitle.setText("This is a test question title that should not show up");	//setting them up with arguments
					questionBody.setText("This is a test question body that should not show up");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_NEGATIVE).performClick();
				}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("The question was added to the list and shouldn't have been", oldCount, newCount); 

	}
	
	/**
	 * Testing that when you click the add question button,
	 * a dialog box pops up and when the fields are completed 
	 * and you click "OK"
	 * the question should show up on the screen and the adapter 
	 * should have a new Question object and that question object should be correct
	 * @throws InterruptedException 
	 */
	public void testAddQuestion() throws InterruptedException{		//testing if adding 1 question works
		activity = (MainActivity) getActivity();
		assertNotNull("The button returned null when called",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		Thread.sleep(2000);
		int oldCount = adapter.getCount();
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
				@Override
				public void run() {
					assertNotNull("The button returned null when called",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
				    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
				     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
							dialog.findViewById(R.id.questionTitle);
					 EditText questionBody = (EditText) 
							dialog.findViewById(R.id.questionBody);
					 EditText userName = (EditText) 
							dialog.findViewById(R.id.UsernameRespondTextView);
					questionTitle.setText("This is a test question title when a question should be added");	//setting them up with arguments
					questionBody.setText("This is a test question body when a question should be added");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
				}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("New question was not added to list", oldCount+1, newCount); 
		
		//this snippet tests that the question just added to the list is the correct question
		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		int i = pc.getQuestionsInstance().size();
		final int lastquestionindex = i - 1;
		Question qCheck = (Question) adapter1.getItem(lastquestionindex);
		assertEquals("The question just added has the correct information", "This is a test question title when a question should be added", qCheck.getSubject() );

	}
	
	/**
	 * Testing adding 2 Questions
	 * at a time will not break the adapter
	 * @throws InterruptedException 
	 */
	public void testAdd1Question() throws InterruptedException{		//testing if adding 1 question works
		activity = (MainActivity) getActivity();
		assertNotNull("button not null",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		Thread.sleep(2000);
		int oldCount = adapter.getCount();
		getInstrumentation().runOnMainSync(new Runnable(){	//clicking on an item automatically
				@Override
				public void run() {
					assertNotNull("The button returned null when called",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
				    AlertDialog dialog = activity.getDialog();	//grabbing the dialog box that opens up when button clicked	
				     EditText questionTitle = (EditText) 		//grabbing all edit texts in teh dialog box
							dialog.findViewById(R.id.questionTitle);
					 EditText questionBody = (EditText) 
							dialog.findViewById(R.id.questionBody);
					 EditText userName = (EditText) 
							dialog.findViewById(R.id.UsernameRespondTextView);
					questionTitle.setText("This is a test question title when a question should be added");	//setting them up with arguments
					questionBody.setText("This is a test question body when a question should be added");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
					assertNotNull("The button returned null when called",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
					questionTitle.setText("This is a test question title when a question should be added");	//setting them up with arguments
					questionBody.setText("This is a test question body when a question should be added");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
				}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("new question added", oldCount+2, newCount); 
		ServerDataManager sdm = new ServerDataManager();
		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		int size = pc.getQuestionsInstance().size();
		sdm.deleteQuestion(pc.getQuestionsInstance().get(0).getId());


	}
	
	/**
	 * Testing adding 30 questions 
	 * will not break the adapter and should work
	 * @throws InterruptedException 
	 */
	public void testAdd30Question() throws InterruptedException{		//testing if adding 1 question works
		activity = (MainActivity) getActivity();
		assertNotNull("button not null",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
		MainListAdapter adapter = activity.getAdapter();
		Thread.sleep(2000);
		int oldCount = adapter.getCount();
		//assertTrue("oldcount is not 10",oldCount==10);
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
					for(int i=1; i <= 30; i++){
					assertNotNull("this should be a button",activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button));
					((Button) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_button)).performClick();
					questionTitle.setText("This is a test question title when a question should be added");	//setting them up with arguments
					questionBody.setText("This is a test question body when a question should be added");
					userName.setText("me");
					dialog.getButton(
							 DialogInterface.BUTTON_POSITIVE).performClick();
				}}
				});
		instrumentation.waitForIdleSync();
		MainListAdapter adapter1 = activity.getAdapter();
		int newCount = adapter1.getCount();
		assertEquals("new question added", oldCount+30, newCount); 
		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		ServerDataManager sdm = new ServerDataManager();
		int size = pc.getQuestionsInstance().size();
		for (int i = 0; i<size; i++){
			sdm.deleteQuestion(pc.getQuestionsInstance().get(i).getId());
		}

	}
	/**
	 * Making sure ListView is showing up on the screen
	 * and not null
	 */
	public void testListView(){
		//testing that the listview is actually visible on the screen
		ListView questionList;
		questionList = (ListView) activity.findViewById(R.id.activity_main_question_list);
		assertNotNull("ListView not created for question view", questionList);
		Intent intent = new Intent();
		setActivityIntent(intent);
		View view = (View) this.activity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(view, listview);
	
	}
	
	/**
	 * Testing that when an question is clicked in the ListView it opens up a new activity with the
	 * correct question being shown in the new activity.
	 * @throws InterruptedException 
	 */
	public void testzQuestionDetailClick() throws InterruptedException{
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewQuestion.class.getName(), null, false);
		assertNotNull("The listView is null",listview);
		//creating a new question here
		instrumentation.waitForIdleSync();
		Thread.sleep(2000);
		PostController pc = new PostController(activity);
		int i = pc.getQuestionsInstance().size();
		final int lastquestionindex = i-1;
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
	  String testTitle = pc.getQuestionsInstance().get(lastquestionindex).getSubject();
	  assertEquals("Correct Question not being added to view", title, testTitle);//checking correct question sent
	  newActivity.finish();	//close activity
	  //viewActivityUItest should be testing that the intent that has been passed to this new activity is correct
	}
	
	
	
}


