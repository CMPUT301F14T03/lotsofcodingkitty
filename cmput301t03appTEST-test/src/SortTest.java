import java.util.ArrayList;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.incomplete.QuestionFilter;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
/**
 * This tests that the sorting function is working
 * The sorting functionality is for sorting by date, score, and by pictures
 * This still needs to be done when the server function is implemented.
 * @author rdejesus
 *
 */
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
		QuestionFilter qFilter = new QuestionFilter();
		ArrayList<Question> qList = new ArrayList<Question>();
		Question q1 = new Question("String", "String", "String", new Date(2000,12,10));
		Question q2 = new Question("String2", "String2", "String2", new Date(2010, 12, 10));
		Question q3 = new Question("String3", "String3", "String3", new Date(2000, 12, 20));
		qList.add(q1); qList.add(q2); qList.add(q3);
		ArrayList<Question> sortedList = qFilter.sortByDate(qList);
		
		// define a method for sorting
		// Collections.sort(ansList);
		
		assertTrue("Answer list not sorted properly by date.", sortedList.get(0).equals(q2));
		assertTrue("Answer list not sorted properly by date.", sortedList.get(1).equals(q3));
		assertTrue("Answer list not sorted properly by date.", sortedList.get(2).equals(q1));
		
	}
	
	// Sorts by picture.  Objects with pictures are at the top of the list
	// This doesn't do anything right now.
	
	public void testSortByPicture() {

		QuestionFilter qFilter = new QuestionFilter();
		ArrayList<Question> qList = new ArrayList<Question>();
		
		// TODO: add a picture to one of the questions
		
		Question q1 = new Question("String", "String", "String");
		Question q2 = new Question("String2", "String2", "String2");
		Question q3 = new Question("String3", "String3", "String3");
		qList.add(q1);
		qList.add(q2);
		qList.add(q3);
		
		ArrayList<Question> sortedList = qFilter.sortByPic(qList);

		
		assertTrue("Question list not sorted properly by picture.", sortedList
				.get(0).getSubject().equals("String"));
	}
	
	// Sort by score.  Highest score first.
	
	public void testSortByScore() {
		QuestionFilter qFilter = new QuestionFilter();
		ArrayList<Question> qList = new ArrayList<Question>();
		qList.add(new Question("Title1","TextBody1", "author"));
		qList.add(new Question("Title2","TextBody2", "author"));
		qList.get(1).upRating();
		ArrayList<Question> sortedList = qFilter.sortByUpvote(qList);
		assertTrue("Question list not sorted properly by score.", sortedList.get(0).getRating() > sortedList.get(1).getRating());
	}
}
