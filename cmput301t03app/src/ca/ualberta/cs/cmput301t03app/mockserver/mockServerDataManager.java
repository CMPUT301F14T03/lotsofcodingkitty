/**
 * THIS IS FOR TESTING PURPOSES ONLY
 * 
 * Snippits of code may be used later on when we actually work on the server/elastic search stuff, but most of the code will have to change in the actual
 * ServerDataManager class.  Since the mock server is simulated as a class and the "server data" is a local saved file, these methods will use file i/o
 * methods to handle pushing (and pulling if I have time to get around to it after we integrate).
 * 
 * Direct any questions to Eric
 */
package ca.ualberta.cs.cmput301t03app.mockserver;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.models.Question;

import android.content.Context;

public class mockServerDataManager {
	
	private Context context;
	private static ArrayList<Question> mockServerList = new ArrayList<Question>(); 
	
	public mockServerDataManager(Context context) {
		this.context = context;
	}
	
	public void mockPushQuestionToServer(Question q) {
		 mockServerList = mockServer.getMainList();
		 mockServerList.add(q);
		 mockServer.acceptPush(mockServerList);
	}
	
	public void mockUpdateList(ArrayList<Question> qList) {
		mockServer.acceptPush(qList);
	}

}
