import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;


public class ConnectivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity activity;

	public ConnectivityTest() {
		super(MainActivity.class);
	}
	
	public void setUp() throws Exception{
		super.setUp();
		this.activity = (MainActivity) getActivity();
		
	}

	public void testConnectivity(){
		PostController pc = new PostController(activity);
		boolean check = pc.checkConnectivity();
		assertTrue("this should be true", check);
	}
	
	
}
