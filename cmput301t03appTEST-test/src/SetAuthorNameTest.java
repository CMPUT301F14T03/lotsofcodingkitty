import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;


public class SetAuthorNameTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public SetAuthorNameTest() {
		super(MainActivity.class);
	}
	
	public void testSetName() {
		PostController aUser = new PostController();
		aUser.setUsername("Test1");
		assertTrue(aUser.getUsername().equals("Test1"));
		String name = "Test2";
		aUser.setUsername(name);
		assertTrue(aUser.getUsername().equals(name));
		assertTrue(aUser.getUsername().equals("Test2"));
	}
}
