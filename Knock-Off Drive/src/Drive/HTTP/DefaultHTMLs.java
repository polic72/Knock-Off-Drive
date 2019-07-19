package Drive.HTTP;

/**
 * Contains multiple default HTML documents to send to a client in the case of an error, or issue internally in 
 * the server. All of the files are to be used in the HTTP class and can be overwritten for other messages.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.6
 */
public enum DefaultHTMLs {
	NOT_FOUND("<!DOCTYPE html>\r\n<html>\r\n\t<head>\r\n\t\t<title>Page Could Not Be Found</title>\r\n"
			+	"\t</head>\r\n\r\n\t<body>\r\n\t\t<h1>Page Not Found</h1>\r\n\r\n"
			+	"\t\t<p>The requested page could not be found. Please try another address.</p>\r\n"
			+	"\t</body>\r\n\r\n</html>\r\n"),
	
	PRECON_FAIL("<!DOCTYPE html>\r\n<html>\r\n\t<head>\r\n\t\t<title>Condition Failed</title>\r\n"
			+	"\t</head>\r\n\r\n\t<body>\r\n\t\t<h1>Precondition Failed</h1>\r\n\r\n"
			+	"\t\t<p>The requested precondition has failed.</p>\r\n\t</body>\r\n\r\n</html>\r\n");
	
	
	private String contents;
	
	/**
	 * Constructs a DefautHTMLs enumeration.
	 * 
	 * @param contents String object representing the entire HTML document.
	 * @since 0.8
	 */
	private DefaultHTMLs(String contents){
		this.contents = contents;
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn += contents;
		
		return stringToReturn;
	}
}
