import java.io.File;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;


public class PictureTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public PictureTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	
	public void ensurePicLessThan64k() {
		File img1 = new File("filepath");
		
		double img1Bytes = img1.length();
		
		double imgKB= (img1Bytes / 1024);
		
		assertEquals("Same size", imgKB, 64);
		
	}
	
	public void testAttachPictureToQuestions() {

		File picture = new File("picture_path");
		Question question = new Question();
		question.setPicture(picture);
		assertEquals(question.getPicture(),picture);
		
	}
	
	public void testAttachPictureToAnswers() {

		File picture = new File("picture_path");
		Answer answer = new Answer();
		answer.setPicture(picture);
		assertEquals(answer.getPicture(),picture);

	}
	

}
