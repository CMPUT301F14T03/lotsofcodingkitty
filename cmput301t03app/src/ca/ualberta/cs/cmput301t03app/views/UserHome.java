package ca.ualberta.cs.cmput301t03app.views;

import ca.ualberta.cs.cmput301t03app.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
}
