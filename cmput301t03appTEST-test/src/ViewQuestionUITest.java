import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
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
 * 
 * @author tbrockma
 * 
 */

public class ViewQuestionUITest extends
		ActivityInstrumentationTestCase2<ViewQuestion>
{

	ViewQuestion activity;
	Question q;
	String qId;

	public ViewQuestionUITest()
	{

		super(ViewQuestion.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Tests that all the objects that should exist in the view do exist in the
	 * view
	 */

	public void testViewObjectsExist()
	{

		PostController pc = new PostController(getInstrumentation()
				.getContext());
		q = new Question("Test subject", "Body", "Author");
		qId = q.getId();
		pc.addQuestion(q);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);
		activity = (ViewQuestion) getActivity();

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
	 */

	public void testAddAnswer()
	{
		PostController pc = new PostController(getInstrumentation()
				.getContext());
		q = new Question("Test subject", "Body", "Author");
		qId = q.getId();
		pc.addQuestion(q);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);
		final ViewQuestion activity = (ViewQuestion) getActivity();

		try {
			runTestOnUiThread(new Runnable() {

				@Override
				public void run() {

					ListView answerListView = (ListView) activity
							.findViewById(R.id.answerListView);
					AnswerListAdapter ala = (AnswerListAdapter) answerListView
							.getAdapter();
					ArrayList<Answer> answers = new ArrayList<Answer>();
					Answer answer = new Answer("test", "test", qId);
					answers.add(answer);
					ala.updateAdapter(answers);
					activity.updateAnswerCount();

					// Assert that the added answer exists

					assertNotNull("Answer not added correctly to adapter",
							ala.getItem(0));

					// Assert that the item and the listview is on screen

					View mainView = (View) activity.getWindow().getDecorView()
							.findViewById(android.R.id.content);

					ViewAsserts.assertOnScreen(mainView, answerListView);

					View answerItem = (View) answerListView
							.getChildAt(answerListView
									.getFirstVisiblePosition());

					// Assert that the view is not null

					assertNotNull("Answer does not exist in listview",
							answerItem);

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
					// assertTrue(
					// "Answer count not displaying one",
					// ((String)answerCounter.getText()).equals("Answers: 1"));

					// Answer adding stress test

					for (int i = 0; i < 100; i++) {
						Answer a = new Answer("test", "test", qId);
						answers.add(a);
						ala.updateAdapter(answers);
						activity.updateAnswerCount();
					}

				}
			});
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getInstrumentation().waitForIdleSync();

		TextView answerCounter = (TextView) activity
				.findViewById(R.id.answer_count);

		// assertTrue("Answer count not correct",
		// ((String)(answerCounter.getText())).equals("Answers: 101"));
	}

	/**
	 * Tests the questions upvote button
	 */

	public void testViewQuestionUIQuestionUpvote() {

		PostController pc = new PostController(getInstrumentation()
				.getContext());
		q = new Question("Test subject", "Body", "Author");
		qId = q.getId();
		pc.addQuestion(q);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);
		final ViewQuestion activity = (ViewQuestion) getActivity();
		try {
			runTestOnUiThread(new Runnable() {

				@Override
				public void run()
				{

					ImageView upvote = (ImageView) activity
							.findViewById(R.id.question_upvote_button);
					upvote.performClick();

					// Asserts that upvote clicks are correctly changing the
					// questions rating

					assertTrue("Question upvote not incrementing score",
							q.getRating() == 1);
					for (int i = 0; i < 1000; i++)
					{
						upvote.performClick();
					}
					assertTrue("Upvoting question 1000 times not working",
							q.getRating() == 1001);
				}
			});
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tests the answer upvote button in the view
	 */

	public void testViewQuestionUIAnswerUpvote()
	{

		PostController pc = new PostController(getInstrumentation()
				.getContext());
		q = new Question("Test subject", "Body", "Author");
		qId = q.getId();
		pc.addQuestion(q);
		final Answer answer = new Answer("test", "test", qId);
		pc.addAnswer(answer, qId);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);
		final ViewQuestion activity = (ViewQuestion) getActivity();
		try
		{
			runTestOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{

					ListView answerListView = (ListView) activity
							.findViewById(R.id.answerListView);
					View answerItem = (View) answerListView
							.getChildAt(answerListView
									.getFirstVisiblePosition());
					ImageView upvote = (ImageView) answerItem
							.findViewById(R.id.answer_upvote_button);
					upvote.performClick();

					// Asserts that answer upvote clicks are correctly changing
					// the answers rating

					assertTrue("Answer upvote not incrementing score",
							answer.getRating() == 1);
					for (int i = 0; i < 1000; i++)
					{
						upvote.performClick();
					}
					assertTrue("Upvoting answer 1000 times not working",
							answer.getRating() == 1001);
				}
			});
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tests the dialog activity
	 */

// 	Test commented out until we have a method of either getting a dialog object, 
//	or we have switched to using Robotium
//	
//	public void testDialogActivity()
//	{
//
//		PostController pc = new PostController(getInstrumentation()
//				.getContext());
//		q = new Question("Test subject", "Body", "Author");
//		qId = q.getId();
//		pc.addQuestion(q);
//		Intent intent = new Intent();
//		intent.putExtra("question_id", qId);
//		setActivityIntent(intent);
//		final ViewQuestion activity = (ViewQuestion) getActivity();
//		try
//		{
//			runTestOnUiThread(new Runnable()
//			{
//
//				@Override
//				public void run()
//				{
//
//					((Button) activity
//							.findViewById(R.id.question_answer_button))
//							.performClick();
//					AlertDialog dialog = activity.getDialog();
//					EditText answerBody = (EditText) dialog
//							.findViewById(R.id.postBody);
//					EditText userName = (EditText) dialog
//							.findViewById(R.id.UsernameRespondTextView);
//					
//					// Asserts that the fields needed were created properly
//					
//					assertNotNull("Answer body EditText not found", answerBody);
//					assertNotNull("Username EditText not found", userName);
//					
//					answerBody.setText("test");
//					userName.setText("test");
//					
//					dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
//					TextView answerCount = (TextView)activity.findViewById(R.id.answer_count);
//					
//					// Assert that the answer counter incremented
//					
//					assertEquals("Answers: 1",answerCount.getText());
//					
//					ListView answerListView = (ListView) activity
//							.findViewById(R.id.answerListView);
//					View answerItem = (View) answerListView
//							.getChildAt(answerListView
//									.getFirstVisiblePosition());
//					View mainView = (View) activity.getWindow().getDecorView()
//							.findViewById(android.R.id.content);
//					
//					// Assert the answer item appeared on the screen
//					
//					ViewAsserts.assertOnScreen(mainView, answerItem);
//					
//					
//					
//				}
//			});
//		} catch (Throwable e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		// Assert that answers were correctly added
//		
//		assertNotNull(pc.getQuestion(qId).getAnswers());
//
//	}

	/**
	 * Tests favoriting a question
	 */

	public void testFavoriteAQuestion()
	{

		PostController pc = new PostController(getInstrumentation()
				.getContext());
		q = new Question("Test subject", "Body", "Author");
		qId = q.getId();
		pc.addQuestion(q);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);
		final ViewQuestion activity = (ViewQuestion) getActivity();
		try
		{
			runTestOnUiThread(new Runnable()
			{

				@Override
				public void run()
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
					assertEquals("Icon not unfilled", icon, favUnfilled);

					favIcon.performClick();
					icon = favIcon.getDrawable();

					// Assert that the icon is now filled in icon

					assertEquals("Icon not filled", icon, favFilled);

				}
			});
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Assert that the question is now in the favorites list

		assertNotNull("Question not favorited properly",
				pc.getFavoriteQuestions());
	}

	/**
	 * Tests the comment counter
	 */

	public void testCommentCounter()
	{

		PostController pc = new PostController(getInstrumentation()
				.getContext());
		Comment comment = new Comment("Test", "test");
		q = new Question("Test subject", "Body", "Author");
		q.addComment(comment);
		qId = q.getId();
		pc.addQuestion(q);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);
		final ViewQuestion activity = (ViewQuestion) getActivity();
		TextView commentCounter = (TextView) activity
				.findViewById(R.id.question_comment_count);

		// Asserts that a question already with a comment correctly displays the
		// comment count
		assertEquals("Not correctly initializing comment count",
				(String) (commentCounter.getText()), "1");

		try
		{
			runTestOnUiThread(new Runnable(){

				@Override
				public void run()
				{

					TextView newCount;
					for (int i = 0; i < 100; i++)
					{
						q.addComment(new Comment("this", "this"));
						activity.updateCommentCount();
						newCount = (TextView) activity
								.findViewById(R.id.question_comment_count);

						// Checks that the count updates properly for many
						// comments added

						assertEquals("Not displaying correct comment cont",
								(String) (newCount.getText()),
								String.valueOf(i + 1));
					}
				}
			});
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tests the answer counter
	 */

	public void testAnswerCounter()
	{

		PostController pc = new PostController(getInstrumentation()
				.getContext());
		q = new Question("Test subject", "Body", "Author");
		qId = q.getId();
		pc.addQuestion(q);
		Intent intent = new Intent();
		intent.putExtra("question_id", qId);
		setActivityIntent(intent);

		try
		{
			activity.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					TextView newCount;
					for (int i = 0; i < 100; i++)
					{
						q.addAnswer(new Answer("this", "this", "1"));
						activity.updateAnswerCount();
						newCount = (TextView) activity
								.findViewById(R.id.answer_count);

						// Checks that the count updates properly for many
						// comments added

						assertEquals(
								"Answer counter not displaying correct count",
								(String) (newCount.getText()),
								"abcd");
					}
				}
			});
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
