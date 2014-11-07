package ca.ualberta.cs.cmput301t03app.incomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;

public class QuestionFilter {
	
	public ArrayList<Question> sortByUpvote(ArrayList<Question> subQuestions){
		Collections.sort(subQuestions,new upvoteComparator());
		return subQuestions;
	}
	
	public ArrayList<Question> sortByDate(ArrayList<Question> subQuestions){
		Collections.sort(subQuestions,new dateComparator());
		return subQuestions;
	}
	
	public ArrayList<Question> sortByPic(ArrayList<Question> subQuestions){
		Collections.sort(subQuestions,new picComparator());
		return subQuestions;
	}
	
	public ArrayList<Answer> sortByScore(ArrayList<Answer> subAnswers){
		Collections.sort(subAnswers, new ansUpvoteComparator());
		return subAnswers;
	}
	
}

class upvoteComparator implements Comparator<Question> {
	@Override
    public int compare(Question q1, Question q2) {
        if(q1.getRating() < q2.getRating()) {
            return 1;
        } 
        else if (q1.getRating() > q2.getRating()) {
            return -1;
        }
        else {
        	return 0;
        }
    }
}

class ansUpvoteComparator implements Comparator<Answer> {
	@Override
    public int compare(Answer a1, Answer a2) {
        if(a1.getRating() < a2.getRating()) {
            return 1;
        } 
        else if (a1.getRating() > a2.getRating()) {
            return -1;
        }
        else {
        	return 0;
        }
    }
}

class dateComparator implements Comparator<Question> {
	@Override
    public int compare(Question q1, Question q2) {
        if(q1.getDate().before(q2.getDate())) {
            return 1;
        } 
        else if (q1.getDate().after(q2.getDate())) {
            return -1;
        }
        else {
        	return 0;
        }
    }
}

class picComparator implements Comparator<Question> {
	@Override
    public int compare(Question q1, Question q2) {
        if(q1.getPicture() == null && q2.getPicture() != null) {
            return 1;
        } 
        else if (q2.getPicture() == null && q1.getPicture() != null) {
            return -1;
        }
        else {
        	if (q1.getRating()<q2.getRating()) {
        		return 1;
        	}
        	else if (q1.getRating()>q2.getRating()) {
        		return -1;
        	}
        	return 0;
        }
    }
}