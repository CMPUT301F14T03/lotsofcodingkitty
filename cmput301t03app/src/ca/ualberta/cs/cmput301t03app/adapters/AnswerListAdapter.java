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

/**
 * 
 *        Custom formats a list of answers so that the specific attributes of the answer can be
 *        seen in a listview according to a specified layout.
 *
 */

public class AnswerListAdapter extends ArrayAdapter<Answer> {
	
	private static ArrayList<Answer> answerList;
	
	/**
	 * Constructs an {@link #AnswerListAdapter() answerListAdapter}.
	 * @param context The context of the adapter.
	 * @param layoutResourceId The resourceID of the layout being used for the adapter.
	 * @param answerList The list of answers to be displayed in the listview.
	 */
	public AnswerListAdapter(Context context, int layoutResourceId, ArrayList<Answer> answerList){
		super(context, layoutResourceId, answerList);
		this.answerList = new ArrayList<Answer>();
		this.answerList.addAll(answerList);
	}

	/**
	 * Class containing all of the view elements required for an answer
	 * element in the UI.
	 */

	public class answerListHolder {

		Answer answer;
		ImageButton answer_upvote_button;
		ImageButton answer_comment_icon;
		ImageButton answer_picture;
		TextView answer_upvote_score;
		ImageView answer_fav_icon;
		TextView answer_text_body;
		TextView post_timestamp;
		TextView answer_author;
		TextView answer_comment_count;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		View row = vi.inflate(R.layout.activity_view_question_answer_entity,
				null);
		answerListHolder holder = getNewAnswerHolder(row);
		// This sets the answer to the object. So in view question you can get
		// the answer
		// that was clicked.
		holder.answer = answerList.get(position);
		holder.answer_upvote_button.setTag(holder.answer);
		holder.answer_text_body.setTag(holder.answer);
		holder.answer_comment_icon.setTag(holder.answer);
		holder.answer_picture.setTag(holder.answer);
		row.setTag(holder);
		// Date to string
		// http://javarevisited.blogspot.ca/2011/09/convert-date-to-string-simpledateformat.html
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date_to_string = sdf.format(holder.answer.getDate());
		holder.answer_text_body.setText(holder.answer.getAnswer());
		holder.answer_author.setText("By: " + holder.answer.getAuthor());
		holder.post_timestamp.setText("Posted: " + date_to_string);
		holder.answer_upvote_score.setText(Integer.toString(holder.answer
				.getRating()));
		holder.answer_comment_count.setText(Integer.toString(holder.answer.getComments().size()));
		
		if (holder.answer.getPicture() != null) {
			holder.answer_picture.setBackgroundResource(R.drawable.ic_picture_yes);
		} else {
			holder.answer_picture.setBackgroundResource(R.drawable.ic_picture_no);
		}
				
		return row;
	}
	
	
	/**
	 * Updates the adapter and refreshes the listview to reflect any changes to the list.
	 *@param answerList The new list of answers that will be shown in the listview.
	 */

	public synchronized void updateAdapter(ArrayList<Answer> answerList) {
		answerList.clear();
		answerList.addAll(answerList);
		notifyDataSetChanged();
	}

	// This is called from the ViewQuestion when an answer is upvoted.

	public void notifyChange() {
		notifyDataSetChanged();
	}
	
	/**Returns an {@link #answerListHolder answerListHolder} that can be used for an adapter.
	 *@param row The view that requires the adapter.
	 *@return A holder that now has the specified views to be used in the adapter.
	 */
	private answerListHolder getNewAnswerHolder(View row) {

		answerListHolder holder = new answerListHolder();
		holder.answer_text_body = (TextView) row
				.findViewById(R.id.answer_text_body);
		holder.answer_author = (TextView) row.findViewById(R.id.answer_author);
		holder.post_timestamp = (TextView) row
				.findViewById(R.id.post_timestamp);
		holder.answer_upvote_score = (TextView) row
				.findViewById(R.id.answer_upvote_score);
		holder.answer_upvote_button = (ImageButton) row
				.findViewById(R.id.answer_upvote_button);
		holder.answer_comment_icon = (ImageButton) row.findViewById(R.id.answer_comment_icon);
		holder.answer_comment_count = (TextView) row.findViewById(R.id.answer_comment_count);
		holder.answer_picture = (ImageButton) row.findViewById(R.id.answer_picture);
		
		return holder;
	}

	// This is for testing.
	public int getCount() {
		return answerList.size();
	}
}
