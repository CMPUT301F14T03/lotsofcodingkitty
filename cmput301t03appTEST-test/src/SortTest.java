import java.util.ArrayList;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.incomplete.QuestionFilter;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;

/**
 * This tests that the sorting function is working. <br>The sorting functionality is
 * for sorting by date, score, and by pictures.
 * 
 * <br><br>TODO: This still needs to be done when
 * the server function is implemented.
 * 
 * @author rdejesus
 * 
 */
public class SortTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public SortTest() {
		super(MainActivity.class);
	}

	/**
	 * This creates a question and upvotes it and tests 
	 * that the upRating method
	 * is working properly.
	 */
	public void testUpvoteQuestions() {

		Question testQuestion = new Question("Title1", "TextBody1", "author");
		testQuestion.upRating();
		assertEquals("Question not upvoted properly.",
				testQuestion.getRating(), 1);
	}

	/**
	 * This creates an answer and upvotes it 
	 * to be used for later.
	 */
	public void testUpvoteAnswers() {

		Answer testAnswer = new Answer("body", "author", "3");
		testAnswer.upRating();
		assertEquals("Answer not upvoted properly", testAnswer.getRating(), 1);
	}

	/**
	 * This test calls the sort by date method
	 * and then grabs the questions in the list
	 * and makes sure that they are properly sorted
	 */
	public void testSortByDate() {
		QuestionFilter qFilter = new QuestionFilter();
		ArrayList<Question> qList = new ArrayList<Question>();
		@SuppressWarnings("deprecation")
		Question q1 = new Question("String", "String", "String", new Date(2000,
				12, 10));
		@SuppressWarnings("deprecation")
		Question q2 = new Question("String2", "String2", "String2", new Date(
				2010, 12, 10));
		@SuppressWarnings("deprecation")
		Question q3 = new Question("String3", "String3", "String3", new Date(
				2000, 12, 20));
		qList.add(q1);
		qList.add(q2);
		qList.add(q3);
		ArrayList<Question> sortedList = qFilter.sortByDate(qList);

		// define a method for sorting
		// Collections.sort(ansList);

		assertTrue("Answer list not sorted properly by date.", sortedList
				.get(0).equals(q2));
		assertTrue("Answer list not sorted properly by date.", sortedList
				.get(1).equals(q3));
		assertTrue("Answer list not sorted properly by date.", sortedList
				.get(2).equals(q1));

	}
	
	/**
	 * This test calls the sort by picture method
	 * and then tests that the listview is actually sorting the
	 * items on the list by those that have pictures
	 * This still needs to be done
	 */
	// Sorts by picture. Objects with pictures are at the top of the list
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
	
	/**
	 * This test calls the sort by score method and then
	 * tests to see if the listview now shows the listview
	 * in order of score from highest to lowest.
	 */

	public void testSortByScore() {
		QuestionFilter qFilter = new QuestionFilter();
		ArrayList<Question> qList = new ArrayList<Question>();
		qList.add(new Question("Title1", "TextBody1", "author"));
		qList.add(new Question("Title2", "TextBody2", "author"));
		qList.get(1).upRating();
		ArrayList<Question> sortedList = qFilter.sortByUpvote(qList);
		assertTrue("Question list not sorted properly by score.", sortedList
				.get(0).getRating() > sortedList.get(1).getRating());
	}
}
