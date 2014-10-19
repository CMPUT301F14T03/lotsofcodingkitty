import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.UserPostCollector;
import android.test.ActivityInstrumentationTestCase2;


public class FavoritesTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public FavoritesTest() {
		super(MainActivity.class);
	}

	// Create a question array, add an array to the question array
	// Create a user post collector, specify to the post collector
	// that the previously added question is to be favourite
	// Use the get favourites method on the user post collector
	// assert that the initialized array is the same as the array
	// returned by get favourites
	
	public void testSaveFavorites() {
		
		ArrayList<Question> questionArray;
		UserPostCollector userCollect = new UserPostCollector();
		ArrayList<Question> ql = new ArrayList<Question>();
		
		Question q = new Question("Title1","TextBody1");
		
		ql.add(q);
		
		userCollect.addFavoriteQuestions(q);
		
		questionArray = userCollect.getFavoriteQuestions();
		
		assertEquals("Questions saved not same as questions retrieved.", ql, questionArray);
		
	}
}
