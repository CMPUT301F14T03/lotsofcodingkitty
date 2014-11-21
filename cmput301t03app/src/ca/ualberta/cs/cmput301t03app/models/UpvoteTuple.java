package ca.ualberta.cs.cmput301t03app.models;

public class UpvoteTuple {
	  private String questionId; 
	  private Integer upvoteCount; 
	  public UpvoteTuple(String questionId, Integer upvoteCount) { 
	    this.setQuestionId(questionId); 
	    this.setUpvoteCount(upvoteCount); 
	  }
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public Integer getUpvoteCount() {
		return upvoteCount;
	}
	public void setUpvoteCount(Integer upvoteCount) {
		this.upvoteCount = upvoteCount;
	}
}