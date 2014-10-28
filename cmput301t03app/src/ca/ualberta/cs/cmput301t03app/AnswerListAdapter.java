package ca.ualberta.cs.cmput301t03app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerListAdapter extends ArrayAdapter<Answer> {

	private int layoutResourceId;
	private Context context;
	private static ArrayList<Answer> answerList;

	public AnswerListAdapter(Context context, int layoutResourceId,
			ArrayList<Answer> answerList) {
		super(context, layoutResourceId, answerList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.answerList = new ArrayList<Answer>();
		this.answerList.addAll(answerList);
	}

	public class answerListHolder {
		ImageButton answer_upvote_button;
		TextView answer_upvote_score;
		ImageView answer_fav_icon;
		TextView answer_text_body;
		TextView post_timestamp;
		TextView answer_author;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();

		answerListHolder holder = null;

		// Tell Listview which xml to find the formatting to.
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		row = vi.inflate(R.layout.activity_view_question_answer_entity, null);

		holder = new answerListHolder();

		holder.answer_text_body = (TextView) row
				.findViewById(R.id.answer_text_body);
		holder.answer_author = (TextView) row
				.findViewById(R.id.answer_author);
		holder.post_timestamp = (TextView) row
				.findViewById(R.id.post_timestamp);

		row.setTag(holder);
		
		
		Answer a = answerList.get(position);
		// Date to string
		// http://javarevisited.blogspot.ca/2011/09/convert-date-to-string-simpledateformat.html
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date_to_string = sdf.format(a.getDate());
		
		
		holder.answer_text_body.setText(a.getAnswer());
		holder.answer_author.setText("By: " + a.getAuthor());
		holder.post_timestamp.setText("Posted: " + date_to_string);

		return row;

	}

	// refresh Adapter Method calling in Question Activity
	public synchronized void updateAdapter(ArrayList<Answer> aList) {
		answerList.clear();
		answerList.addAll(aList);
		notifyDataSetChanged();
	}

}
