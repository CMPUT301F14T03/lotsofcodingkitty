import java.io.File;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.MainActivity;


public class pictureTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public pictureTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	
	public void ensurePicLessThan64k() {
		File img1 = new File("filepath");
		
		double img1Bytes = img1.length();
		
		double imgKB= (img1Bytes / 1024);
		
		assertEquals("Same size", imgKB, 64);
		
	}
	
	

}
