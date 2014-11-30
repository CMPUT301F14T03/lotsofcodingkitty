// Taken from ElasticSearch Lab

package ca.ualberta.cs.cmput301t03app.datamanagers;

/**
 * Responsible for creating the search query to be sent to the server
 * so it can be read by the server properly
 * 
 *
 */
public class SimpleSearchCommand {

	private String query;
	private String[] fields;
	
	
	/**
	 * Constructs a {@link #SimpleSearchCommand() SimpleSearchCommand}
	 * @param query - the word that will be used to search through the server
	 */
	public SimpleSearchCommand(String query) {
		this(query, null);
	}
	
	/**
	 * Constructs a {@link #SimpleSearchCommand() SimpleSearchCommand}
	 * @param query - the word that will be used to search through the server
	 * @param fields - the fields that will be searched with the query
	 */
	public SimpleSearchCommand(String query, String[] fields) {
		this.query = query;
		this.fields = fields;
	}
	
	
	/**
	 * Returns the String that represents the search query that will be 
	 * sent to the server
	 * @return The query search
	 */
	public String getJsonCommand() {
		StringBuffer command = new StringBuffer(
				"{\"query\" : {\"query_string\" : {\"query\" : \"" + query
						+ "\"");

		if (fields != null) {
			command.append(", \"fields\":  [");

			for (int i = 0; i < fields.length; i++) {
				command.append("\"" + fields[i] + "\", ");
			}
			command.delete(command.length() - 2, command.length());

			command.append("]");
		}

		command.append("}}}");

		return command.toString();
	}
}
