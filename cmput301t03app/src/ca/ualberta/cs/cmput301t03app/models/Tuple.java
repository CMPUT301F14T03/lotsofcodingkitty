package ca.ualberta.cs.cmput301t03app.models;


public class Tuple
{
	private String qId;
	private String aId;
	private Comment comment;
	
	
	public Tuple(String qId,String aId, Comment comment){
		this.qId=qId;
		this.aId=aId;
		this.comment=comment;
	}
	
	public String getqId()
	{
	
		return qId;
	}

	
	public void setqId(String qId)
	{
	
		this.qId = qId;
	}

	
	public String getaId()
	{
	
		return aId;
	}

	
	public void setaId(String aId)
	{
	
		this.aId = aId;
	}

	
	public Comment getComment()
	{
	
		return comment;
	}

	
	public void setComment(Comment comment)
	{
	
		this.comment = comment;
	}


}
