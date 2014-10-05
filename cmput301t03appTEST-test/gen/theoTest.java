import static org.junit.Assert.*;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.cmput301t03app.MainActivity;





public class theoTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	// Creates a list of questions and and list of answers
	// iterates through all existing questions and answers, favoriting
	// each question and answer and then adding them to the
	// question and answer lists.
	// It then uses the getter methods for favourites answers
	// and questions and then compares the two lists
	// which should be equal
	
	public void testViewFavorites() {
		
		ArrayList<Question> favQuestions = new ArrayList<Question>();
		ArrayList<Answer> favAnswer = new ArrayList<Answer>();
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
		favQuestions = User.getFavQuestions();
		favAnswers = User.getFavAnswers();
		assertEquals(favQuestions,q);
		assertEquals(favAnswers,a);
	}
	
	// Creates a dummy questions, adds an upvote
	// asserts that the question has one upvote
	
	public void testUpvoteQuestions() {
		
		Question testQuestion = new Question;
		testQuestions.changeRating();
		assertEquals(testQuestion.getRating(),1);
	}
	
	// Creates dummy answer, adds and upvote
	// asserts that the question has one upvote
	
	public void testUpvoteAnswers() {
		
		Answer testAnswer = new Answer;
		testAnswers.changeRating();
		assertEquals(testAnswer.getRating(), 1);
	}
	
	// Creates a dummy author, uses a dummy function
	// of uploading a test jpg file, sets the author profile
	// picture to the newly uploaded file.
	// Asserts with a getter method that the jpg returned and
	// the jpg uploaded are the same.
	
	public void testProfilePic() {
		Author newAuthor = new Author;
		profilePic = uploadPic("test.jpg");
		newAuthor.setProfile(profilePic);
		assertEquals("test.jpg",newAuthor.getProfilePic());
	}
}
