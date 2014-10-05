import android.R;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;



public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	public void testViewByUpVotes(){
		QuestionListManager qlm=new QuestionListManager;
		ArrayList<Question> questionList=new ArrayList<Question>;
		questionList=qlm.load();
		assertTrue("Upvoted questions dont exist",upvoteSort(questionList)>0);
	}
	public void testViewAnswersToQuestions(){
		EditText et=(EditText) ViewById(R.id.answerstoquestions);
		String answerstoquestions=et.getText().toString();
		asserFalse("The string is null", answers==null);
	}
	public void testCachedAnswersAndQuestions(){
		UserManager um=new UserManager;
		ArrayList<Question> cachedAnswers=new ArrayList<Question>;
		ArrayList<Question> cachedQuestions=new ArrayList<Question>;
		cachedAnswers=um.loadCacheAnswers();
		cachedQuestions=um.loadCacheQuestions();
		assertTrue("There are no cached answers",cachedAnswers.size()>0);
		assertTrue("There are no cached questions",cachedQuestions.size()>0);
}
