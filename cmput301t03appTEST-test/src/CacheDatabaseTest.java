import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import ca.ualberta.cs.cmput301t03app.DataManager;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;
import android.test.ActivityInstrumentationTestCase2;


public class CacheDatabaseTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	/*Cache read questions and answers 	
	 * Allows the user save read questions/answers and questions/answers 
	 * marked as read to be automatically saved to local cache */
	public CacheDatabaseTest() {
		
		super(MainActivity.class);
	}
	
	public void testSaveLoadQuestionList() {		
		
		DataManager dataManager = new DataManager();
		ArrayList<Question> q = new ArrayList<Question>();
		
		//Create Questions and add to the array
		Question q1 = new Question("Title1","TextBody1");
		Question q2 = new Question("Title2","TextBody2");
		
		q.add(q1);
		q.add(q2);
		
		dataManager.localSaveQuestions(q);
		
		ArrayList<Question> newQuestionArray = new ArrayList<Question>();
		
		newQuestionArray = dataManager.localLoadQuestions();
		assertNotNull(newQuestionArray);
	}
	
	/*Incomplete test*/
//	public void testSearchQuestionAnswer() {
//		
//		CacheDatabase cdb = new CacheDatabase();
////		ArrayList<Questions> questionArray = new ArrayList<Questions>;
////		ArrayList<Questions> answerArray = new ArrayList<Answer>;
//		QuestionListController qlc = new QuestionListController();
//		
//		
//		cdb.loadQuestionList(questionArray);
//		cdb.loadAnswerList(answerArray);
//		
//		Question q1 = new Question();
//		Question q2 = new Question();
//		questionArray.addQuestion(q1);
//		questionArray.addQuestion(q2);
//		
//		Answer a1 = new Answer();
//		Answer a2 = new Answer();
//		answerArray.addAnswer(q1);
//		AnswerArray.addAnswer(q2);
//		
//		searchQuestionAnswer();
//		
//		
//	}
}
