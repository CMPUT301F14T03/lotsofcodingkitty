import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import android.test.ActivityInstrumentationTestCase2;


public class SearchTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public SearchTest() {
		super(MainActivity.class);
	}
	
	public void testSearch() {
		
		// in real search should have a data manager class here
		
		ArrayList<Question> qList = new ArrayList<Question>();
		String searchWord = "test";
		Question q1 = new Question("Title1","TextBody1", "author");
		Question q2 = new Question("Title1","TextBody1", "author");
		ArrayList<Question> searchResults = new ArrayList<Question>();
		
		
		qList.add(q1);
		qList.add(q2);
		
		//Save questionArray into the server
		
		//data manager is called to save questions
		
		// searchResults = sbd.search(searchWord); 
		//Searches for searchWord in the list of questions in the server
		
		// elasticsearch query will go here
		
		assertNotNull(searchResults);
		
		Question q3 = searchResults.get(0);
		
		//asserts that the results obtained are the expected results of the search query
		
		assertEquals("Search results inconsistent with expected results.",q3.getSubject(),q1.getSubject());
		
		
	}

}
