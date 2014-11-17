/*
 * This class is the main access point to the application. 
 * 
 * @author
 * 
 * */
package ca.ualberta.cs.cmput301t03app.views;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.adapters.MainListAdapter;
import ca.ualberta.cs.cmput301t03app.controllers.GeoLocationTracker;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;

import ca.ualberta.cs.cmput301t03app.models.GeoLocation;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.utils.TakePicture;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Address; 
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * This activity is what is launched at the beginning of the application. It
 * loads questions into a listview that the user can browse through. This
 * activity is also where the user posts questions.
 * 
 */

public class MainActivity extends Activity
{
	protected Uri imageFileUri;
	protected boolean hasPicture = false;
	private ListView lv;
	private MainListAdapter mla;
	private PostController pc = new PostController(this);
	private ArrayList<Question> serverList = new ArrayList<Question>();
	public AlertDialog alertDialog1; // for testing purposes

	/**
	 * onCreate sets up the listview,sets the click listeners
	 * and runs the setupAdapter() method
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (pc.checkConnectivity() == false) {
		Toast.makeText(this,
				"You are not connected to the server. To access your locally saved data go to your userhome.",
				Toast.LENGTH_LONG).show();
		}
		ListView questionList = (ListView) findViewById(R.id.activity_main_question_list);
		questionList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id)
			{

				toQuestionActivity(position);
			}
		});

		questionList.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				{
					addToToRead(position);
				}
				return true;
			}
		});

		setupAdapter();
		Thread thread = new SearchThread("");
		thread.start();
		//pc.getQuestionsFromServer();
		//mla.updateAdapter(pc.getQuestionsInstance());
	}

	@Override
	public void onResume()
	{

		super.onResume();
		mla.updateAdapter(pc.getQuestionsInstance());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.user_home)
		{
			Intent intent = new Intent(this, UserHome.class);
			startActivity(intent);
		}
		if (id == R.id.search) {
			searchQuestions();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * onClick method for calling the dialog box to ask a question Dialog box
	 * requires a Question Title, Question Body, and Author
	 * 
	 * @param view
	 *            The View it got called from.
	 */

	@SuppressWarnings("deprecation")
	public void addQuestionButtonFunction(View view)
	{

		// Pops up dialog box for adding a question
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.dialog_activity_respond, null);// Get
																		// XML
																		// file
																		// to
																		// view
		final EditText questionTitle = (EditText) promptsView
				.findViewById(R.id.questionTitle);
		final EditText questionBody = (EditText) promptsView
				.findViewById(R.id.questionBody);
		final EditText userName = (EditText) promptsView
				.findViewById(R.id.UsernameRespondTextView);
		final ImageButton attachImg = (ImageButton) promptsView
				.findViewById(R.id.attachImg);
		final EditText userLocation = (EditText) promptsView
				.findViewById(R.id.userLocation);
		
		GeoLocation location = new GeoLocation();
//		GeoLocationTracker locationTracker = new GeoLocationTracker(this, location);
//		locationTracker.getLocation();
		
		location.setLatitude(53.53333);
		location.setLongitude(-113.5);
		
		String cityName = null;                
		Geocoder gcd = new Geocoder(getBaseContext(),   
		Locale.getDefault());               
		List<Address> addresses;    
		try {    
	      addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);   
	      
	      if (addresses.size() > 0)               
	         cityName=addresses.get(0).getLocality();    
		} catch (IOException e) {              
	        e.printStackTrace();    
		}
		
		if (cityName != null) {
			userLocation.setText(cityName);
		} else {
			userLocation.setText("No Location");
		}
		
		
		attachImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				TakePicture tp = new TakePicture();
//				tp.takeAPhoto();
				
				takeAPhoto();
				
			}
		});
		
		final CheckBox check = (CheckBox) promptsView
				.findViewById(R.id.enableLocation);
		check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);// Create a new AlertDialog

		alertDialogBuilder.setView(promptsView);// Link the alertdialog to the
												// XML
		alertDialogBuilder.setPositiveButton("Ask!",
				new DialogInterface.OnClickListener()
				{

					@Override
					// Building the dialog for adding
					public void onClick(DialogInterface dialog, int which)
					{

						String questionTitleString = (String) questionTitle
								.getText().toString();
						String questionBodyString = (String) questionBody
								.getText().toString();
						String userNameString = (String) userName.getText()
								.toString();

						Question q = new Question(questionTitleString,
								questionBodyString, userNameString);
						pc.addQuestion(q);

						if(hasPicture)
							q.setPicture(imageFileUri.getPath());
						
						
						pc.addUserPost(q);
						Thread thread = new AddThread(q);
						thread.start();
						mla.updateAdapter(pc.getQuestionsInstance());

					}

				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						// Do nothing
						dialog.cancel();
					}
				});

		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog1 = alertDialog;
		alertDialog.show();
		alertDialog.getButton(AlertDialog.BUTTON1).setEnabled(false);

		TextWatcher textwatcher = new TextWatcher()
		{

			// creating a listener to see if any changes to edit text in dialog
			private void handleText()
			{

				final Button button = alertDialog
						.getButton(AlertDialog.BUTTON_POSITIVE);
				if (questionTitle.getText().length() == 0)
				{ // these checks the edittext to make sure not empty edit text
					button.setEnabled(false);
				} else if (questionBody.getText().length() == 0)
				{
					button.setEnabled(false);
				} else if (userName.getText().length() == 0)
				{
					button.setEnabled(false);
				} else
				{
					button.setEnabled(true);
				}
			}

			@Override
			public void afterTextChanged(Editable s)
			{

				handleText();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{

				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{

				// do nothing
			}
		};

		questionTitle.addTextChangedListener(textwatcher); // adding listeners
															// to the edittexts
		questionBody.addTextChangedListener(textwatcher);
		userName.addTextChangedListener(textwatcher);
		Toast.makeText(this, "Please write your question", Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * This function is called when the user long clicks on a question
	 * in the list view. It allows the user to save the question in the 
	 * ToRead list so that they can read the question later.
	 * @param position A final int from the question position the 
	 * 	long click was called from
	 * 			
	 * 
	 */

	public void addToToRead(final int position)
	{

		AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
		editDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						dialog.cancel();
					}
				}).setPositiveButton("Add to To-Read",
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						pc.addToRead(pc.getQuestionsInstance().get(position));
						Toast.makeText(MainActivity.this,
								"Added to To-Read List", Toast.LENGTH_SHORT)
								.show();
					}
				});

		AlertDialog alertDialog = editDialog.create();
		alertDialog.show();
	}
	

	public AlertDialog getDialog()
	{ // this is for testing purposes

		return alertDialog1;
	}

	public MainListAdapter getAdapter()
	{ // this is for testing purposes

		return mla;
	}

	/**
	 * Sets the adapter for the list view.
	 */
	private void setupAdapter()
	{

		lv = (ListView) findViewById(R.id.activity_main_question_list);
		mla = new MainListAdapter(this, R.layout.activity_main_question_entity,
				pc.getQuestionsInstance());
		//TODO: Show questions in question bank when user first opens app if not connected
		lv.setAdapter(mla);
	}

	/**
	 *  This method runs when the user chooses to answer
	 *  a question. It creates an intent and adds the question ID
	 *  to the intent then it starts the ViewQuestion activity.
	 *  
	 * @param position The position of the question clicked
	 */
	public void toQuestionActivity(int position)
	{

		Intent i = new Intent(this, ViewQuestion.class);
		i.putExtra("question_id", pc.getQuestionsInstance().get(position)
				.getId());
		pc.addReadQuestion(pc.getQuestionsInstance().get(position));
		startActivity(i);
	}
	
	/**
	 * This is the onClic
	 * @param view
	 */
	
	public void searchQuestions() {
		LayoutInflater li = LayoutInflater.from(this);
		View searchView = li.inflate(R.layout.search_dialog, null);
		final EditText searchField = (EditText) searchView
				.findViewById(R.id.searchField);

		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		alertDialogBuilder.setView(searchView);
		alertDialogBuilder.setPositiveButton("Search",
				new DialogInterface.OnClickListener()
				{

					@Override
					// Building the dialog for adding
					public void onClick(DialogInterface dialog, int which)
					{

						String searchString = "";
						if (searchField.getText().toString() != "" || searchField.getText().toString() != null){
							searchString = (String) searchField
									.getText().toString();
						}
						
						final String finalString = searchString;
						new Thread() {
							public void run() {
								pc.executeSearch(finalString);
							}
						}.start();
						
						// Give some time to get updated info
						try {
							Thread.currentThread().sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mla.updateAdapter(pc.getQuestionsInstance());
					}

				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						// Do nothing
						dialog.cancel();
					}
				});

		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog1 = alertDialog;
		alertDialog.show();
//		alertDialog.getButton(AlertDialog.BUTTON1).setEnabled(false);
	}

	
	
	 public void takeAPhoto() {
		 	/* Main Activity is getting pretty bloated so I'm trying to move this out into the Utils package */
	    	String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCameraTest";
			File folder = new File(path);
			
			if (!folder.exists()){
				folder.mkdir();
			}
				
			String imagePathAndFileName = path + File.separator + 
					String.valueOf(System.currentTimeMillis()) + ".jpg" ; //makes the timestamp as part of the filename
			
			File imageFile = new File(imagePathAndFileName);
			imageFileUri = Uri.fromFile(imageFile); 
			
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
			startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE); // sets the ID for when the CAmera app sends it back here. 
			// matches the ID to the request code in onActivityResult
			
	    }
	    
	    
	    private final int CAMERA_ACTIVITY_REQUEST_CODE = 12345;
	    
	    
	    //This method is run after returning back from camera activity:
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			
	    	if (requestCode == CAMERA_ACTIVITY_REQUEST_CODE){
				//TextView tv = (TextView)findViewById(R.id.status); // THE TEXT VIEW THAT YOU SEE ON SCREEN
				
				if (resultCode == RESULT_OK){
					hasPicture = true;
					Log.d("click", "Imag efile path: "+imageFileUri.getPath());
//					tv.setText("Photo completed");
//					ImageButton ib = (ImageButton) findViewById(R.id.TakeAPhoto);
//					ib.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath())); // need to use GETPATH
					
					
				}
				else
					if (resultCode == RESULT_CANCELED){
						
					}
					
			}
	    }
	    
	    public void loadMoreQuestions(View view) {
	    	pc.loadServerQuestions(serverList);
	    	mla.updateAdapter(pc.getQuestionsInstance());
	    }
	    
	    private Runnable doUpdateGUIList = new Runnable() {
	    	public void run() {
	    		mla.updateAdapter(pc.getQuestionsInstance());
	    	}
	    };
	    
	    private Runnable doFinishAdd = new Runnable() {
	    	public void run() {
	    		finish();
	    	}
	    };
	    
	    class SearchThread extends Thread {
	    	private String search;
	    	
	    	public SearchThread(String s) {
	    		search = s;
	    	}
	    	
	    	@Override
	    	public void run() {
	    		pc.getQuestionsInstance().clear();
	    		serverList = pc.getQuestionsFromServer();
	    		pc.loadServerQuestions(serverList);
	    		runOnUiThread(doUpdateGUIList);
	    	};
	    }
	    
	    class AddThread extends Thread {
	    	private Question question;
	    	
	    	public AddThread(Question question) {
	    		this.question = question;
	    	}
	    	
	    	@Override
	    	public void run() {
	    		pc.pushNewPosts();
	    		try {
	    			Thread.sleep(500);
	    		} catch(InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		
	    		runOnUiThread(doFinishAdd);
	    	}
	    }
	
}
