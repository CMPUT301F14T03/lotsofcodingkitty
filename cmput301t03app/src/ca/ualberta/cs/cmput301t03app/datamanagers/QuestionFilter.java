package ca.ualberta.cs.cmput301t03app.datamanagers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.GeoLocation;

/**
 * This class is a filter for sorting through the list of questions
 * in the MainActivity. The list of questions can be filtered by
 * most number of upvotes, newest, closest, and if there is an image
 * attached.
 *
 */
public class QuestionFilter {
	
	/**
	 * returns a List of questions sorted by number of upvotes
	 * @param subQuestions - list that needs to be sorted
	 * @return list that is sorted by number of upvotes
	 */
	public ArrayList<Question> sortByUpvote(ArrayList<Question> subQuestions){
		Collections.sort(subQuestions,new upvoteComparator());
		return subQuestions;
	}
	
	/**
	 * returns a List of questions sorted by date
	 * @param subQuestions - list that needs to be sorted
	 * @return list that is sorted by date
	 */
	public ArrayList<Question> sortByDate(ArrayList<Question> subQuestions){
		Collections.sort(subQuestions,new dateComparator());
		return subQuestions;
	}
	
	/**
	 * returns a List of questions sorted if it contains a picture
	 * @param subQuestions - list that needs to be sorted
	 * @return list that is sorted with the questions having pictures first
	 */
	public ArrayList<Question> sortByPic(ArrayList<Question> subQuestions){
		Collections.sort(subQuestions,new picComparator());
		return subQuestions;
	}
	
	/**
	 * returns a List of answers sorted by number of upvotes
	 * @param subQuestions - list that needs to be sorted
	 * @return list that is sorted by number of upvotes
	 */
	public ArrayList<Answer> sortByScore(ArrayList<Answer> subAnswers){
		Collections.sort(subAnswers, new ansUpvoteComparator());
		return subAnswers;
	}
	
	/**
	 * returns a List of questions sorted by how close the location of that question is to the person using it
	 * @param subQuestions - list that needs to be sorted
	 * @return list that is sorted by number of upvotes
	 */
	public ArrayList<Question> sortByLocation(ArrayList<Question> subQuestions, GeoLocation location) {
		double userLong = location.getLongitude();
		double userLat = location.getLatitude();
		ArrayList<QuestionDistance> distanceArray = new ArrayList<QuestionDistance>();
		for (int i = 0; i < subQuestions.size(); i++) {
			Question q = subQuestions.get(i);
			double distance;
			if (q.getGeoLocation() == null) {
				distance = Double.POSITIVE_INFINITY;;
			}
			else {
				distance = Math.abs((q.getGeoLocation().getLongitude()-userLong)) + Math.abs((q.getGeoLocation().getLatitude()-userLat));
			}
			QuestionDistance qDist = new QuestionDistance(q,distance);
			distanceArray.add(qDist);
		}
		Collections.sort(distanceArray, new distanceComparator());
		ArrayList<Question> newQuestions = new ArrayList<Question>();
		for (int i = 0; i < distanceArray.size(); i++) {
			newQuestions.add(distanceArray.get(i).getQuestion());
		}
		return newQuestions;
	}
	
}

/*########################----COMPARATOR CLASSES----#############################*/


/**
 * Comparator class that returns 1 when the first question 
 * passed has more upvotes than the second question
 *
 */
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

/**
 * Comparator class that returns 1 when the first answer 
 * passed has more upvotes than the second answer
 *
 */
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

/**
 * Comparator class that returns 1 when the first question 
 * passed is more recent than the second question
 *
 */
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

/**
 * Comparator class that returns 1 when the questions have pictures
 *
 */
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


/**
 * Comparator class that returns 1 when the first question 
 * passed is closer to the location of the person using the app,
 *  than the second question
 *
 */
class distanceComparator implements Comparator<QuestionDistance> {
	@Override
	public int compare(QuestionDistance q1, QuestionDistance q2) {
		if(q1.getDistance() > q2.getDistance()) {
			return 1;
		}
		else if (q1.getDistance() < q2.getDistance()) {
			return -1;
		}
		else {
			return 0;
		}
	}
}

/**
 * This class is used as a data class to store the distance between
 * the location of the question and the location of the user.
 *
 */
class QuestionDistance {
	private Question question;
	private double distance;
	
	public QuestionDistance(Question question, double distance) {
		this.question = question;
		this.distance = distance;
	}
	
	public Question getQuestion()
	{
	
		return question;
	}

	
	public void setQuestion(Question question)
	{
	
		this.question = question;
	}

	
	public double getDistance()
	{
	
		return distance;
	}

	
	public void setDistance(double distance)
	{
	
		this.distance = distance;
	}
	
}