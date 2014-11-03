import java.io.File;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;


public class PictureTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public PictureTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	// Creates a new file from <filepath>,
	// gets the size of the file
	// converts to kilobytes
	// and asserts that the size of the file is less than 64
	
	public void ensurePicLessThan64k() {
		File img1 = new File("filepath");
		
		double img1Bytes = img1.length();
		
		double imgKB= (img1Bytes / 1024);
		
		assertTrue("Image not less than 64 kb.", imgKB<64);
		
	}
	
	public void testAttachPictureToQuestions() {

	// Creates new picture from "picture_path"
	// Creates a new question object
	// Adds the picture to the question file
	// attribute. Asserts that the questions file
	// attribute is the same as the picture we specified.

		File picture = new File("picture_path");
		Question question = new Question("Title1","TextBody1", "author");
		question.setPicture(picture);
		assertEquals("Picture not attached correctly to question.", question.getPicture(),picture);
		
	}
	
	// Same test as above.
	
	public void testAttachPictureToAnswers() {

		File picture = new File("picture_path");
		Answer answer = new Answer("answer", "a author","1");
		answer.setPicture(picture);
		assertEquals("Picture not attached correctly to answer.", answer.getPicture(),picture);

	}
	

}
