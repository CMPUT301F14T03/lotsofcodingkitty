package ca.ualberta.cs.cmput301t03app.views;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Creates the view known as the "User Home" provides the user with an list
 * interface that will give the user options as to which list of questions to
 * load onto the screen. The options are:<br>
 * - Favorites<br>- Cached<br> - To read<br> - My
 * Questions (user posted questions).
 * 
 */

public class UserHome extends Activity {
	/**
	 * Basic Android onCreate method. The content layout is the activity_user_home GUI
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		
		getActionBar().setHomeButtonEnabled(true);
	}

	/**
	 * The onClick for the user_fav_button on the GUI for this activity's layout.  By clicking
	 * the button, the application will start the UserListsActivity with userListMode = 0 which
	 * will tell the UserListsActivity which list to load (0 = favorites list)
	 * @param view is the activity where the view is called from
	 */
	public void favorites(View view) {
		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 0);
		startActivity(intent);
	}

	/**
	 * The onClick for the user_cached_button on the GUI for this activity's layout.  By clicking
	 * the button, the application will start the UserListsActivity with userListMode = 1 which
	 * will tell the UserListsActivity which list to load (1 = cached/read list)
	 * @param view is the activity where the view is called from
	 */

	public void viewCached(View view) {
		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 1);
		startActivity(intent);
	}

	/**
	 * The onClick for the user_toRead_button on the GUI for this activity's layout.  By clicking
	 * the button, the application will start the UserListsActivity with userListMode = 2 which
	 * will tell the UserListsActivity which list to load (2 = to read list)
	 * @param view is the activity where the view is called from
	 */

	public void toRead(View view) {
		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 2);
		startActivity(intent);
	}

	/**
	 * The onClick for the user_questions_button on the GUI for this activity's layout.  By clicking
	 * the button, the application will start the UserListsActivity with userListMode = 3 which
	 * will tell the UserListsActivity which list to load (3 = user posted questions list)
	 * @param view is the activity where the view is called from
	 */

	public void postedQuestions(View view) {

		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 3);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_question, menu);
		menu.getItem(1).setVisible(false);
		menu.getItem(2).setVisible(false);
		menu.getItem(0).setVisible(false);
		getActionBar().setHomeButtonEnabled(true);
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		switch (item.getItemId()) {
		case android.R.id.home:
			runOnUiThread(doFinish);
			break;
		
		
		case R.id.sync:
			final PostController pc = new PostController(this);
			pc.pushNewPosts();
			new Thread() {
				public void run() {
					pc.executeSearch("");
					
				}
			}.start();
			
			// Give some time to get updated info
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pc.sortQuestions(0);
			
		}
		
		return (super.onOptionsItemSelected(item));
	}
	
	private Runnable doFinish = new Runnable() {
		public void run() {
			finish();
		}
	};
}
