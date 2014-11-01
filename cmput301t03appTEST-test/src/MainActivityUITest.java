/*this test tests our main activity UI functionality*/

import ca.ualberta.cs.cmput301t03app.MainActivity;
import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;


public class MainActivityUITest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	Instrumentation instrumentation;
	Activity activity;
	ListView listview;
	
	public MainActivityUITest() {
		super(MainActivity.class);
	}
	
	
	protected void setUp() throws Exception{
		//just setting up the things we will need for tests
		super.setUp();
		instrumentation = getInstrumentation();
		activity = getActivity();
		listview = ((ListView) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_list));
		
	}
	
	private void addQuestion(String text){
		//assertNotNull((ListView) activity.findViewById(ca.ualberta.cs.cmput301t03app.R.id.activity_main_question_list));
		MainActivity ma = getActivity();
	//	ArrayAdapter<Question> aa = ma.getAdapter();
		
	
	}
}
