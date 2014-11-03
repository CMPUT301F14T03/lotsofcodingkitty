import java.util.Date;

import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.ViewComment;
import ca.ualberta.cs.cmput301t03app.ViewQuestion;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

// This tests the Answer mode in View comment

public class ViewCommentUIAnswerModeTest extends
		ActivityInstrumentationTestCase2<ViewComment>
{
	Instrumentation instrumentation;
	ActivityMonitor monitor;
	Intent intent;
	PostController pc;
	Question q;

	public ViewCommentUITest(){
		super(ViewComment.class);
	}
	
	public void setUp() throws Exception{
		//just setting up the things for tests
		super.setUp();
		this.instrumentation = getInstrumentation();
		this.intent=new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_ANSWER_KEY);
		intent.putExtra(ViewQuestion.ANSWER_ID_KEY, q.getId());
		pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		
	}
	
	public void testDisplayAnswerInfo(){
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Date date = new Date();
		Question q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		
		Intent intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_QUESTION_KEY);
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		
		setActivityIntent(intent);
		ViewComment vc = getActivity();
		
		TextView title = (TextView) vc.findViewById(R.id.comment_title);
		TextView timeStamp = (TextView) vc.findViewById(R.id.comment_post_timestamp);
		TextView author = (TextView) vc.findViewById(R.id.comment_post_author);
		assertEquals("Titles are not the same!",title.getText(),"Q: "+ qTitle);
		assertEquals("Authors are not the same!",author.getText(),"By" + qAuthor);
		assertEquals("Dates are not the same!",timeStamp.getText(),"Posted: " + date.toString());
		
	}
	
	
		
		
}