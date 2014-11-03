
import java.util.Date;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.ViewComment;
import ca.ualberta.cs.cmput301t03app.views.ViewQuestion;


/* This Junit test tests the ViewComment activity UI objects
 * @author Dominik Pachala
 */
public class ViewCommentUITest extends ActivityInstrumentationTestCase2<ViewComment>{
	Instrumentation instrumentation;
	ActivityMonitor monitor;

	public ViewCommentUITest(){
		super(ViewComment.class);
	}
	
	public void setUp() throws Exception{
		//just setting up the things for tests
		super.setUp();
		this.instrumentation = getInstrumentation();
		
	}
	// The following tests focus when the mode is set to Question comments.
	// This tests make sure that the view displays the correct
	// information from a question. Checks the Title,Date,Author.
	public void testDisplayQuestionInfo(){
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
	// This tests if the view displays the comment count properly. 
	// This one tests specifically if it's zero.
	public void testDisplayQuestionCommentCountZero(){
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Question q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		
		Intent intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_QUESTION_KEY);
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		
		setActivityIntent(intent);
		ViewComment vc = getActivity();
		
		TextView cc = (TextView) vc.findViewById(R.id.comment_count);
		assertTrue("Comment count is not zero.",cc.getText().equals("Comments: 0"));
	}
	// Tests if the Comment Count is one.
	public void testDisplayQuestionCommentCountOne(){
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Question q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		Comment c = new Comment("String","String");
		pc.addCommentToQuestion(c, q.getId());
		
		Intent intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_QUESTION_KEY);
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		
		setActivityIntent(intent);
		ViewComment vc = getActivity();
		
		TextView cc = (TextView) vc.findViewById(R.id.comment_count);
		assertTrue("Comment count is not 1.",cc.getText().equals("Comments: 1"));
	}
	
	// This test sees if the items are on screen.
	
	public void testView(){
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Question q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		Intent intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_QUESTION_KEY);
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		
		setActivityIntent(intent);
		ViewComment vc = getActivity();
		TextView title = 
				(TextView) vc.findViewById(R.id.comment_title);
		TextView timeStamp = 
				(TextView) vc.findViewById(R.id.comment_post_timestamp);
		TextView author = 
				(TextView) vc.findViewById(R.id.comment_post_author);
		ListView lv = 
				(ListView) vc.findViewById(R.id.commentListView);

		Button b =
				(Button)vc.findViewById(R.id.comment_button);
		ViewAsserts.assertOnScreen(vc.getWindow().getDecorView(), lv);
		ViewAsserts.assertOnScreen(vc.getWindow().getDecorView(), title);
		ViewAsserts.assertOnScreen(vc.getWindow().getDecorView(), timeStamp);
		ViewAsserts.assertOnScreen(vc.getWindow().getDecorView(), author);
		ViewAsserts.assertOnScreen(vc.getWindow().getDecorView(), b);
	}
	
	// This comment tests if the comments body shows correctly with only one comment.
	public void testCommentBodyIsCorrectWithOneComment(){
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Question q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		Comment c = new Comment("String","String");
		pc.addCommentToQuestion(c, q.getId());
		
		Intent intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_QUESTION_KEY);
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		
		setActivityIntent(intent);
		ViewComment vc = getActivity();
		ListView lv = 
				(ListView) vc.findViewById(R.id.commentListView);
		
		assertTrue("The comment body is not the same",lv.getItemAtPosition(0).equals("String"));	
	}
	// Tests if the view shows two comments correctly.
	public void testCommentBodyIsCorretWithTwoComments(){
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Question q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		Comment c = new Comment("String","String");
		Comment c1 = new Comment("String1","String1");
		pc.addCommentToQuestion(c, q.getId());
		pc.addCommentToQuestion(c1, q.getId());
		
		Intent intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_QUESTION_KEY);
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		
		setActivityIntent(intent);
		ViewComment vc = getActivity();
		
		ListView lv = 
				(ListView) vc.findViewById(R.id.commentListView);

		assertTrue("The comment body is not the same at position 0",lv.getItemAtPosition(0).equals("String"));
		assertTrue("The comment body is not the same at position 1",lv.getItemAtPosition(1).equals("String1"));	
	}
	
	// This adds a comment to a question and tests to see if it is shown.
	// Also tests the dialog box.
	
	// THIS IS BREAKING NEED SOLVING. CANT GET THE DIALOG TO SHOW AND ADD A COMMENT.
	public void testCommentIsAddedAndShown(){
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Question q = new Question(qTitle,qBody,qAuthor);
		pc.addQuestion(q);
		
		Intent intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,ViewQuestion.COMMENT_ON_QUESTION_KEY);
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		
		setActivityIntent(intent);
		ViewComment vc = getActivity();
		
		assertNotNull(((Button) vc.findViewById(R.id.comment_button)));
//		((Button) vc.findViewById(R.id.comment_button)).performClick();
//		AlertDialog dialog = vc.getDialog();
//	    EditText commentBody = (EditText) 
//				dialog.findViewById(R.id.postBody);
//		EditText commentAuthor = (EditText) 
//				dialog.findViewById(R.id.UsernameRespondTextView);
		 
//		commentBody.setText("CommentBody");
//		commentAuthor.setText("CommentAuthor");
//		dialog.getButton(
//				DialogInterface.BUTTON_POSITIVE).performClick();
		ListView lv = 
				(ListView)vc.findViewById(R.id.commentListView);
		assertEquals("New comment is not added to ListView", lv.getItemAtPosition(0),"CommentBody");
		 
	}

}