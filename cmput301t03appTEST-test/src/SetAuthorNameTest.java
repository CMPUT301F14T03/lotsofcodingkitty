import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;


public class SetAuthorNameTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public SetAuthorNameTest() {
		super(MainActivity.class);
	}
	
	// Creates a new post controller containing a username "Test1" then asserts
	// that the username of the user is "Test1". It then takes a new name "Test2"
	// Sets the username once more and asserts the username returned by the user
	// is equal to "Test2"
	
	
	public void testSetName() {
		PostController aUser = new PostController(getInstrumentation().getTargetContext());
		aUser.setUsername("Test1");
		assertTrue("Username not set properly.", aUser.getUsername().equals("Test1"));
		String name = "Test2";
		aUser.setUsername(name);
		assertTrue("Username not same as expected.", aUser.getUsername().equals("Test2"));
	}
}
