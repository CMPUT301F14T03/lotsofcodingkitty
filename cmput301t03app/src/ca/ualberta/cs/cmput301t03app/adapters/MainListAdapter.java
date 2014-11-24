/*
 *  This class populates a custom list adapter full of custom question rows.
 * This is displayed in the Main Activity
 * @ author
 *  * */

package ca.ualberta.cs.cmput301t03app.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.UserPostCollector;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Custom formats a list of questions so that the specific attributes of the question can be
 *        seen in a listview according to a specified layout.
 */

public class MainListAdapter extends ArrayAdapter<Question> {

	private int layoutResourceId;
	private Context context;
	private ArrayList<Question> questionList;
	private PostController pc;
	
	/**
	 * Constructs a {@link #MainListAdapter() MainListAdapter}.
	 *  @param context The context of the adapter.
	 * @param layoutResourceId Resource ID of the listview using this adapter.
	 * @param incQuestionList The list of questions to be displayed in the listview.
	 */
	public MainListAdapter(Context context, int layoutResourceId,
			ArrayList<Question> incQuestionList) {

		super(context, layoutResourceId, incQuestionList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.questionList = new ArrayList<Question>();
		this.questionList.addAll(incQuestionList);
		this.pc = new PostController(context);
	}

	/**
	 * Class containing all of the view elements required for a question
	 * element in the UI.
	 */

	public static class questionListHolder {

		Question q;
		/* This is the custom holder with the custom elements for each row */
		ImageButton question_upvote_button;
		TextView question_upvote_score;
		ImageView question_fav_icon;
		ImageView question_pic_icon;
		ImageButton question_viewed_icon;
		TextView question_title;
		TextView post_timestamp;
		TextView question_author;
		TextView question_location;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		questionListHolder holder = null;
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		row = vi.inflate(R.layout.activity_main_question_entity, null); 
		holder = setupHolder(row);
		row.setTag(holder);
		
		holder.q = questionList.get(position);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date_to_string = sdf.format(holder.q.getDate());
		// Set the TextViews
		holder.question_title.setText(holder.q.getSubject());
		holder.question_upvote_score.setText(Integer.toString(holder.q.getRating()));
		holder.post_timestamp.setText("Posted: " + date_to_string);

		holder.question_author.setText("By: " + holder.q.getAuthor());

		
		if (holder.q.getGeoLocation() != null) {
			holder.question_location.setText("Location: " + holder.q.getGeoLocation().getCityName());
		}

		// Tell the Textviews where the info is coming from.
		holder.question_author.setTag(holder.q);
		holder.post_timestamp.setTag(holder.q);
		holder.question_upvote_score.setTag(holder.q);
		holder.question_title.setTag(holder.q);
		
		if (holder.q.getPicture() != null) {
			holder.question_pic_icon.setBackgroundResource(R.drawable.ic_picture_yes);
		}
		
		if (pc.isQuestionInFavByID(holder.q.getId())) 
			holder.question_fav_icon.setBackgroundResource(R.drawable.ic_fav_yes_small);

		return row;

	}

	/**
	 * Updates the adapter and refreshes the listview to reflect any changes to the list.
	 *@param qList The new list of questions that will be shown in the listview.
	 */
	
	public synchronized void updateAdapter(ArrayList<Question> qList) {
		questionList.clear();
		questionList.addAll(qList);
		notifyDataSetChanged();
	}
	
	/**Returns an {@link #questionListHolder questionListHolder} that can be used for an adapter.
	 *@param row The view that requires the adapter.
	 *@return A holder that now has the specified views to be used in the adapter.
	 */
	private questionListHolder setupHolder(View row) {

		/* setting up holder with XML IDs */
		questionListHolder holder = new questionListHolder();
		holder.question_fav_icon = (ImageView) row
				.findViewById(R.id.question_fav_icon);
		holder.question_pic_icon = (ImageView) row
				.findViewById(R.id.question_pic_icon);
		holder.question_title = (TextView) row
				.findViewById(R.id.question_title);
		holder.post_timestamp = (TextView) row
				.findViewById(R.id.post_timestamp);
		holder.question_author = (TextView) row
				.findViewById(R.id.question_author);
		holder.question_upvote_score = (TextView) row
				.findViewById(R.id.question_upvote_score);
		holder.question_location = (TextView) row
				.findViewById(R.id.question_location1);
		return holder;
	}

}
