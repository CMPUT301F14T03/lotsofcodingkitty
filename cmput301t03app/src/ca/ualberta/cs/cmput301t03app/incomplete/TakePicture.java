package ca.ualberta.cs.cmput301t03app.incomplete;
//package ca.ualberta.cs.cmput301t03app.utils;
//
//import java.io.File;
//
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//
//public class TakePicture extends Activity {
//
//	Uri imageFileUri;
//	
//	//This method creates an intent. 
//    //It is told that we need camera action, and the results should be saved in a location that is sent to the intent.
//	public void takeAPhoto() {
//		
//    	String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCameraTest";
//		File folder = new File(path);
//		
//		if (!folder.exists()){
//			folder.mkdir();
//		}
//			
//		String imagePathAndFileName = path + File.separator + 
//				String.valueOf(System.currentTimeMillis()) + ".jpg" ; //makes the timestamp as part of the filename
//		
//		File imageFile = new File(imagePathAndFileName);
//		imageFileUri = Uri.fromFile(imageFile); 
//		
//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
//		Log.d("click", "*** Taking pic - before intent start");
//		startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE); // sets the ID for when the CAmera app sends it back here. 
//		// matches the ID to the request code in onActivityResult
//		
//    }
//    
//    
//    private final static int CAMERA_ACTIVITY_REQUEST_CODE = 12345;
//    
//    
//    //This method is run after returning back from camera activity:
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		
//    	Log.d("click", "*** Taking pic - in intent");
//    	
//    	if (requestCode == CAMERA_ACTIVITY_REQUEST_CODE){
//		//	TextView tv = (TextView)findViewById(R.id.status); // THE TEXT VIEW THAT YOU SEE ON SCREEN
//			
//			if (resultCode == RESULT_OK){
//
//			//	tv.setText("Photo completed");
//			//	ImageButton ib = (ImageButton) findViewById(R.id.displayPicture); // use this to fill the thumbnail
//			//	ib.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath())); // need to use GETPATH
//			}
//			else
//				if (resultCode == RESULT_CANCELED){
//				//	tv.setText("Photo was canceled!");
//				}
////				else
////					//tv.setText("What happened?!!");
//		}
//    }
//	
//}
