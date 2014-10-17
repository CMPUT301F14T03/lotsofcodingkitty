import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;


public class setAuthorNameTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public setAuthorNameTest() {
		super(MainActivity.class);
	}
	
	public void testSetName() {
		PostController aUser = new PostController();
		aUser.setUsername("Test1");
		assert(aUser.getUsername().equals("Test1"));
		String name = "Test2";
		aUser.setUsername(name);
		assert(aUser.getUsername().equals(name));
		assert(aUser.getUsername().equals("Test2"));
	}
}
