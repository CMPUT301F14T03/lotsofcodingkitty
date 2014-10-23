/*
 *  This class populates a custom list adapter full of custom question rows.
 * This is displayed in the Main Activity
 * by: Joshua Nguyen
 * */


package ca.ualberta.cs.cmput301t03app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
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
		
		// Major contribs from :
				// http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html
		
		View row = convertView;
		
		//LayoutInflator inflator 
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		questionListHolder holder = null;
		
		// Tell Listview which xml to find the formatting to.
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		row = vi.inflate(R.layout.activity_main_question_entity, null);
		
		// Match the xml Ids with the TextViews
		holder = new questionListHolder();

		holder.question_title = (TextView) row.findViewById(R.id.question_title);
		holder.post_timestamp = (TextView) row.findViewById(R.id.post_timestamp);
		holder.question_author = (TextView) row.findViewById(R.id.question_author);
		holder.question_upvote_score = 
				(TextView) row.findViewById(R.id.question_upvote_score);

		row.setTag(holder);

		
		//Does the selected state change as well as loads into ListView as selected or not
		
		Question q=questionList.get(position);
		//Date to string
		// http://javarevisited.blogspot.ca/2011/09/convert-date-to-string-simpledateformat.html
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date_to_string= sdf.format(q.getDate());
		//Set the TextViews
		holder.question_title.setText(q.getSubject());
		holder.question_upvote_score.setText(Integer.toString(q.getRating()));
		holder.post_timestamp.setText("Posted: "+ date_to_string);
		holder.question_author.setText("By: "+q.getAuthor());
		// Tell the Textviews where the info is coming from.
		holder.question_author.setTag(q);
		holder.post_timestamp.setTag(q);
		holder.question_upvote_score.setTag(q);
		holder.question_title.setTag(q);
		return row;

	}
	
	// refresh Adapter Method calling in Homepage Activity

	   public synchronized void updateAdapter(ArrayList<Question> qList) {   
	    questionList.clear();
	    questionList.addAll(qList);
	    notifyDataSetChanged();
	   }
	
	
	

}
