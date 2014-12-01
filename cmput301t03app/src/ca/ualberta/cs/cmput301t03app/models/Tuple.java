package ca.ualberta.cs.cmput301t03app.models;

/**
 * A custom tuple object that is used to save information about Questions,
 * Answer and Comments made while offline. More importantly, it helps save the
 * correct association, so that the objects get placed with the correct
 * corresponding ones (ie. Answer A belongs to Question A, but not to Question
 * B).
 * 
 */

public class Tuple {
	private String qId;
	private String aId;
	private Comment comment;

	/**
	 * Constructor for the Tuple
	 * 
	 * @param qId
	 *            ID of the Question
	 * @param aId
	 *            ID of the Answer
	 * @param comment
	 *            Comment object
	 */
	public Tuple(String qId, String aId, Comment comment) {
		this.qId = qId;
		this.aId = aId;
		this.comment = comment;
	}

	/**
	 * Returns the question ID
	 * @return A question ID string
	 */
	public String getQuestionID() {
		return qId;
	}
	
	/**
	 * Returns the answer ID
	 * @return A answer ID string
	 */
	public String getAnswerID() {
		return aId;
	}

	/**
	 * Returns the Comment object
	 * @return A Comment Object
	 */
	public Comment getComment() {
		return comment;
	}
}
