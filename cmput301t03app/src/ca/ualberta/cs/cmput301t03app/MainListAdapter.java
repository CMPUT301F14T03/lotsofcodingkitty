/*
 *  This class populates a custom list adapter full of custom question rows.
 * This is displayed in the Main Activity
 * by: Joshua Nguyen
 * */


package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<Question> {

	private int layoutResourceId;
	private Context context;
	private ArrayList<Question> questionList;
	
	public MainListAdapter(Context context, int layoutResourceId,
			ArrayList<Question> questionList) {
		super(context, layoutResourceId, questionList );
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.questionList = new ArrayList<Question>();
		this.questionList.addAll(questionList);
			
	}
	
	/* This is the custom holder with the custom elements for each row */
	public static class questionListHolder {
		ImageButton question_upvote_button;
		TextView question_upvote_score;
		ImageButton question_fav_icon;
		ImageButton question_viewed_icon;
		TextView question_title;
		TextView post_timestamp;
		TextView question_author;
	}
	

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		
		//LayoutInflator inflator 
		
		
		return null;
	}
	
	
	

}
