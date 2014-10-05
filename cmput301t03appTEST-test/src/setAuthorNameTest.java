import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.MainActivity;


public class setAuthorNameTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public setAuthorNameTest() {
		super(MainActivity.class);
	}
	
	public void testSetName() {
		User aUser = new User();
		aUser.setName("Test1");
		assertEquals(aUser.getName().equals("Test1"));
		String name = "Test2";
		aUser.setName(name);
		assertEquals(aUser.getName().equals(name));
		assertEquals(aUser.getName().equals("Test2"));
	}
}
