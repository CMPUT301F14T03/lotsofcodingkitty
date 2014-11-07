import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;

/**
 * This test suite is used to test the functionalities of the Comment object
 * @author Eric
 *
 */
public class CommentModelTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public CommentModelTest() {
		super(MainActivity.class);
	}

	/**Tests the get methods
	 * Tests that the comment being made is correct
	 */
	public void testCommentConstructor()  {
		Comment c1 = new Comment("a comment", "author");
		Date date= new Date();
		assertEquals("Comment made is not the expected string",  "a comment", c1.getCommentBody());
		assertEquals("Comment has not the expected author","author",c1.getAuthor());
		assertEquals("Dates are not the same",date,c1.getDate());
	}
	/**
	 * Tests that when making two comments they are saved as different objects
	 */
	public void testTwoDifferentComments(){
		Comment c = new Comment("comment1","author1");
		Comment c1 = new Comment("comment2","author2");
		
		assertFalse("The items are the same", c==c1);
		assertFalse("The authors are the same for both comments",c.getAuthor().equals(c1.getAuthor()));
		assertFalse("The comments are the same for both comments", c.getCommentBody().equals(c1.getCommentBody()));
		assertFalse("The dates are not the same",c.getDate()==c1.getDate());
		
		
	}
	
}
