import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;

/**
 * This test suite is used to test the functionality of the Question model
 * @category Unit Testing
 */
public class QuestionModelTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public QuestionModelTest() {
		super(MainActivity.class);
	}

	/**
	 * Testing that constructor of question model is working properly and that
	 * the getter methods are returning proper information about the correct
	 * question
	 */
	// Checks if all the attributes get set correctly by the constructor through
	// use of retrieval methods
	public void testQuestionConstructor() {
		Question q1 = new Question("a subject", "a body", "a author");
		Date date = new Date();
		assertEquals("Subject is not 'a subject'", "a subject", q1.getSubject());
		assertEquals("Body is not 'a body'", "a body", q1.getBody());
		assertEquals("author is not 'a author'", "a author", q1.getAuthor());
		assertEquals("Rating is not 0", 0, q1.getRating());
		assertEquals("Date is not set correctly", date, q1.getDate());
	}

	/**
	 * Tests that the method upRating increments the Question score by one point.
	 */
	// Checks if incrementing the rating works
	public void testIncreaseQuestionScore() {
		Question q1 = new Question("a subject", "a body", "a author");
		assertEquals("Rating for question is not 0 something is wrong in Test1", 0, q1.getRating());
		q1.upRating();
		assertEquals("Rating is not 1 so not incremented properly", 1, q1.getRating());
	}

	/**
	 * Tests the method upRating works for multiple calls.
	 */
	public void testIncreaseQuestionScoreby30() {
		Question q1 = new Question("a subject", "a body", "a author");
		assertEquals("Rating for question is not 0 something is wrong in Test2", 0, q1.getRating());
		for (int i = 0; i <= 30; i++) {
			q1.upRating();
		}
		assertEquals("Rating is not 31 so upvote on question not incremented properly", 31, q1.getRating());
	}

	/**
	 * Tests that the question is adding answers properly and that if the
	 * answers have the same attributes, it is added as different objects.
	 */

	public void testAddAnswer() {
		Question q1 = new Question("a subject", "a body", "a author");
		Answer a1 = new Answer("a body", "a author", "1");
		q1.addAnswer(a1);
		assertSame("answer object created is not the correctly being made", a1, q1.getAnswers().get(0));

		Answer a2 = new Answer("a body", "a author", "2");
		q1.addAnswer(a2);
		assertNotSame("a1 is a2 in Test1", a1, a2); // Checks to ensure a1 is not a2
		assertNotSame("a1 is a2 in Testing getters", q1.getAnswers().get(0), q1.getAnswers()
				.get(1)); // Now checks to ensure this is also the case for the
							// answers added.

		q1.addAnswer(a1);
		assertSame("index 1 and 2 dont have the same answer object", q1
				.getAnswers().get(0), q1.getAnswers().get(2));
	}

	/**
	 * Testing that the count of the answer is being incremented properly when
	 * an answer is being added to the question. This tests adding two questions.
	 */
	// Check if the counting of questions works properly
	public void testAnswerCount() {
		Question q1 = new Question("a subject", "a body", "a author");
		Answer a1 = new Answer("a body", "a author", "1");
		q1.addAnswer(a1);
		assertEquals("Answer count  is not 1 in Test1", 1, q1.countAnswers());
		Answer a2 = new Answer("a body", "a author", "1");
		q1.addAnswer(a2);
		assertEquals("Answer count is not 2 in Test1", 2, q1.countAnswers());
	}

	/**
	 * Testing that the count of the answer is being 
	 * incremented properly when
	 * an answer is being added to the question. 
	 * This tests adding three questions.
	 */
	public void testAnswerCountby3() {
		Question q1 = new Question("a subject", "a body", "a author");
		Answer a1 = new Answer("a body", "a author", "1");
		q1.addAnswer(a1);
		assertEquals("Answer count  is not 1 in Test2", 1, q1.countAnswers());
		Answer a2 = new Answer("a body", "a author", "1");
		q1.addAnswer(a2);
		assertEquals("Answer count is not 2 in Test2", 2, q1.countAnswers());
		Answer a3 = new Answer("a body", "a author", "1");
		q1.addAnswer(a3);
		assertEquals("Answer count is not 3 in Test2", 3, q1.countAnswers());
	}

	/**
	 * Testing that the question model 
	 * is adding the comments properly and if
	 * the comments have the same attributes they are 
	 * added as separate objects.
	 */

	// Same conditions as testAddAnswer()
	public void testAddComment() {
		Question q1 = new Question("a subject", "a body", "a author");
		Comment c1 = new Comment("a comment", "author");
		q1.addComment(c1);
		assertSame("comment object is the not correctly creating comment", c1, q1.getComments().get(0));

		Comment c2 = new Comment("a comment", "author");
		q1.addComment(c2);
		assertNotSame("c1 is equal c2", c1, c2);
		assertNotSame("c1 is equal c2", q1.getComments().get(0), q1.getComments()
				.get(1));

		q1.addComment(c2);
		assertSame("index 1 and 2 have the same comment object", q1
				.getComments().get(1), q1.getComments().get(2));
	}
	// This tests the getAnserByIDmethod by making sure it returns the proper
	// answers given the answer keys.
	// public void testGetAnswerByIDMethod(){
	// Question q1 = new Question("a subject", "a body", "a author");
	// Answer a1 = new Answer("a body", "a author","1");
	// Answer a2 = new Answer("Another body", "some other author","1");
	//
	// assertTrue("An empty list doesnt return null",q1.getAnswerByID("dummy")==null);
	// q1.addAnswer(a1);
	// assertTrue("Doesnt return null with something in the list",q1.getAnswerByID("dummy")==null);
	// assertSame("The answer returned is not the same that was added",q1.getAnswerByID(a1.getId()),a1);
	//
	// q1.addAnswer(a2);
	// assertNotSame("The answers are the same",q1.getAnswerByID(a1.getId()),q1.getAnswerByID(a2.getId()));
	//
	// }
}
