package ca.ualberta.cs.cmput301t03app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Respond extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView(R.layout.activity_respond);
	}
	
	public void okayResponse(View view) {
	// Get the text put in the body and the title
	// Use the controllers to add the new data into the model
	// Return from the activity
	}
	
	public void cancelResponse(View view) {
	// Return from the activity
		
	}
}
