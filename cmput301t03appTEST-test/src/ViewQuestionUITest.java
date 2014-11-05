import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.adapters.AnswerListAdapter;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.ViewQuestion;

/**
 * This test unit is used for testing the View Questions view and all the functionalities within it.
 * @author tbrockma
 * 
 */

public class ViewQuestionUITest extends
		ActivityInstrumentationTestCase2<ViewQuestion>{

	private static final String TAG = "1234";
	ViewQuestion activity;
	PostController pc;
	ArrayList<Answer> answers;
	Question q;
	String qId;

	public ViewQuestionUITest(){
		super(ViewQuestion.class);
	}
	
    @Override
    protected void setUp() throws Exception{
        super.setUp();
		PostController pc = new PostController(getInstrumentation().getContext());
		q = new Question("Test subject", "Body", "Author");
		qId = q.getId();
		pc.addQuestion(q);
		Comment comment = new Comment("Test", "test");
		q.addComment(comment);
		Answer a = new Answer("test", "test", qId);
		answers = new ArrayList<Answer>();
		answers.add(a);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);
		activity = (ViewQuestion) getActivity();
        
    }

	/**
	 * Tests that all the objects that should exist in the view do exist in the
	 * view
	 * 
	 * Part of UC2: View Question details
	 * Part of UC3: View answers
	 * Part of UC5: View number of answers to a question
	 * Part of UC6: Upvote questions
	 * Part of UC7: Upvote answers
	 * Part of UC12: Post answers
	 * Part of UC13: Comment on a question
	 * Part of UC14: Comment on an answer
	 * Part of UC15: Mark questions as favorite
	 * Part of UC16: Save favorited questions to local drive
	 * Part of UC17: Set user name
	 * 
	 */

	public void testViewObjectsExist(){
		
		ListView answerListView;
		ImageButton favIcon;
		ImageButton upvoteButton;
		ImageButton commentButton;
		TextView upvote_score;
		Button answerButton;
		TextView answerCounter;
		TextView commentCounter;

		answerListView = (ListView) activity.findViewById(R.id.answerListView);
		favIcon = (ImageButton) activity.findViewById(R.id.question_fav_icon);
		upvoteButton = (ImageButton) activity
				.findViewById(R.id.question_upvote_button);
		commentButton = (ImageButton) activity
				.findViewById(R.id.question_comment_icon);
		upvote_score = (TextView) activity
				.findViewById(R.id.question_upvote_score);
		answerButton = (Button) activity
				.findViewById(R.id.question_answer_button);
		answerCounter = (TextView) activity.findViewById(R.id.answer_count);
		commentCounter = (TextView) activity
				.findViewById(R.id.question_comment_count);
		answerListView = (ListView) activity.findViewById(R.id.answerListView);

		// Assert that none of the views are null

		assertNotNull("Item not created for question view", answerListView);
		assertNotNull("Item not created for question view", favIcon);
		assertNotNull("Item not created for question view", upvoteButton);
		assertNotNull("Item not created for question view", commentButton);
		assertNotNull("Item not created for question view", upvote_score);
		assertNotNull("Item not created for question view", answerCounter);
		assertNotNull("Item not created for question view", answerButton);
		assertNotNull("Item not created for question view", commentCounter);

		View mainView = (View) activity.getWindow().getDecorView()
				.findViewById(android.R.id.content);

		// Assert that all of the views are displayed on screen

		ViewAsserts.assertOnScreen(mainView, answerListView);
		ViewAsserts.assertOnScreen(mainView, favIcon);
		ViewAsserts.assertOnScreen(mainView, upvoteButton);
		ViewAsserts.assertOnScreen(mainView, commentButton);
		ViewAsserts.assertOnScreen(mainView, upvote_score);
		ViewAsserts.assertOnScreen(mainView, answerCounter);
		ViewAsserts.assertOnScreen(mainView, answerButton);
		ViewAsserts.assertOnScreen(mainView, commentCounter);

	}

	/**
	 * Adds an answers to a question, tests that the objects in the view change
	 * properly
	 * 
	 * Part of UC12: Post answers
	 */
	
	@UiThreadTest
	public void testAddAnswer(){
		ListView answerListView = (ListView) activity.findViewById(R.id.answerListView);
		AnswerListAdapter ala = (AnswerListAdapter) answerListView.getAdapter();
		ala.updateAdapter(answers);
		activity.updateAnswerCount();

		// Assert that the added answer exists

		assertEquals("Answer not added correctly to adapter", 1, ala.getCount());

		// Assert that the item and the listview is on screen

		View mainView = (View) activity.getWindow().getDecorView()
				.findViewById(android.R.id.content);

		ViewAsserts.assertOnScreen(mainView, answerListView);

		View answerItem = (View) answerListView.getChildAt(answerListView
				.getFirstVisiblePosition());

		// Assert that the view is not null

		assertNotNull("Answer does not exist in listview", answerItem);

		// Assert that the view is in the main window

		ViewAsserts.assertOnScreen(mainView, answerItem);

		// Assert that all of the items in the view are in the main
		// window

		ImageButton answer_upvote_button = (ImageButton) answerItem
				.findViewById(R.id.answer_upvote_button);
		TextView answer_upvote_score = (TextView) answerItem
				.findViewById(R.id.answer_upvote_score);
		ImageView answer_fav_icon = (ImageView) answerItem
				.findViewById(R.id.answer_fav_icon);
		TextView answer_text_body = (TextView) answerItem
				.findViewById(R.id.answer_text_body);
		TextView post_timestamp = (TextView) answerItem
				.findViewById(R.id.post_timestamp);
		TextView answer_author = (TextView) answerItem
				.findViewById(R.id.answer_author);

		ViewAsserts.assertOnScreen(mainView, answer_upvote_button);
		ViewAsserts.assertOnScreen(mainView, answer_upvote_score);
		ViewAsserts.assertOnScreen(mainView, answer_fav_icon);
		ViewAsserts.assertOnScreen(mainView, answer_text_body);
		ViewAsserts.assertOnScreen(mainView, post_timestamp);
		ViewAsserts.assertOnScreen(mainView, answer_author);

		// Assert that the answer count is equal to one

		TextView answerCounter = (TextView) activity
				.findViewById(R.id.answer_count);
		assertTrue("Answer count not displaying one",
				((String) answerCounter.getText()).equals("Answers: 1"));

		// Answer adding stress test

		for (int i = 0; i < 100; i++) {
			Answer a = new Answer("test", "test", qId);
			answers.add(a);
			ala.updateAdapter(answers);
			activity.updateAnswerCount();
		}

		Log.e(TAG, (String) answerCounter.getText());
		assertTrue("Answer count not correct",
				((String) (answerCounter.getText())).equals("Answers: 101"));
	}

	/**
	 * Tests the questions upvote button
	 * 
	 * Part of UC6: Upvote questions
	 * 
	 */
	
	@UiThreadTest
	public void testViewQuestionUIQuestionUpvote() {
		ImageView upvote = (ImageView) activity
				.findViewById(R.id.question_upvote_button);
		upvote.performClick();

		// Asserts that upvote clicks are correctly changing the
		// questions rating

		assertTrue("Question upvote not incrementing score", q.getRating() == 1);
		for (int i = 0; i < 1000; i++) {
			upvote.performClick();
		}
		assertTrue("Upvoting question 1000 times not working",
				q.getRating() == 1001);
	}

	/**
	 * Tests the answer upvote button in the view
	 * 
	 * Part of UC7: Upvote answers
	 * 
	 */

	
	@UiThreadTest
	public void testViewQuestionUIAnswerUpvote()
	{
		Answer answer = new Answer("test", "test", qId);
		pc.addAnswer(answer, qId);
		ListView answerListView = (ListView) activity
				.findViewById(R.id.answerListView);
		View answerItem = (View) answerListView.getChildAt(answerListView
				.getFirstVisiblePosition());
		ImageView upvote = (ImageView) answerItem
				.findViewById(R.id.answer_upvote_button);
		upvote.performClick();

		// Asserts that answer upvote clicks are correctly changing
		// the answers rating

		assertTrue("Answer upvote not incrementing score",
				answer.getRating() == 1);
		for (int i = 0; i < 1000; i++) {
			upvote.performClick();
		}
		assertTrue("Upvoting answer 1000 times not working",
				answer.getRating() == 1001);
	}

	/**
	 * Tests the dialog activity
	 */
	
	
	@UiThreadTest
	public void testDialogActivity()
 {
		((Button) activity.findViewById(R.id.question_answer_button))
				.performClick();
		AlertDialog dialog = activity.getDialog();
		EditText answerBody = (EditText) dialog.findViewById(R.id.postBody);
		EditText userName = (EditText) dialog
				.findViewById(R.id.UsernameRespondTextView);

		// Asserts that the fields needed were created properly

		assertNotNull("Answer body EditText not found", answerBody);
		assertNotNull("Username EditText not found", userName);

		answerBody.setText("test");
		userName.setText("test");

		dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
		TextView answerCount = (TextView) activity
				.findViewById(R.id.answer_count);

		// Assert that the answer counter incremented

		assertEquals("Answers: 1", answerCount.getText());

		ListView answerListView = (ListView) activity
				.findViewById(R.id.answerListView);
		View answerItem = (View) answerListView.getChildAt(answerListView
				.getFirstVisiblePosition());
		View mainView = (View) activity.getWindow().getDecorView()
				.findViewById(android.R.id.content);

		// Assert the answer item appeared on the screen

		ViewAsserts.assertOnScreen(mainView, answerItem);

		assertNotNull(pc.getQuestion(qId).getAnswers());

	}

	/**
	 * Tests favoriting a question
	 * 
	 * Part of UC15: Mark questions as favorite
	 * Part of UC16: Save favorited questions to local drive
	 * 
	 */

	
	@UiThreadTest
	public void testFavoriteAQuestion()
	{
		Drawable icon;
		Drawable favUnfilled = activity.getResources().getDrawable(
				R.drawable.ic_fav_no);
		Drawable favFilled = activity.getResources().getDrawable(
				R.drawable.ic_fav_yes);
		ImageView favIcon = (ImageView) activity
				.findViewById(R.id.question_fav_icon);

		// Assert the questions favourite icon is unfilled

		icon = favIcon.getDrawable();
		
		Bitmap unfil = ((BitmapDrawable)favUnfilled).getBitmap();
		Bitmap fill = ((BitmapDrawable)favFilled).getBitmap();
		Bitmap ic = ((BitmapDrawable)icon).getBitmap();
		assertEquals("Favorite icon not unfilled", unfil, ic);

		favIcon.performClick();

		ic = ((BitmapDrawable)(favIcon.getDrawable())).getBitmap();

		// Assert that the icon is now filled in icon

		assertEquals("Icon not filled", ic, fill);

		// Assert that the question is now in the favorites list

//		assertNotNull("Question not favorited properly",
//				pc.getFavoriteQuestions());
		
		assertTrue(pc.isQuestionInFavByID(qId));
	}

	/**
	 * Tests the comment counter
	 */

	@UiThreadTest
	public void testCommentCounter()
 {
		TextView commentCounter = (TextView) activity
				.findViewById(R.id.question_comment_count);

		// Asserts that a question already with a comment correctly displays the
		// comment count
		assertEquals("Not correctly initializing comment count",
				(String) (commentCounter.getText()), "1");
		TextView newCount;
		for (int i = 1; i < 100; i++) {
			q.addComment(new Comment("this", "this"));
			activity.updateCommentCount();
			newCount = (TextView) activity
					.findViewById(R.id.question_comment_count);

			// Checks that the count updates properly for many
			// comments added

			assertEquals("Not displaying correct comment cont",
					(String) (newCount.getText()), String.valueOf(i + 1));
		}
	}

	/**
	 * Tests the answer counter
	 * 
	 * Part of UC5: View number of answers to a question
	 * 
	 */

	@UiThreadTest
	public void testAnswerCounter() {

		TextView newCount;
		for (int i = 0; i < 100; i++) {
			q.addAnswer(new Answer("this", "this", "1"));
			activity.updateAnswerCount();
			newCount = (TextView) activity.findViewById(R.id.answer_count);

			// Checks that the count updates properly for many
			// comments added

			assertEquals("Answer counter not displaying correct count",
					"Answers: " + String.valueOf(i + 1), (String) (newCount.getText()));
		}
	}
}
