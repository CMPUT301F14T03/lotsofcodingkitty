package ca.ualberta.cs.cmput301t03app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ViewQuestion extends Activity {
	PostController pc = new PostController();
	UserPostCollector upc = new UserPostCollector(this);
	ImageButton favIcon;
	ImageButton upvoteButton;
	TextView upvote_score;
	String question_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_question);

		Bundle extras = getIntent().getExtras();
		question_id = extras.getString("question_id");

		setQuestionText(question_id);

		favIcon = (ImageButton) findViewById(R.id.question_fav_icon);
		upvoteButton = (ImageButton) findViewById(R.id.question_upvote_button);
		upvote_score = (TextView) findViewById(R.id.question_upvote_score);

		favIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setFavorited();
				favIcon.setImageResource(R.drawable.ic_fav_yes);
			}
		});

		upvoteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer.toString(pc.getQuestion(question_id).getRating());
				increment_upvote();
			}
		});
	}

	public void setQuestionText(String ID) {
		Question q = pc.getQuestion(ID);
		TextView q_title = (TextView) findViewById(R.id.question_title);
		TextView upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		TextView q_body = (TextView) findViewById(R.id.question_text_body);
		TextView q_author = (TextView) findViewById(R.id.question_author);
		TextView q_date = (TextView) findViewById(R.id.post_timestamp);
		upvote_score.setText(Integer.toString(pc.getQuestion(question_id)
				.getRating()));
		q_title.setText(q.getSubject());
		q_body.setText(q.getBody());
		q_author.setText(q.getAuthor());
		q_date.setText(q.getDate().toString());

		// Log.d("click", "Contains? " +
		// upc.getFavoriteQuestions().contains(pc.getQuestion(question_id)));
		//
		// if(upc.getFavoriteQuestions().contains(pc.getQuestion(question_id)))
		// {
		// favIcon.setImageResource(R.drawable.ic_fav_yes);
		// }

	}

	public void setFavorited() {
		upc.addFavoriteQuestions(pc.getQuestion(question_id));
		//Log.d("click", "Favs: " + upc.getFavoriteQuestions());
	}

	public void increment_upvote() {
		pc.getQuestion(question_id).upRating();
		Log.d("click",
				Integer.toString(pc.getQuestion(question_id).getRating()));
		TextView upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		upvote_score.setText(Integer.toString(pc.getQuestion(question_id)
				.getRating()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
