package Drive.HTTP.Message;

/**
 * Status is a utility enumeration to help identify different HTTP response statuses.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.2
 */
public enum Status {
	OK(200, "OK"),
	MOVED_PERM(301, "Moved Permanently"),
	BAD_REQ(400, "Bad Request"),
	NOT_FOUND(404, "Not Found"),
	PRECON_FAIL(412, "Precondition Failure"),
	SERV_UNAVAIL(503, "Service Unavailable"),
	HTTP_NOT_SUPP(505, "HTTP Version Not Supported");
	
	
	private int code;
	private String phrase;
	
	/**
	 * Constructs a Status enumeration for an HTTP response message.
	 * 
	 * @param code Number field of the status sent in an HTTP response.
	 * @param phrase Phrase associated with the respective number code in an HTTP response message.
	 * 0.2
	 */
	private Status(int code, String phrase){
		this.code = code;
		this.phrase = phrase;
	}
	
	/**
	 * Gets the number code as an integer.
	 * 
	 * @return The number code as an integer.
	 * @since 0.2
	 */
	public int getCode(){
		return code;
	}
	
	/**
	 * Gets the phrase as a String object.
	 * 
	 * @return The phrase as a String object.
	 * @since 0.2
	 */
	public String getPhrase(){
		return phrase;
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn += String.valueOf(code);
		stringToReturn += " ";
		stringToReturn += phrase;
		
		return stringToReturn;
	}
}





















