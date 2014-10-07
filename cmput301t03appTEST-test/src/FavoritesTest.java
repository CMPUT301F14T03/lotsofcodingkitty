import java.sql.Date;
import java.util.List;

import ca.ualberta.cs.cmput301t03app.CacheDatabase;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.QuestionList;
import android.test.ActivityInstrumentationTestCase2;


public class FavoritesTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public FavoritesTest(String name) {
		super(MainActivity.class);
	}

	/*Allows user to select favorites (and the associated replies) 
	and have them auto-save to local device for later access*/
	public void testSaveFavorite() {
		
		User user = new User();
		UserController userController = new UserController();
		QuestionList ql = new QuestionList();
		
		//Create Questions and add to the array
		Date date = new Date(14, 0, 28);
		Question q1 = new Question(date,"Title1","TextBody1");
		Question q2 = new Question(date,"Title2","TextBody2");
		
		ql.addQuestion(q1);
		ql.addQuestion(q2);
		
		List<Question> questionArray = ql.getQuestionList();
		
		//Get question from questionArray and add to favQuestionsArray
		Question favQuestion = questionArray.get(0);		
		userController.addFavQuestion(favQuestion); //This auto-saves to favQuestionArray

		//TODO:Load favQuestionsArray from elasticsearch database
		
		assertNotNull(favQuestionsArray);	
	}
}
