import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import ca.ualberta.cs.cmput301t03app.CacheDatabase;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.QuestionList;
import android.test.ActivityInstrumentationTestCase2;


public class CacheDatabaseTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	/*Cache read questions and answers 	
	 * Allows the user save read questions/answers and questions/answers 
	 * marked as read to be automatically saved to local cache */
	public CacheDatabaseTest() {
		
		super(MainActivity.class);
	}
	
	public void testSaveLoadQuestionList() {		
		
		CacheDatabase cdb = new CacheDatabase();
		QuestionList ql = new QuestionList();
		
		//Create Questions and add to the array
		Date date = new Date(14, 0, 28);
		Question q1 = new Question(date,"Title1","TextBody1");
		Question q2 = new Question(date,"Title2","TextBody2");
		
		ql.addQuestion(q1);
		ql.addQuestion(q2);
		
		List<Question> questionArray = ql.getQuestionList();
		
		cdb.saveQuestionList(questionArray);
		
		List<Question> newQuestionArray = new ArrayList<Question>();
		
		newQuestionArray = cdb.loadQuestionList();
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
