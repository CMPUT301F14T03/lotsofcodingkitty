package ca.ualberta.cs.cmput301t03app.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerListAdapter extends ArrayAdapter<Answer> {

	private static ArrayList<Answer> answerList;

	public AnswerListAdapter(Context context, int layoutResourceId,
			ArrayList<Answer> answerList) {
		super(context, layoutResourceId, answerList);
		this.answerList = new ArrayList<Answer>();
		this.answerList.addAll(answerList);
	}

	public class answerListHolder {
		Answer answer;
		ImageButton answer_upvote_button;
		TextView answer_upvote_score;
		ImageView answer_fav_icon;
		TextView answer_text_body;
		TextView post_timestamp;
		TextView answer_author;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		View row = vi.inflate(R.layout.activity_view_question_answer_entity, null);

		answerListHolder holder = getNewHolder(row);
		// This sets the answer to the object. So in view question you can get the answer
		// that was clicked.
		holder.answer = answerList.get(position);
		holder.answer_upvote_button.setTag(holder.answer);
		holder.answer_text_body.setTag(holder.answer);
		row.setTag(holder);
		// Date to string
		// http://javarevisited.blogspot.ca/2011/09/convert-date-to-string-simpledateformat.html
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date_to_string = sdf.format(holder.answer.getDate());
		holder.answer_text_body.setText(holder.answer.getAnswer());
		holder.answer_author.setText("By: " + holder.answer.getAuthor());
		holder.post_timestamp.setText("Posted: " + date_to_string);
		holder.answer_upvote_score.setText(Integer.toString(holder.answer.getRating()));
		return row;
	}

	// refresh Adapter Method calling in Question Activity
	public synchronized void updateAdapter(ArrayList<Answer> aList) {
		answerList.clear();
		answerList.addAll(aList);
		notifyDataSetChanged();
	}
	
	// This is called from the ViewQuestion when an answer is upvoted.
	public void notifyChange(){
		notifyDataSetChanged();
	}
	
	private answerListHolder getNewHolder(View row){
		answerListHolder holder = new answerListHolder();
		holder.answer_text_body = (TextView) row
				.findViewById(R.id.answer_text_body);
		holder.answer_author = (TextView) row
				.findViewById(R.id.answer_author);
		holder.post_timestamp = (TextView) row
				.findViewById(R.id.post_timestamp);
		holder.answer_upvote_score = (TextView) row
				.findViewById(R.id.answer_upvote_score);
		holder.answer_upvote_button = (ImageButton) row.findViewById(R.id.answer_upvote_button);
		return holder;
	}
	// This is for testing.
	public int getCount(){
		return answerList.size();
	}
}
