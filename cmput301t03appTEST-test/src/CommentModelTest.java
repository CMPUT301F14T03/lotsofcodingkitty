import ca.ualberta.cs.cmput301t03app.Comment;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * This test suite is used to test the functionalities of the Comment object
 * @author Eric
 *
 */
public class CommentModelTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public CommentModelTest() {
		super(MainActivity.class);
	}

	// Checks to ensure that the constructor sets attributes correctly by using the retrieval methods
	public void testCommentConstructor()  {
		Comment c1 = new Comment("a comment", "author");
		assertEquals("Comment made is not the expected string",  "a comment", c1.getCommentBody());
	}
	
}
