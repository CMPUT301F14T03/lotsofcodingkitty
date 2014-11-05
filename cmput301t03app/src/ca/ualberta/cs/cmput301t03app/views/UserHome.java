package ca.ualberta.cs.cmput301t03app.views;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserHome extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_user_home);
	}
	/*these are the onClick methods called by the buttons on layout*/
	public void favorites (View view) {
		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 0);
		startActivity(intent);
	}
	
	public void viewCached (View view) {
		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 1);
		startActivity(intent);
	}

	public void toRead (View view) {
		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 2);
		startActivity(intent);
	}
	
	public void postedQuestions (View view) {
		Intent intent = new Intent(this, UserListsActivity.class);
		intent.putExtra("userListMode", 3);
		startActivity(intent);
	}
}
