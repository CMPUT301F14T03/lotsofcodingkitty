import java.io.File;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.DataManager;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;



public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	public MainActivityTest() {
		super(MainActivity.class);
	}
	
//  Commented out test related to viewing things
//	
//	public void testViewAnswersToQuestions(){
//
//		EditText et=(EditText) ViewById(R.id.answerstoquestions);
//		String answers=et.getText().toString();
//		assertFalse("The string is null",answers==null);
//	}
	
	public void testCachedAnswersAndQuestions(){

		DataManager dm = new DataManager();
		ArrayList<Answer> cachedAnswers=new ArrayList<Answer>();
		ArrayList<Question> cachedQuestions=new ArrayList<Question>();
		cachedAnswers=dm.localLoadAnswers();
		cachedQuestions=dm.localLoadQuestions();
		assertTrue("There are no cached answers",cachedAnswers.size()>0);
		assertTrue("There are no cached questions",cachedQuestions.size()>0);
		
	}
		
	public void testAttachPictureToQuestions(){

		File picture = new File("picture_path");
		Question question = new Question();
		question.setPicture(picture);
		assertEquals(question.getPicture(),picture);
		
	}
	
	public void testAttachPictureToAnswers(){

		File picture = new File("picture_path");
		Answer answer = new Answer();
		answer.setPicture(picture);
		assertEquals(answer.getPicture(),picture);

	}
}
