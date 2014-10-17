import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.Comment;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class commentTestCases extends ActivityInstrumentationTestCase2<MainActivity> {

	/*
	* View newest comment by default  User, Author
  	* Write comments, questions and answers offline  Author
  	* Push comments, questions and answers when connected  Author
  	* Write comments, questions and answers
	* */
	
	
	public commentTestCases() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	public void writePostsOffline() {
		/* Write comments, questions and answers */
		
		/* Check connection */
		ConncetionManager cm = new ConnectionManager();
		
				
		QuestionListController qlc = new QuestionListController();
		Question q1 = new Question("My question");
		
		qlc.addQuestion(q1);
		
		AnswerController ac = new AnswerController();
		Answer a1 = new Answer("My answer");
		
		q1.addAnswer(a1);
		
		CommentController cc = new CommentController();
		Comment c1 = new Comment("My comment");
		
		
		if (!cm.isConnected) {
			/* save to server */
			
			/* Assert its true while online */
			
			assertTrue("Add question", 1, qlc.ListofQuestions.size());
			assertTrue("Add answer", 1, q1.ListofQuestions.size());
			assertTrue("Add comment", 1, q1.ListofAnswers.size());
		}
	}
		
	public void writePostsOnline() {
		/* Write comments, questions and answers */
		
		/* Check connection */
		ConncetionManager cm = new ConnectionManager();
		
				
		QuestionListController qlc = new QuestionListController();
		Question q1 = new Question("My question");
		
		qlc.addQuestion(q1);
		
		AnswerController ac = new AnswerController();
		Answer a1 = new Answer("My answer");
		
		q1.addAnswer(a1);
		
		CommentController cc = new CommentController();
		Comment c1 = new Comment("My comment");
		
		
		if (!cm.isConnected) {
			/* save to server */
			
			/* Assert its true while online */
			
			assertTrue("Add question", 1, qlc.ListofQuestions.size());
			assertTrue("Add answer", 1, q1.ListofQuestions.size());
			assertTrue("Add comment", 1, q1.ListofAnswers.size());
		}
	}
		
		
	public void writePosts() {
		/* Write comments, questions and answers */
		
		/* Check connection */
		ConncetionManager cm = new ConnectionManager();
		
				
		QuestionListController qlc = new QuestionListController();
		Question q1 = new Question("My question");
		
		qlc.addQuestion(q1);
		
		AnswerController ac = new AnswerController();
		Answer a1 = new Answer("My answer");
		
		q1.addAnswer(a1);
		
		CommentController cc = new CommentController();
		Comment c1 = new Comment("My comment");
		
		

		assertTrue("Add question", 1, qlc.ListofQuestions.size());
		assertTrue("Add answer", 1, q1.ListofQuestions.size());
		assertTrue("Add comment", 1, q1.ListofAnswers.size());
		
	}

	
	
	
	
}
