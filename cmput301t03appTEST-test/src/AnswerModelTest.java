import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.Comment;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * This test suite is used to test all the functionalities of the Answer model
 * @author Eric
 *
 */
public class AnswerModelTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public AnswerModelTest() {
		super(MainActivity.class);
	}
	// Checks that the constructor fills attributes properly and that the attributes can be retrieved.
	public void testAnswerConstructor() {
		Answer a1 = new Answer("a body", "a author","1");
		assertEquals("body is 'a body'", "a body", a1.getAnswer());
		assertEquals("author is 'a author'",  "a author", a1.getAuthor());
		assertEquals("rating is 0", 0, a1.getRating());
		assertTrue("Id is a string",a1.getId() instanceof String);
		//assertTrue("ParentID is 1",a1.getParentID()=="1");
	}
	
	// Checks that the rating of the answer is incremented properly
	public void testIncreaseAnswerScore() {
		Answer a1 = new Answer("a body", "a author","1");
		assertEquals("rating is 0", 0, a1.getRating());
		a1.upRating();
		assertEquals("rating is 1", 1, a1.getRating());
	}
	
	// Checks if a comment can be added by seeing if the object created is the same object in the comment list of the answer
	// Also checks to see if two comments with the same attributes are added as separate objects.
	public void testAddComment() {
		Answer a1 = new Answer("a body", "a author","1");
		Comment c1 = new Comment("a comment", "comment author guy");
		a1.addComment(c1);
		assertSame("comment object is the same", c1, a1.getComments().get(0));
		
		Comment c2 = new Comment("a comment", "other guy");
		a1.addComment(c2);
		assertNotSame("c1 is not c2", c1, c2);  // Check to ensure objects with same attributes are different instances
		assertNotSame("c1 is not c2", a1.getComments().get(0), a1.getComments().get(1)); // Same check for objects in the list
		
		a1.addComment(c2);
		assertSame("index 1 and 2 have the same comment object", a1.getComments().get(1), a1.getComments().get(2));
		
		
	}
	
}
