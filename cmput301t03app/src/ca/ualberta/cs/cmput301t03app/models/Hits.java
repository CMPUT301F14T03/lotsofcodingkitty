// Taken from ElasticSearch Lab

package ca.ualberta.cs.cmput301t03app.models;

import java.util.ArrayList;


/**
 * This class contains the list of SearchHits 
 *
 * @param <T>
 */
public class Hits<T> {
	private int total;
	private float max_score;
	private ArrayList<SearchHit<T>> hits;
	
	public Hits() {}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public float getMax_score() {
		return max_score;
	}

	public void setMax_score(float max_score) {
		this.max_score = max_score;
	}

	public ArrayList<SearchHit<T>> getHits() {
		return hits;
	}

	public void setHits(ArrayList<SearchHit<T>> hits) {
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "Hits [total=" + total + ", max_score=" + max_score + ", hits="
				+ hits + "]";
	}
}