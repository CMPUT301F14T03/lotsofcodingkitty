import static org.junit.Assert.*;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.cmput301t03app.MainActivity;





public class theoTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	public void testViewFavorites() {
		ArrayList<Question> favQuestions = new ArrayList<Question>();
		ArrayList<Answer> favAnswer = new ArrayList<Answer>();
		{
			forumQuestionList listTest = new forumQuestionList;
			ArrayList<Question> q = listTest.getList();
			ArrayList<Answer> a = new ArrayList<Answer>();
			for(int i = 0; i < q.size(); i++) {
				User.addFavQuestion(q.indexOf(i));
				ArrayList<Answer> temp = q.indexOf(i).getAnswers();
				for(int j = 0; j < temp.size(); j++) {
					User.addFavAnswer(temp.indexOf(j));
					a.add(temp.indexOf(j))
				}
			}
		}
		favQuestions = User.getFavQuestions();
		favAnswers = User.getFavAnswers();
		assertEquals(favQuestions,q);
		assertEquals(favAnswers,a);
	}
	
	public void testUpvoteQuestions() {
		Question testQuestion = new Question;
		testQuestions.upvote();
		assertEquals(testQuestion.getRating(),1);
	}
	
	public void testUpvoteAnswers() {
		Answer testAnswer = new Answer;
		testAnswers.upvote();
		assertEquals(testAnswer.getRating(), 1);
	}
	
	public void testProfilePic() {
		Author newAuthor = new Author;
		profilePic = uploadPic("test.jpg");
		newAuthor.setProfile(profilePic);
		assertEquals("test.jpg",newAuthor.getProfilePic());
	}
}
