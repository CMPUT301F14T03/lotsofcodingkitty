import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;
import android.test.ActivityInstrumentationTestCase2;


public class SearchTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public SearchTest() {
		super(MainActivity.class);
	}
	
	public void testSearch() {
		
		ArrayList<Question> qList = new ArrayList<Question>();
		String searchWord = "test";
		Question q1 = new Question("This is test question?","TextBody1");
		Question q2 = new Question("This question should not be returned?","TextBody2");
		ArrayList<Question> searchResults = new ArrayList<Question>();
		
		
		qList.add(q1);
		qList.add(q2);
		
		//Save questionArray into the server
		
		
		
		// searchResults = sbd.search(searchWord); 
		//Searches for searchWord in the list of questions in the server
		
		assertNotNull(searchResults);
		
		Question q3 = searchResults.get(0);
		assertEquals(q3.getSubject(),q1.getSubject());
		
		
	}

}
