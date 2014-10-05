import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.MainActivity;

public class sortTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public sortTest() {
		super(MainActivity.class);
	}
	
	// Sorts by date.  Newest date first.
	public void testSortByDate() {
		ArrayList<Answer> ansList = new ArrayList<Answer>();
		ansList.add(new Answer("Test 1", new Date(2014-09-01)));
		ansList.add(new Answer("Test 2", new Date(2014-08-01)));
		ansList.add(new Answer("Test 3", new Date(2014-10-01)));
		ansList.sortByDate();
		assertTrue(ansList.get(0).getDate().compareTo(ansList.get(1).getDate())<0);
		assertTrue(ansList.get(1).getDate().compareTo(ansList.get(2).getDate())<0);
	}
	
	//Sorts by picture.  Objects with pictures are at the top of the list
	public void testSortByPicture() {
		ArrayList<Question> qList = new QuestionList<Question>();
		qList.add(new Question("Test Subject1", "Test Body1"));
		qList.add(new Question("Test Subject2", "Test Body2", "testpic.png"));
		qList.sortByPicture();
		assertTrue(qList.get(0).hasPicture());
		assertTrue(qList.get(0).getSubject().equals("Test Subject2"));
	}
	
	// Sort by score.  Highest score first.
	public void testSortByScore() {
		ArrayList<Question> ArrayList<Question> qList = new QuestionList<Question>();
		qList.add(new Question("Test Subject1", "Test Body1"));
		qList.add(new Question("Test Subject2", "Test Body2"));
		qList.get(0).changeMockRating(5);
		qList.get(1).changeMockRating(10);
		qList.sortByRating();
		assertTrue(qList.get(1).getRating() > qList.get(1).getRating());
	}
}
