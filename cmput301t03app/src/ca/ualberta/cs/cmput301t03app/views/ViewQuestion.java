package ca.ualberta.cs.cmput301t03app.views;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.R;
import ca.ualberta.cs.cmput301t03app.adapters.AnswerListAdapter;
import ca.ualberta.cs.cmput301t03app.controllers.GeoLocationTracker;
import ca.ualberta.cs.cmput301t03app.controllers.PictureController;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.GeoLocation;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity.PushThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * 
 * 
 * This is the Activity responsible for allowing a user to view further details
 * about a Question (the Question body text), view answers to a given question,
 * as well as add answers, add comments to the question, and add comments to
 * answers.
 * 
 */

public class ViewQuestion extends Activity {

	public static final String SET_COMMENT_TYPE = "0";
	public static final int COMMENT_ON_QUESTION_KEY = 1;
	public static final int COMMENT_ON_ANSWER_KEY = 2;
	public static final String QUESTION_ID_KEY = "3";
	public static final String ANSWER_ID_KEY = "4";
	
	private final int CAMERA_ACTIVITY_REQUEST_CODE = 12345;
	private final int GALLERY_ACTIVITY_REQUEST_CODE = 67890;
	
	protected boolean hasLocation = false;
	protected boolean hasPicture = false;
	protected GeoLocation location;
	protected String cityName;
	private PostController pc = new PostController(this);
	private PictureController pictureController = new PictureController(this);	
	private ArrayList<Answer> answerList = new ArrayList<Answer>();
	public AlertDialog dialog; // for testing
	AnswerListAdapter ala;
	Uri imageFileUri;	
	
	//UI Objects
	private static ImageButton favIcon;
	ListView answerListView;
	ImageButton upvoteButton;
	ImageButton commentButton;
	TextView upvote_score;
	Button answerButton;
	String question_id;
	TextView answerCounter;
	TextView commentCounter;
	ImageView questionPictureButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view_question);
		
		/* Removes the actionbar title text */
		getActionBar().setDisplayShowTitleEnabled(false);

		// enables the activity icon as a 'home' button. required if
		// "android:targetSdkVersion" > 14
		getActionBar().setHomeButtonEnabled(true);

		Bundle extras = getIntent().getExtras();
		question_id = extras.getString("question_id"); // grabbing question to
														// be displayed
		instantiateViews();
		setQuestionText(question_id);
		updateAnswerCount();
		setListeners();
		setFavoriteIcon();
		setAnswerAdapter();
		updateCommentCount(); // updates comments counter
	}

	@Override
	protected void onResume() {

		super.onResume();
		updateCommentCount();
		ala.updateAdapter(answerList);
		setFavoriteIcon();
	}

	/**
	 * Listeners for the given buttons in the activity
	 */

	public void setListeners() {

		
		answerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				answerQuestion();
			}
		});
		// listener to see if click on comment question
		commentButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				toCommentActivityQuestion(v);
			}
		});
		// listener to favorite a question
		favIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				pc.addFavoriteQuestion(pc.getQuestion(question_id));
				favIcon.setImageResource(R.drawable.ic_fav_yes);
				setFavoriteIcon();
				Toast.makeText(ViewQuestion.this, "Added to Favorites List.",
						Toast.LENGTH_SHORT).show();
			}
		});
		// listener to upvote when clicked
		upvoteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pc.checkConnectivity()) {
					Integer.toString(pc.getQuestion(question_id).getRating());
					increment_upvote();
				} else {
					pc.cantUpvoteMsg();
				}
			}
		});
		questionPictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toPictureActivityQuestion(v);
			}
		});

	}

	/**
	 * Assigns the AnswerListAdapter to the AnswerList and populates it with
	 * answers to the given Question
	 */

	public void setAnswerAdapter() {

		answerListView = (ListView) findViewById(R.id.answerListView);
		populateThisQuestionsAnswers(question_id);
		ala = new AnswerListAdapter(this, R.id.answerListView, answerList);
		answerListView.setAdapter(ala);

		/* Change icon if has Picture for answers */
	}

	public void populateThisQuestionsAnswers(String question_id) {

		answerList.clear();
		Log.v("THIS", pc.getQuestion(question_id).getAnswers().toString());
		answerList.addAll(pc.getQuestion(question_id).getAnswers());
	}



	public View getViewByPosition(int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition
				+ listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	/**
	 * Takes a questionId and uses that ID to fill in the various elements of a
	 * Question in the view.
	 * 
	 * @param ID
	 *            The id of teh question
	 */

	public void setQuestionText(String ID) {

		Question q = pc.getQuestion(ID);
		TextView q_title = (TextView) findViewById(R.id.question_title);
		TextView upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		TextView q_body = (TextView) findViewById(R.id.question_text_body);
		TextView q_author = (TextView) findViewById(R.id.question_author);
		TextView q_date = (TextView) findViewById(R.id.post_timestamp);
		TextView q_location = (TextView) findViewById(R.id.question_location1);
		upvote_score.setText(Integer.toString(pc.getQuestion(question_id)
				.getRating()));
		q_title.setText(q.getSubject());
		q_body.setText(q.getBody());
		q_author.setText("By: " + q.getAuthor());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date_to_string = sdf.format(q.getDate());
		q_date.setText("Posted: " + date_to_string);

		if (q.getPicture() != null)
			questionPictureButton.setImageResource(R.drawable.ic_picture_yes);

		
		//Sets city name for location
		if (q.getGeoLocation()!= null) {
			q_location.setText("Location: " + q.getGeoLocation().getCityName());
		}
	}

	/**
	 * Fills the favorite icon
	 */
	public void setFavoriteIcon() {

		if (pc.isQuestionInFavByID(question_id)) {
			favIcon.setImageResource(R.drawable.ic_fav_yes);
		}
	}

	/**
	 * Instrantiates the views
	 */

	public void instantiateViews() {

		// thisQuestion = (TextView) findViewById(R.id.question_title);
		answerListView = (ListView) findViewById(R.id.answerListView);
		favIcon = (ImageButton) findViewById(R.id.question_fav_icon);
		upvoteButton = (ImageButton) findViewById(R.id.question_upvote_button);
		commentButton = (ImageButton) findViewById(R.id.question_comment_icon);
		upvote_score = (TextView) findViewById(R.id.question_upvote_score);
		answerButton = (Button) findViewById(R.id.question_answer_button);
		answerCounter = (TextView) findViewById(R.id.answer_count);
		commentCounter = (TextView) findViewById(R.id.question_comment_count);
		answerListView = (ListView) findViewById(R.id.answerListView);
		questionPictureButton = (ImageView) findViewById(R.id.question_picture_button);
	}

	/**
	 * onClick for commenting on a question
	 * 
	 * @param v
	 *            View where the click happened
	 */

	public void toCommentActivityQuestion(View v) {

		/* This method takes user to ViewComment activity for questions */
		Intent i = new Intent(this, ViewComment.class);
		i.putExtra(SET_COMMENT_TYPE, COMMENT_ON_QUESTION_KEY);
		i.putExtra(QUESTION_ID_KEY, question_id);
		startActivity(i);
	}

	/**
	 * onClick for commenting on an answer
	 * 
	 * @param v
	 */

	public void toCommentActivityAnswer(View v) {

		/* This method takes user to ViewComment activity for answers */
		Answer answer = (Answer) v.getTag();
		Intent i = new Intent(this, ViewComment.class);
		i.putExtra(SET_COMMENT_TYPE, COMMENT_ON_ANSWER_KEY);
		i.putExtra(QUESTION_ID_KEY, question_id);
		i.putExtra(ANSWER_ID_KEY, answer.getId());
		startActivity(i);
	}

	/**
	 * onClick for going to Picture activity for question
	 * 
	 * @param v
	 *            View where the click happened
	 */

	public void toPictureActivityQuestion(View v) {
		/* This method takes user to ViewPicture activity for questions */

		if (pc.getQuestion(question_id).getPicture() != null) {
					
			Intent i = new Intent(this, ViewPicture.class);
			i.putExtra(SET_COMMENT_TYPE, 1);
			i.putExtra(QUESTION_ID_KEY, question_id);
			Log.d("click", "Leaving View picture");
			startActivity(i);
		}
	}

	/**
	 * onClick for going to Picture activity for question
	 * 
	 * @param v
	 *            View where the click happened
	 */

	public void toPictureActivityAnswer(View v) {
		/* This method takes user to ViewPicture activity for answers */
			
		Answer answer = (Answer) v.getTag();
		
		if (pc.getAnswer(answer.getId(), question_id).getPicture() != null) {
	
			Intent i = new Intent(this, ViewPicture.class);
			i.putExtra(SET_COMMENT_TYPE, 2);
			i.putExtra(QUESTION_ID_KEY, question_id);
			i.putExtra(ANSWER_ID_KEY, answer.getId());
			startActivity(i);
		}
	}

	/**
	 * onClick method for adding an answer to the question Prompts the user with
	 * a dialog box which requests answer body text (answerBodyString) as well
	 * as a user name (userNameString). When the dialog is complete, the
	 * adapters update method is called.
	 */

	public void answerQuestion() {

		/* when clicked answer question dialog box pops up here */
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.activity_post_dialog, null);
		final EditText answerBody = (EditText) promptsView
				.findViewById(R.id.postBody);
		final EditText userName = (EditText) promptsView
				.findViewById(R.id.UsernameRespondTextView);
		final ImageButton attachImg = (ImageButton) promptsView
				.findViewById(R.id.attachImg);
		final ProgressBar spinner = (ProgressBar) promptsView
				.findViewById(R.id.progressBar1);
		spinner.setVisibility(View.GONE);
		attachImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				pictureChooserDialog();

			}
		});


		final EditText userLocation = (EditText) promptsView
				.findViewById(R.id.userLocation2);
		
		CheckBox check = (CheckBox) promptsView
				.findViewById(R.id.enableLocation2);
		check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hasLocation=!hasLocation;
				
				if (hasLocation) {
					spinner.setVisibility(View.VISIBLE);
					
					location = new GeoLocation();
					GeoLocationTracker locationTracker = new GeoLocationTracker(ViewQuestion.this, location);
					locationTracker.getLocation();
					
//					location.setLatitude(53.53333);
//					location.setLongitude(-113.5);
					
					//Delay for 7 seconds
				    Handler handler = new Handler(); 
				    handler.postDelayed(new Runnable() { 
				         public void run() { 
				        	 cityName = pc.getCity(location);
				        	 Log.d("Loc","Timer is done");
							if (cityName != null) {
								userLocation.setText(cityName);
								location.setCityName(cityName);
							} else {
								userLocation.setText("Location not found.");
							}
							spinner.setVisibility(View.GONE);
				         } 
				    }, 7000); 
				}
				
			}
		});

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); // Create
																				// a
																				// new
																				// AlertDialog
		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder.setPositiveButton("Answer",
				new DialogInterface.OnClickListener() {

					@Override
					// Building the dialog for adding
					public void onClick(DialogInterface dialog, int which) {

						String answerBodyString = (String) answerBody.getText()
								.toString();
						String userNameString = (String) userName.getText()
								.toString();

						final Answer a = new Answer(answerBodyString, userNameString,
								question_id);
						
						if (hasPicture){
							FileInputStream in;
							BufferedInputStream buf;
							try {
								in = new FileInputStream(imageFileUri.getPath());
								buf = new BufferedInputStream(in);
								Bitmap _bitmapPreScale = BitmapFactory.decodeStream(buf);
								int oldWidth = _bitmapPreScale.getWidth();
								int oldHeight = _bitmapPreScale.getHeight();
								int newWidth = 200; 
								int newHeight = 200;
								
								float scaleWidth = ((float) newWidth) / oldWidth;
								float scaleHeight = ((float) newHeight) / oldHeight;
								
								Matrix matrix = new Matrix();
								// resize the bit map
								matrix.postScale(scaleWidth, scaleHeight);
								Bitmap _bitmapScaled = Bitmap.createBitmap(_bitmapPreScale, 0, 0,  oldWidth, oldHeight, matrix, true);
								ByteArrayOutputStream bytes = new ByteArrayOutputStream();
								_bitmapScaled.compress(Bitmap.CompressFormat.PNG, 0, bytes);
								a.setPicture(bytes.toByteArray());
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						String userLocationString = (String) userLocation.getText()
								.toString();
						
						
						
						if(hasLocation){
							//Set location if location typed by user is same as location found
							if (userLocationString.equals(cityName)){
								a.setGeoLocation(location);
							}
							//Find the coordinates of place entered by user and set location
							else{
								a.setGeoLocation(pc.turnFromCity(userLocationString));
								//Testing
								GeoLocation testlocation= pc.turnFromCity(userLocationString);
								Log.d("Location",Double.toString(testlocation.getLatitude()));
								Log.d("Location",Double.toString(testlocation.getLongitude()));
								
							}
						}
						
						populateThisQuestionsAnswers(question_id);
						pc.getQuestion(question_id).addAnswer(a);
						updateAnswerCount();
						if(pc.checkConnectivity()) {
							Thread thread = new AnswerQuestion(question_id, a);
							thread.start();
						} else {
							pc.addPushAnsAndComm(question_id, a.getId(), null);
						}

						try {
							Thread.currentThread().sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						populateThisQuestionsAnswers(question_id);
						ala.updateAdapter(answerList);
						hasLocation = false;
					}
				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

						// Do nothing
						hasLocation = false;
						dialog.cancel();
					}
				});

		final AlertDialog alertDialog = alertDialogBuilder.create();
		dialog = alertDialog;
		alertDialog.show();
		alertDialog.getButton(AlertDialog.BUTTON1).setEnabled(false);

		// creating a listener to see if any changes to edit text in dialog
		TextWatcher textwatcher = new TextWatcher() {

			private void handleText() {

				final Button button = alertDialog
						.getButton(AlertDialog.BUTTON_POSITIVE);
				if (answerBody.getText().length() == 0) { // these checks the
															// edittext to make
															// sure not empty
															// edit text
					button.setEnabled(false);
				} else if (userName.getText().length() == 0) {
					button.setEnabled(false);
				} else {
					button.setEnabled(true);
				}
			}
			

			@Override
			public void afterTextChanged(Editable s) {

				handleText();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// do nothing
			}
		};

		answerBody.addTextChangedListener(textwatcher); // adding listeners to
														// the edittexts
		userName.addTextChangedListener(textwatcher);
		Toast.makeText(this, "Please write your answer", Toast.LENGTH_SHORT)
				.show();
	}

	// DECREPIATE
	// public void setFavorited() {
	// pc.addFavoriteQuestion(pc.getQuestion(question_id));
	// Log.d("click", "Favs: " + upc.getFavoriteQuestions());
	// }

	/**
	 * onClick method for upvoting the questionhttp://stackoverflow.com/questions/2173936/how-to-set-background-color-of-a-view
	 */

	public void increment_upvote() {

		new Thread() {
			public void run() {
				pc.upvoteQuestion(question_id);
			}
		}.start();
		// Give some time to get updated info
		try {
			Thread.currentThread().sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		upvote_score.setText(Integer.toString(pc.getQuestion(question_id)
				.getRating()));
		pc.updateQuestionInBank(question_id);
	}

	/**
	 * Responsible for updating the views answerCounter
	 */

	public void updateAnswerCount() {

		Log.d("click",
				"Count"
						+ String.valueOf(pc.getQuestion(question_id)
								.countAnswers()));
		answerCounter.setText("Answers: "
				+ String.valueOf(pc.getQuestion(question_id).countAnswers()));
	}

	/**
	 * Responsible for updating the views question commentCounter
	 */

	public void updateCommentCount() {

		commentCounter.setText(String.valueOf(pc.getQuestion(question_id)
				.countComments()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		http://stackoverflow.com/questions/2173936/how-to-set-background-color-of-a-view
		getMenuInflater().inflate(R.menu.view_question, menu);
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
		}
		
		return (super.onOptionsItemSelected(item));
	}
	
	/**
	 * onClick method for updating answers
	 * 
	 * @param v
	 *            View where the click happened
	 */

	// This on upvotes an answer
	public void answerUpvote(View v) {

		final Answer answer = (Answer) v.getTag();
		new Thread() {
			public void run() {
				pc.upvoteAnswer(answer.getId(), question_id);
			}
		}.start();
		// Give some time to get updated info
		try {
			Thread.currentThread().sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ala.notifyChange();
		pc.updateQuestionInBank(answer.getParentId());
	}

	// Used for testing
	public AlertDialog getDialog() {

		return this.dialog;
	}
	
	public void pictureChooserDialog() {
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Pictures Option");
		myAlertDialog.setMessage("Select Picture Mode");

		myAlertDialog.setPositiveButton("Gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						takeFromGallery();
					}
				});

		myAlertDialog.setNegativeButton("Camera",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						takeAPhoto();
					}
				});
		myAlertDialog.show();

	}

	public void takeAPhoto() {
		/*
		 * Main Activity is getting pretty bloated so I'm trying to move this
		 * out into the Utils package
		 */
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/MyCameraTest";
		File folder = new File(path);

		if (!folder.exists()) {
			folder.mkdir();
		}

		String imagePathAndFileName = path + File.separator
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"; 															
		// timestamp as part of the filename

		File imageFile = new File(imagePathAndFileName);
		imageFileUri = Uri.fromFile(imageFile);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE); 
		
		// matches the ID to the request code in onActivityResult

	}

	public void takeFromGallery() {

		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), GALLERY_ACTIVITY_REQUEST_CODE);
	}
	
	// This method is run after returning back from camera activity:
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		case CAMERA_ACTIVITY_REQUEST_CODE:
			// TextView tv = (TextView)findViewById(R.id.status); // THE TEXT
			// VIEW THAT YOU SEE ON SCREEN

			if (resultCode == RESULT_OK) {
				hasPicture = true;
				Log.d("click", "Imag efile path: " + imageFileUri.getPath());
				// tv.setText("Photo completed");
				// ImageButton ib = (ImageButton) findViewById(R.id.TakeAPhoto);
				// ib.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
				// // need to use GETPATH

			} else if (resultCode == RESULT_CANCELED) {

			}
		case GALLERY_ACTIVITY_REQUEST_CODE:

			if (resultCode == RESULT_OK) {
				hasPicture = true;
				Uri galleryImageUri = data.getData();
				File imageFile = new File(pictureController.getRealPathFromURI(galleryImageUri));
				imageFileUri = Uri.fromFile(imageFile);
			}

		}
	}	

	class AnswerQuestion extends Thread {
    	private String qID;
    	private Answer answer;
    	
    	public AnswerQuestion(String qID, Answer answer) {
    		this.qID = qID;
    		this.answer = answer;
    		//Log.d("push", this.question.getSubject());
    	}
    	
    	@Override
    	public void run() {
    		pc.answerAQuestionToServer(this.answer, this.qID);
    		try {
    			Thread.sleep(500);
    		} catch(InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    	}
    }
	private Runnable doFinish = new Runnable() {
		public void run() {
			finish();
		}
	};
	
	

}
