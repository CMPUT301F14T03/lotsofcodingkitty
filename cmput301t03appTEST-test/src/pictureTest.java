import ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity;
import android.test.ActivityInstrumentationTestCase2;


public class pictureTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public pictureTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	
	public void ensurePicLessThan64k() {
		Image img1 = new Image("filepath");
		
		double img1Bytes = img1.length();
		
		double imgKB= (img1Bytes / 1024);
		
		assertEquals("Same size", imgKB, 64);
		
	}
	
	

}
