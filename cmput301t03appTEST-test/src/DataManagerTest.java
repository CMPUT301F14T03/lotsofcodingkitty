import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.interfaces.IDataManager;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.UserPostCollector;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;

/**
 * Tests all saving and loading methods in both the LocalDataManager and
 * ServerDataManager.
 * <br><br>TODO: Server still needs to be implemented.
 * @category Unit Testing
 * 
 */

public class DataManagerTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	ArrayList<Question> questionArray;
	PostController postController;
	ArrayList<Question> ql;
	Question q;
	Question q2;
	Question q3;

	public DataManagerTest() {

		super(MainActivity.class);
	}

	/**
	 * This test uses the favoriteQuestions list to test the saving and loading. <br>
	 * Asserts all questions have the same content when it is saved and loaded
	 * from local data
	 */
	public void testSuccessfulSavingAndLoadingFavoritesFromCache() {
		postController = new PostController(getInstrumentation()
				.getTargetContext());
		ql = new ArrayList<Question>();

		q = new Question(
				"This is a test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q2 = new Question(
				"This is a another test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q3 = new Question(
				"This is a third test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		ql.add(q);
		ql.add(q2);
		ql.add(q3);
		postController.addFavoriteQuestion(q);
		postController.addFavoriteQuestion(q2);
		postController.addFavoriteQuestion(q3);
		questionArray = postController.getFavoriteQuestions();
		int size = questionArray.size();
		assertNotNull("Loaded array is empty", questionArray);
		assertEquals("Loaded array is the not the same size as ql",
				questionArray.size(), ql.size());
		assertEquals(
				"The first index of loaded array does not have the same question as the first question added",
				q.getSubject(), questionArray.get(0).getSubject());
		assertEquals(
				"The second index of the loaded array does not have the same question as the second question added",
				q.getId(), questionArray.get(0).getId());
		ServerDataManager sdm=new ServerDataManager();
		sdm.deleteQuestion(q.getId());
		sdm.deleteQuestion(q2.getId());
		sdm.deleteQuestion(q3.getId());
		
	}

	/**
	 * This test uses the readQuestions list to test the saving and loading. <br>
	 * Asserts all questions have the same content when being loaded from the
	 * local data
	 */
	public void testSuccessfulSavingAndLoadingReadFromCache() {
		postController = new PostController(getInstrumentation()
				.getTargetContext());
		ql = new ArrayList<Question>();
		q = new Question(
				"This is a test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q2 = new Question(
				"This is a another test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q3 = new Question(
				"This is a third test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		ql.add(q);
		ql.add(q2);
		ql.add(q3);
		postController.addReadQuestion(q);
		postController.addReadQuestion(q2);
		postController.addReadQuestion(q3);
		questionArray = postController.getReadQuestions();
		assertNotNull("Loaded array is empty", questionArray);
		assertEquals("Loaded array is the not the same size as ql",
				questionArray.size(), ql.size());
		assertEquals(
				"The first index of loaded array does not have the same question as the first question added",
				q.getSubject(), questionArray.get(0).getSubject());
		assertEquals(
				"The second index of the loaded array does not have the same question as the second question added",
				q.getId(), questionArray.get(0).getId());
		ServerDataManager sdm=new ServerDataManager();
		sdm.deleteQuestion(q.getId());
		sdm.deleteQuestion(q2.getId());
		sdm.deleteQuestion(q3.getId());
		
	}

	/**
	 * This test uses the toReadQuestions list to test the saving and loading. <br>
	 * Asserts all questions have the same content when saved then loaded from
	 * local data.
	 */
	public void testSuccessfulSavingAndLoadingToReadFromCache() {
		postController = new PostController(getInstrumentation()
				.getTargetContext());
		ql = new ArrayList<Question>();
		q = new Question(
				"This is a test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q2 = new Question(
				"This is a another test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q3 = new Question(
				"This is a third test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		ql.add(q);
		ql.add(q2);
		ql.add(q3);
		postController.addToRead(q);
		postController.addToRead(q2);
		postController.addToRead(q3);
		questionArray = postController.getToReadQuestions();
		assertNotNull("Loaded array is empty", questionArray);
		assertEquals("Loaded array is the not the same size as ql",
				questionArray.size(), ql.size());
		assertEquals(
				"The first index of loaded array does not have the same question as the first question added",
				q.getSubject(), questionArray.get(0).getSubject());
		assertEquals(
				"The second index of the loaded array does not have the same question as the second question added",
				q.getId(), questionArray.get(0).getId());
		ServerDataManager sdm=new ServerDataManager();
		sdm.deleteQuestion(q.getId());
		sdm.deleteQuestion(q2.getId());
		sdm.deleteQuestion(q3.getId());
		
	}

	/**
	 * This test uses the toReadQuestions list to test the saving and loading.<br>
	 * Asserts all questions have the same content.
	 */
	public void testSuccessfulSavingAndLoadingUserPostsFromCache() {
		postController = new PostController(getInstrumentation()
				.getTargetContext());
		ql = new ArrayList<Question>();
		q = new Question(
				"This is a test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q2 = new Question(
				"This is a another test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q3 = new Question(
				"This is a third test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		ql.add(q);
		ql.add(q2);
		ql.add(q3);
		postController.addUserPost(q);
		postController.addUserPost(q2);
		postController.addUserPost(q3);
		questionArray = postController.getUserPostedQuestions();
		assertNotNull("Loaded array is empty", questionArray);
		assertEquals("Loaded array is the not the same size as ql",
				questionArray.size(), ql.size());
		assertEquals(
				"The first index of loaded array does not have the same question as the first question added",
				q.getSubject(), questionArray.get(0).getSubject());
		assertEquals(
				"The second index of the loaded array does not have the same question as the second question added",
				q.getId(), questionArray.get(0).getId());
		ServerDataManager sdm=new ServerDataManager();
		sdm.deleteQuestion(q.getId());
		sdm.deleteQuestion(q2.getId());
		sdm.deleteQuestion(q3.getId());
		
	}

	/**
	 * This tests that the favorite list is saving and loading the correct
	 * questions after being saved and loaded.
	 */
	public void testParsingFavoriteList() {
		postController = new PostController(getInstrumentation()
				.getTargetContext());
		ql = new ArrayList<Question>();
		q = new Question(
				"This is a test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q2 = new Question(
				"This is a another test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		q3 = new Question(
				"This is a third test question for caching a favorite question.",
				"This is some random text to fill out the textbody.",
				"Tonberry");
		ql.add(q);
		ql.add(q2);
		ql.add(q3);
		postController.addReadQuestion(q);
		postController.addReadQuestion(q2);
		postController.addReadQuestion(q3);
		postController.addFavoriteQuestion(q2);
		questionArray = postController.getFavoriteQuestions();
		int size = questionArray.size();
		assertEquals("Parser did not find correct question", q2.getId(),
				questionArray.get(size-1).getId());
		UserPostCollector upc = postController.getUPC();
		upc.clearLists();
		ServerDataManager sdm=new ServerDataManager();
		sdm.deleteQuestion(q.getId());
		sdm.deleteQuestion(q2.getId());
		sdm.deleteQuestion(q3.getId());
		
	}

	/**
	 * This test should test the server loading and saving.
	 * <br><br>TODO: Still in progress.
	 */
	public void testSuccessfulSavingAndLoadingToServer() {
		// testing if question posted saved to cache
		ServerDataManager dataManager = new ServerDataManager();
		postController = new PostController(getInstrumentation()
				.getTargetContext());
		ArrayList<Question> q = new ArrayList<Question>();
		Question q1 = new Question("Title1", "TextBody1", "author");
		Question q2 = new Question("Title1", "TextBody1", "author");
		q.add(q1);
		q.add(q2);

		dataManager.saveToQuestionBank(q);

		ArrayList<Question> newQuestionArray= new ArrayList<Question>();

		postController.loadServerQuestions(newQuestionArray);
		dataManager.deleteQuestion(q1.getId());
		dataManager.deleteQuestion(q2.getId());
		assertNotNull("No questions loaded.", newQuestionArray);
	}
}
