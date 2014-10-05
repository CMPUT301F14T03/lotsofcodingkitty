import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.CacheDatabase;
import android.test.ActivityInstrumentationTestCase2;


public class CacheDatabaseTest extends ActivityInstrumentationTestCase2<CacheDatabase> {
	
	/*Cache read questions and answers 	
	 * Allows the user save read questions/answers and questions/answers 
	 * marked as read to be automatically saved to local cache */
	public void testSaveQuestionList() {
		
		CacheDatabase cdb = new CacheDatabase();
//		ArrayList<Questions> questionArray = new Arraylist<Questions>();
//		ArrayList<Questions> newQuestionArray = new Arraylist<Questions>();
		QuestionListController qlc = new QuestionListController();
		
		//Create Questions and add to the array
		Question q1 = new Question();
		Question q2 = new Question();
		
		qlc.addQuestion(q1);
		qlc.addQuestion(q2);
		
		cdb.saveQuestionList(questionArray);
		
		cdb.loadQuestionList(newQuestionArray);
		assertNotNull(newQuestionArray);
	}
	
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
