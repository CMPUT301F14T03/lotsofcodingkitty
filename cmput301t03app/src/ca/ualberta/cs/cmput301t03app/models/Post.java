package ca.ualberta.cs.cmput301t03app.models;

/**
 * This is a generic class used for a describing a post
 * @author tbrockma
 *
 */

public class Post {
	private Object self;
	private String parentId;
	private String questionParentId; //only used for comments to answers
	private Class<?> classofParent;
	
	public Post(Object self, String parentId, String questionParentId,
			Class<?> classofParent) {
		this.setSelf(self);
		this.setParentId(parentId);
		this.setClassofParent(classofParent);
		this.setQuestionParentId(questionParentId);
		
	}
	
	public Post(Object self, String parentId, Class<?> classofParent) {
		this.setSelf(self);
		this.setParentId(parentId);
		this.setClassofParent(classofParent);
		this.setQuestionParentId(null);
	}
	
	
	public Post(Object self) {
		this.setSelf(self);
		this.setParentId(null);
		this.setClassofParent(Object.class);
		this.setQuestionParentId(null);
	}

	public Class<?> getClassofParent() {
		return classofParent;
	}

	private void setClassofParent(Class<?> classofParent) {
		this.classofParent = classofParent;
	}

	public String getParentId() {
		return parentId;
	}

	private void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Object getSelf() {
		return self;
	}

	private void setSelf(Object self) {
		this.self = self;
	}

	public String getQuestionParentId() {
		return questionParentId;
	}

	private void setQuestionParentId(String questionParentId) {
		this.questionParentId = questionParentId;
	}
	
	
}
