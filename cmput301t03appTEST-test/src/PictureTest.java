/**
 * This test tests that adding pictures to an answer or Question
 * is done properly and can be seen and saved properly
 */

import java.io.File;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;

public class PictureTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public PictureTest() {
		super(MainActivity.class);
	}

	/**
	 * this creates a new file path and creates an image that will be asserted
	 * if it is less than 64KB if added to the comment or answer (STILL NEED TO
	 * BE WRITTEN)
	 */

	public void ensurePicLessThan64k() {
		File img1 = new File("filepath");

		double img1Bytes = img1.length();

		double imgKB = (img1Bytes / 1024);

		assertTrue("Image not less than 64 kb.", imgKB < 64);

	}

	/**
	 * This tests that if a new picture is created and added to a question, that
	 * the question is correctly adding the picture file and that the getter
	 * method is working properly
	 */
	public void testAttachPictureToQuestions() {

		// Creates new picture from "picture_path"
		// Creates a new question object
		// Adds the picture to the question file
		// attribute. Asserts that the questions file
		// attribute is the same as the picture we specified.

		File picture = new File("picture_path");
		Question question = new Question("Title1", "TextBody1", "author");
		question.setPicture(picture);
		assertEquals("Picture not attached correctly to question.",
				question.getPicture(), picture);

	}

	/**
	 * This tests that if a new picture is created and added to an answer, that
	 * the question is correctly adding the picture file and that the getter
	 * method is working properly
	 */

	public void testAttachPictureToAnswers() {

		File picture = new File("picture_path");
		Answer answer = new Answer("answer", "a author", "1");
		answer.setPicture(picture);
		assertEquals("Picture not attached correctly to answer.",
				answer.getPicture(), picture);

	}

}
