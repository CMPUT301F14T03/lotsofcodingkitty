import android.R;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;



public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	public void testViewByUpVotes(){
		super(MainActivity.class);
		QuestionListManager qlm=new QuestionListManager();
		ArrayList<Question> questionList=new ArrayList<Question>();
		questionList=qlm.load();
		assertTrue("Upvoted questions dont exist",upvoteSort(questionList)>0);
	}
	public void testViewAnswersToQuestions(){
		super(MainActivity.class);
		EditText et=(EditText) ViewById(R.id.answerstoquestions);
		String answerstoquestions=et.getText().toString();
		asserFalse("The string is null", answers==null);
	}
	public void testCachedAnswersAndQuestions(){
		super(MainActivity.class);
		UserManager um=new UserManager;
		ArrayList<Question> cachedAnswers=new ArrayList<Question>();
		ArrayList<Question> cachedQuestions=new ArrayList<Question>();
		cachedAnswers=um.loadCacheAnswers();
		cachedQuestions=um.loadCacheQuestions();
		assertTrue("There are no cached answers",cachedAnswers.size()>0);
		assertTrue("There are no cached questions",cachedQuestions.size()>0);
	public void testAttachPictureToQuestions(){
		super(MainActivity.class);
		Question question=new Question();
		// Used from http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
		BufferedImage img = null;
		try {
			// The image IO will be receiving from images from phone.
			question.picture = ImageIO.read(new File("picture.jpg"));
		} catch (IOException e) {
		}
		assertTrue("Question does not have a picture",question.picture==NULL);
	}
	public void testAttachPictureToAnswers(){
		super(MainActivity.class);
		Answer answer=new Answer();
		// Used from http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
		BufferedImage img = null;
		try {
			// The image IO will be receiving from images from phone.
		    answer.picture = ImageIO.read(new File("picture.jpg"));
		} catch (IOException e) {
		}
		assertTrue("Question does not have a picture",answer.picture==NULL);
	}
}
