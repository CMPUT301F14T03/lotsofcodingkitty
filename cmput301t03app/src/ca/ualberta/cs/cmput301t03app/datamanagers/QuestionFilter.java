package ca.ualberta.cs.cmput301t03app.datamanagers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.GeoLocation;

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
	
	public ArrayList<Question> sortByLocation(ArrayList<Question> subQuestions, GeoLocation location) {
		double userLong = location.getLongitude();
		double userLat = location.getLatitude();
		ArrayList<QuestionDistance> distanceArray = new ArrayList<QuestionDistance>();
		for (int i = 0; i < subQuestions.size(); i++) {
			Question q = subQuestions.get(i);
			double distance;
			if (q.getGeoLocation() == null) {
				distance = 9999999;
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