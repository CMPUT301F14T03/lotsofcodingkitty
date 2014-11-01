import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;

public class SortTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public SortTest() {
		super(MainActivity.class);
	}
	
	// Creates a dummy questions, adds an upvote
	// asserts that the question has one upvote
	
	public void testUpvoteQuestions() {
		
		Question testQuestion = new Question("Title1","TextBody1", "author");
		testQuestion.upRating();
		assertEquals("Question not upvoted properly.", testQuestion.getRating(),1);
	}
	
	// Creates dummy answer, adds and upvote
	// asserts that the question has one upvote
	
	public void testUpvoteAnswers() {
		
		Answer testAnswer = new Answer("body", "author","3");
		testAnswer.upRating();
		assertEquals("Answer not upvoted properly", testAnswer.getRating(), 1);
	}
	
	// Sorts by date.  Newest date first.
	
	public void testSortByDate() {
		ArrayList<Answer> ansList = new ArrayList<Answer>();
		ansList.add(new Answer("body1", "author","1"));
		ansList.add(new Answer("body2", "author","1"));
		ansList.add(new Answer("body3", "author","1"));
		
		// define a method for sorting
		// Collections.sort(ansList);
		assertTrue("Answer list not sorted properly by date.", ansList.get(0).getDate().compareTo(ansList.get(1).getDate())<0);
		assertTrue("Answer list not sorted properly by date.", ansList.get(1).getDate().compareTo(ansList.get(2).getDate())<0);
	}
	
	//Sorts by picture.  Objects with pictures are at the top of the list
	public void testSortByPicture() {
		ArrayList<Question> qList = new ArrayList<Question>();
		qList.add(new Question("Title1","TextBody1", "author"));
		
		
		//qList.add(new Question("Test Subject2", "Test Body2", "testpic.png"));
		
		// define a method for sorting
		// Collections.sort(qList);
		//assertTrue(qList.get(0).hasPicture());
		assertTrue("Question list not sorted properly by picture.", qList.get(0).getSubject().equals("Test Subject2"));
	}
	
	// Sort by score.  Highest score first.
	public void testSortByScore() {
		ArrayList<Question> qList = new ArrayList<Question>();
		qList.add(new Question("Title1","TextBody1", "author"));
		qList.add(new Question("Title2","TextBody2", "author"));
		qList.get(0).upRating();
		// add a sort method
		//qList.sortByRating();
		assertTrue("Question list not sorted properly by score.", qList.get(1).getRating() > qList.get(1).getRating());
	}
}
