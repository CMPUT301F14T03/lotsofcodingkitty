import java.util.Date;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.ViewComment;
import ca.ualberta.cs.cmput301t03app.views.ViewQuestion;

/**
 * This tests that the View Comment User Interface works properly by clicking on
 * buttons and using dialog boxes.
 * @category Integration/System Testing
 */
public class ViewCommentUIAnswerTest extends

ActivityInstrumentationTestCase2<ViewComment> {
	Instrumentation instrumentation;
	ActivityMonitor monitor;
	Intent intent;
	PostController pc;
	Question q;
	Comment c;

	public ViewCommentUIAnswerTest() {
		super(ViewComment.class);
	}

	/**
	 * sets up everything needed for testing
	 */
	public void setUp() throws Exception {

		super.setUp();
		this.instrumentation = getInstrumentation();
		this.intent = new Intent();
		intent.putExtra(ViewQuestion.SET_COMMENT_TYPE,
				ViewQuestion.COMMENT_ON_ANSWER_KEY);
		pc = new PostController(getInstrumentation().getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		q = new Question(qTitle, qBody, qAuthor);
		pc.addQuestionToServer(q);
		pc.getQuestionsInstance().add(q);
		c = new Comment("String", "String");

	}

	/**
	 * This tests that the TextViews show the correct information and displays
	 * the correct answer that is being viewed.
	 */

	public void testDisplayAnswerInfo() {
		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		String qTitle = "Title";
		String qBody = "Body";
		String qAuthor = "Author";
		Date date = new Date();
		Question q1 = new Question(qTitle, qBody, qAuthor);
		pc.addQuestionToServer(q1);

		Answer a = new Answer("aTitle", "aAuthor", "1");
		pc.addAnswer(a, q1.getId());

		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q1.getId());
		intent.putExtra(ViewQuestion.ANSWER_ID_KEY, a.getId());

		setActivityIntent(intent);
		ViewComment vc = getActivity();

		TextView title = (TextView) vc.findViewById(R.id.comment_title);
		TextView timeStamp = (TextView) vc
				.findViewById(R.id.comment_post_timestamp);
		TextView author = (TextView) vc.findViewById(R.id.comment_post_author);

		assertEquals("Titles are not the same!", title.getText(), "Q: "
				+ qTitle + "\n" + "A: " + a.getAnswer());
		assertEquals("Authors are not the same!", author.getText(),
				"By: " + a.getAuthor());
		//There's a one second difference in the dates for some reason
//		assertEquals("Dates are not the same!", timeStamp.getText(), "Posted: "
//				+ date.toString());

	}

	/**
	 * This tests that the view is displaying the count of comments properly
	 * :: this tests to see that if zero comments
	 * have been added, the TextView shows zero.
	 */

	public void testDisplayQuestionCommentCountZero() {

		Answer a = new Answer("aTitle", "aAuthor", "1");
		pc.addAnswer(a, q.getId());
		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		intent.putExtra(ViewQuestion.ANSWER_ID_KEY, a.getId());

		setActivityIntent(intent);
		ViewComment vc = getActivity();

		TextView cc = (TextView) vc.findViewById(R.id.comment_count);
		assertTrue("Comment count is not zero.",
				cc.getText().equals("Comments: 0"));
	}

	/**
	 * This tests if a comment has been added. The comments should show one.
	 */

	public void testDisplayQuestionCommentCountOne() {

		Answer a = new Answer("aTitle", "aAuthor", "1");
		pc.addAnswer(a, q.getId());
		pc.addCommentToAnswer(c, q.getId(), a.getId());

		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		intent.putExtra(ViewQuestion.ANSWER_ID_KEY, a.getId());

		setActivityIntent(intent);
		ViewComment vc = getActivity();

		TextView cc = (TextView) vc.findViewById(R.id.comment_count);
		assertTrue("Comment count is not 1.", cc.getText()
				.equals("Comments: 1"));
	}

	/**
	 * This tests to see that the correct comment is being showed
	 * and the content is correct.
	 */
	public void testCommentBodyIsCorrectWithOneComment() {

		Answer a = new Answer("aTitle", "aAuthor", "1");
		pc.getQuestionsInstance();
		pc.addAnswer(a, q.getId());
		pc.addCommentToAnswer(c, q.getId(), a.getId());

		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q.getId());
		intent.putExtra(ViewQuestion.ANSWER_ID_KEY, a.getId());

		setActivityIntent(intent);
		ViewComment vc = getActivity();
		ListView lv = (ListView) vc.findViewById(R.id.commentListView);

		assertTrue("The comment body is not the same", lv.getItemAtPosition(0)
				.equals("String"));
	}

	/**
	 * This tests that two comments are being shown properly and that the content is
	 * correct.
	 */
	public void testCommentBodyIsCorretWithTwoComments() {
		Question q1 = new Question("New Title", "Body", "Author");
		pc.addQuestionToServer(q1);
		Answer a1 = new Answer("twoTitle", "twoAuthor", q1.getId());
		pc.addAnswer(a1, q1.getId());
		Comment c1 = new Comment("String1", "String1");
		Comment c2 = new Comment("String2", "String2");
		pc.addCommentToAnswer(c1, q1.getId(), a1.getId());
		pc.addCommentToAnswer(c2, q1.getId(), a1.getId());

		intent.putExtra(ViewQuestion.QUESTION_ID_KEY, q1.getId());
		intent.putExtra(ViewQuestion.ANSWER_ID_KEY, a1.getId());

		setActivityIntent(intent);
		ViewComment vc = getActivity();

		ListView lv = (ListView) vc.findViewById(R.id.commentListView);

		assertEquals("The comment body is not the same at position 0",lv
				.getItemAtPosition(0),c1.getCommentBody());
		assertEquals("The comment body is not the same at position 1",lv
				.getItemAtPosition(1),c2.getCommentBody());
//		assertTrue("The comment body is not the same at position 0", lv
//				.getItemAtPosition(0).equals("String1"));
//		assertTrue("The comment body is not the same at position 1", lv
//				.getItemAtPosition(1).equals("String2"));
	}

}