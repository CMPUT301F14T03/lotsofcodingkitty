import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;

public class sortTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public sortTest() {
		super(MainActivity.class);
	}
	
	// Creates a dummy questions, adds an upvote
	// asserts that the question has one upvote
	
	public void testUpvoteQuestions() {
		
		Question testQuestion = new Question();
		testQuestion.setRating(1);
		assertEquals(testQuestion.getRating(),1);
	}
	
	// Creates dummy answer, adds and upvote
	// asserts that the question has one upvote
	
	public void testUpvoteAnswers() {
		
		Answer testAnswer = new Answer();
		testAnswer.setRating(1);
		assertEquals(testAnswer.getRating(), 1);
	}
	
	// Sorts by date.  Newest date first.
	
	public void testSortByDate() {
		ArrayList<Answer> ansList = new ArrayList<Answer>();
		ansList.add(new Answer("Test 1", new Date(2014-9-01)));
		ansList.add(new Answer("Test 2", new Date(2014-8-01)));
		ansList.add(new Answer("Test 3", new Date(2014-10-01)));
		
		// define a method for sorting
		// Collections.sort(ansList);
		assertTrue(ansList.get(0).getDate().compareTo(ansList.get(1).getDate())<0);
		assertTrue(ansList.get(1).getDate().compareTo(ansList.get(2).getDate())<0);
	}
	
	//Sorts by picture.  Objects with pictures are at the top of the list
	public void testSortByPicture() {
		ArrayList<Question> qList = new ArrayList<Question>();
		qList.add(new Question("Test Subject1", "Test Body1"));
		
		
		//qList.add(new Question("Test Subject2", "Test Body2", "testpic.png"));
		
		// define a method for sorting
		// Collections.sort(qList);
		//assertTrue(qList.get(0).hasPicture());
		assertTrue(qList.get(0).getSubject().equals("Test Subject2"));
	}
	
	// Sort by score.  Highest score first.
	public void testSortByScore() {
		ArrayList<Question> qList = new ArrayList<Question>();
		qList.add(new Question("Test Subject1", "Test Body1"));
		qList.add(new Question("Test Subject2", "Test Body2"));
		qList.get(0).setRating(5);
		qList.get(1).setRating(10);
		// add a sort method
		//qList.sortByRating();
		assertTrue(qList.get(1).getRating() > qList.get(1).getRating());
	}
}
