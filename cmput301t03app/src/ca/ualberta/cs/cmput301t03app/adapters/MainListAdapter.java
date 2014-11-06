/*
 *  This class populates a custom list adapter full of custom question rows.
 * This is displayed in the Main Activity
 * @ author
 *  * */


package ca.ualberta.cs.cmput301t03app.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.models.Question;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<Question> {

	private int layoutResourceId;
	private Context context;
	private ArrayList<Question> questionList;
	
	public MainListAdapter(Context context, int layoutResourceId,
			ArrayList<Question> incQuestionList) {
		super(context, layoutResourceId, incQuestionList );
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.questionList = new ArrayList<Question>();
		this.questionList.addAll(incQuestionList);
	}
	
	public static class questionListHolder {
			/* This is the custom holder with the custom elements for each row */
		ImageButton question_upvote_button;
		TextView question_upvote_score;
		ImageView question_fav_icon;
		ImageButton question_viewed_icon;
		TextView question_title;
		TextView post_timestamp;
		TextView question_author;
	}
	
	/**
	 * Major contribution from :
				http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html (Adapter)
				http://javarevisited.blogspot.ca/2011/09/convert-date-to-string-simpledateformat.html (Date to string)
	 * 
	 This sets up the XML IDs with the layout of the ListView*/
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		questionListHolder holder = null;
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		row = vi.inflate(R.layout.activity_main_question_entity, null);		// Tell Listview which xml to find the formatting to.
		holder = setupHolder(row);
		row.setTag(holder);
		
		Question q = questionList.get(position);
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
	
	public synchronized void updateAdapter(ArrayList<Question> qList) {
			/*method used to refresh MainActivity*/
		questionList.clear();
	    questionList.addAll(qList);
	    notifyDataSetChanged();
	}
	  
	private questionListHolder setupHolder(View row){
			/*setting up holder with XML IDs*/
		questionListHolder holder = new questionListHolder();
		holder.question_fav_icon = (ImageView) row.findViewById(R.id.question_fav_icon);
		holder.question_title = (TextView) row.findViewById(R.id.question_title);
		holder.post_timestamp = (TextView) row.findViewById(R.id.post_timestamp);
		holder.question_author = (TextView) row.findViewById(R.id.question_author);
		holder.question_upvote_score = (TextView) row.findViewById(R.id.question_upvote_score);
		return holder;
	}
	
	

}
