import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.controllers.PictureController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
/**
 * This test tests that pictures are being added to questions properly
 * @category Unit testing
 */

public class PictureTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private PictureController pictureController;
	public PictureTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception{
		pictureController = new PictureController(getInstrumentation()
				.getTargetContext());
	}

	/**
	 * This tests adding a picture to question
	 * @throws IOException 
	 */
	public void testAddingPicToQuestion() throws IOException {
		

		Question question = new Question("Title1", "TextBody1", "author");
		int WIDTH = 100, HEIGHT = 100;
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		Bitmap bmp = Bitmap.createBitmap(WIDTH, HEIGHT, conf); // this creates a MUTABLE bitmap
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG,
				64, bytes);
		question.setPicture(bytes.toByteArray());
		assertNotNull("Picture not attached to question.",
				question.getPicture());
		

	}
	
	/**
	 * This tests adding a picture to answer
	 * @throws IOException 
	 */
	public void testAddingPicToAnswer() throws IOException {
		

		Question question = new Question("Title1", "TextBody1", "author");
		int WIDTH = 100, HEIGHT = 100;
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		Bitmap bmp = Bitmap.createBitmap(WIDTH, HEIGHT, conf); // this creates a MUTABLE bitmap
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG,
				64, bytes);
		
		Answer answer = new Answer("I am answering a question", "Me", question.getId());
		answer.setPicture(bytes.toByteArray());
		assertNotNull("Picture not attached to question.",
				answer.getPicture());
		

	}
	/**
	 * This tests that a pictureController is correctly shrinking bitmap
	 * to less than 64KB
	 * @throws IOException 
	 */
	public void testShrinkingBitmap() throws IOException {
		String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + 
                "/TestingPictures";
		File dir = new File(file_path);
		if(!dir.exists())
			dir.mkdirs();
		File file = new File(dir, "sketchpad.png");
		FileOutputStream fOut = new FileOutputStream(file);
		Question question = new Question("Title1", "TextBody1", "author");
		int WIDTH = 100, HEIGHT = 100;
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		Bitmap bmp = Bitmap.createBitmap(WIDTH, HEIGHT, conf); // this creates a MUTABLE bitmap
		bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
		fOut.flush();
		fOut.close();
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG,
				64, bytes);
		question.setPicture(bytes.toByteArray());
		assertNotNull("Picture not attached to question.",
				question.getPicture());
		
		Bitmap bmp1;
		bmp1 = pictureController.ShrinkBitmap(file_path+"/sketchpad.png", 100, 100);
		assertNotNull("Picture null", bmp1);	
		double count = bmp1.getByteCount();
		double imgKB = (count / 1024);
		assertTrue("Image not less than 64 kb.", imgKB < 64);

		

	}
}

