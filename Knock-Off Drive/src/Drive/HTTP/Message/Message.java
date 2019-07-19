package Drive.HTTP.Message;

import java.util.LinkedList;
import java.util.Scanner;

import Drive.HTTP.Version;
import Drive.HTTP.HeaderLine.HeaderLine;
import Drive.HTTP.HeaderLine.HeaderLineType;

/**
 * Message organizes the concept of an HTTP message into a simple object. The object contains every 
 * bit of useful information regarding the message, including HTTP version, header lines, and the data 
 * field.
 * <p>
 * The class contains useful methods that convert to/from a string, which could be sent/received 
 * to/from the client as well as automatic checks for recognized header lines. If a header failed to be 
 * recognized, check out the
 * <p>
 * No actual work on the requests or responses is done in the class. The only responsibility of the class 
 * is to store the data usefully.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.1
 *///TODO
public class Message{
	private Version version;
	
	private Method method;
	private String path;
	
	private Status status;
	private byte[] data;
	
	private boolean isResponse;
	
	private LinkedList<HeaderLine> headerLines;
	
	private int headerLineIterator = 0;
	private String headerLineFailure;
	
	/**
	 * Create new Request message.
	 * 
	 * @param method Request command as a Method enumeration.
	 * @param path Path to work with.
	 * @param version Version of HTTP to work with.
	 * @since 0.1
	 */
	public Message(Method method, String path, Version version){
		this.version = version;
		this.method = method;
		this.path = path;
		this.data = null;	//Most request messages don't use the data field; if needed, use the "setData(String)" method.
		
		headerLines = new LinkedList<>();
		
		isResponse = false;
	}
	
	/**
	 * Create new Response message.
	 * 
	 * @param version Version of HTTP to work with.
	 * @param status Status code and phrase as Status enumeration.
	 * @param data Data field as a String object.
	 * @since 0.1
	 */
	public Message(Version version, Status status, byte[] data){//LinkedList<Integer> data){
		this.version = version;
		this.status = status;
		this.data = data;	//Data is set as an argument here because most response messages use data; if arbitrary, pass an empty list.
		
		headerLines = new LinkedList<>();
		
		isResponse = true;
	}
	
	/**
	 * Gets the version of HTTP.
	 * 
	 * @return Current Version enumeration.
	 * @since 0.1
	 */
	public Version getVersion(){
		return version;
	}
	
	/**
	 * Gets method of request message if applicable.
	 * 
	 * @return Method of request message if applicable, otherwise null.
	 * @since 0.3
	 */
	public Method getMethod(){
		if (!isResponse){
			return method;
		}
		
		return null;
	}
	
	/**
	 * Gets desired path of request message if applicable.
	 * 
	 * @return Desired path of request message as a String object if applicable, otherwise null.
	 * @since 0.1
	 */
	public String getPath(){
		if (!isResponse){
			return path;
		}
		
		return null;
	}
	
	/**
	 * Gets status object if applicable.
	 * 
	 * @return Status code and phrase if applicable, otherwise null.
	 * @since 0.2
	 */
	public Status getStatus(){
		if (isResponse){
			return status;
		}
		
		return null;
	}
	
	/**
	 * Gets the data field as an array of bytes.
	 * 
	 * @return Data field as an array of bytes.
	 * @since 0.8
	 */
	public byte[] getData(){
		return data;
	}
	
	/**
	 * Gets the data field as a String object. Remember that not all data fields are properly represented as a 
	 * String object, as some special characters fail to convert properly.
	 * 
	 * @return Data field as a String object.
	 * @since 0.1
	 */
	public String getDataString(){
		String stringToReturn = new String();
		
		for (int i = 0; i < data.length; ++i){
			stringToReturn += data[i];
		}
		
		return stringToReturn;
	}
	
	/**
	 * Sets the data field. Mostly useful for giving a request message a data field.
	 * 
	 * @param newData New data to change the field to.
	 * @since 0.8
	 */
	public void setData(byte[] newData){//LinkedList<Integer> newData){
		data = newData;
	}
	
	/**
	 * Sets the data field. Mostly useful for giving a request message a data field.
	 * 
	 * @param newData New data, as a String object, to change the field to.
	 * @since 0.3
	 */
	public void setData(String newData){//LinkedList<Integer> newData){
		data = new byte[newData.length()];
		
		for (int i = 0; i < newData.length(); ++i){
			data[i] = (byte)newData.charAt(i);
		}
	}
	
	/**
	 * Adds a header line to the storage of the message from a String object. Removes "\r\n" if present.
	 * 
	 * @param hLine String object that stores a header line.
	 * @return Whether or not the addition was successful.
	 * @since 0.2
	 *///TODO
	public boolean addHeaderLine(String hLine){
		boolean hasNewLine = false;
		
		boolean isLegalFormat = false;
		int splitLocation = -1;
		
		for (int i = 0; i < hLine.length(); ++i){
			if (hLine.charAt(i) == ':' && hLine.charAt(i + 1) == ' '){
				isLegalFormat = true;
				splitLocation = i;
			}
			
			if (hLine.charAt(i) == '\r' && hLine.charAt(i + 1) == '\n'){
				hasNewLine = true;
			}
		}
		
		if (!isLegalFormat){
			return false;
		}
		
		if (hasNewLine){
			String arg1_String = hLine.substring(0, splitLocation);
			String arg2 = hLine.substring(splitLocation + 2, hLine.length() - 2);
			
			HeaderLineType arg1 = HeaderLineType.convertFromString(arg1_String);
			
			if (addHeaderLineFailure(arg1, arg1_String, arg2)){
				return false;
			}
			
			headerLines.add(new HeaderLine(arg1, arg2));
		}
		else{
			String arg1_String = hLine.substring(0, splitLocation);
			String arg2 = hLine.substring(splitLocation + 2);
			
			HeaderLineType arg1 = HeaderLineType.convertFromString(arg1_String);
			
			if (addHeaderLineFailure(arg1, arg1_String, arg2)){
				return false;
			}
			
			headerLines.add(new HeaderLine(arg1, arg2));
		}
		
		//System.out.println("\tAdded header: " + this.getNextHeaderLine());
		
		return true;
	}
	
	/**
	 * Adds a header line to the storage of the message from a HeaderLine object.
	 * 
	 * @param hLine HeaderLine object to add to the message.
	 * @return Whether or not the addition was successful.
	 * @since 0.4
	 */
	public boolean addHeaderLine(HeaderLine hLine){
		if (addHeaderLineFailure(hLine.getType(), "", hLine.getContents())){	//arg1_String is not applicable here.
			return false;
		}
		
		headerLines.add(hLine);
		
		return true;
	}
	
	/**
	 * Adds a header line to the storage of the message from a HeaderLineType enumeration and a String 
	 * object.
	 * 
	 * @param type HeaderLineType enumeration to be represented.
	 * @param contents String object to be represented in the HeaderLine object in the contents data field.
	 * @return Whether or not the addition was successful.
	 * @since 0.4
	 */
	public boolean addHeaderLine(HeaderLineType type, String contents){
		if (addHeaderLineFailure(type, "", contents)){	//arg1_String is not applicable here.
			return false;
		}
		
		headerLines.add(new HeaderLine(type, contents));
		
		return true;
	}
	
	/**
	 * Helper method that checks whether or not the added header line is legal. Will print a description of 
	 * the error to the headerLineFailure String object if it fails.
	 * <p>
	 * See "if(newLine){" and down code in addHeaderLine(String) for more information on why the method 
	 * is what it is.
	 * 
	 * @param arg1 The HeadLineType in question.
	 * @param arg1_String A string representation of an attempted HeaderLineType.
	 * @param arg2 String object representing the expected contents for the header line.
	 * @return Whether or not the attempted HeadLineType is illegal (true if illegal).
	 * @since 0.4
	 */
	private boolean addHeaderLineFailure(HeaderLineType arg1, String arg1_String, String arg2){
		while (hasNextHeaderLine()){
			if (getNextHeaderLine().getType() == arg1){	//Trying to add duplicate.
				headerLineFailure = ("Header line with type \"" + arg1 + "\" already exists.");
				
				resetHeaderLineIterator();
				return true;
			}
		}
		
		resetHeaderLineIterator();
		
		if (arg1 == null || !arg1.isLegalState(isResponse) || !arg1.isVersionAccepted(version)){
			if (arg1 == null){
				headerLineFailure = ("\"" + arg1_String + "\" is not a recognized HeadLineType.");
			}
			else if (!arg1.isLegalState(isResponse) && !arg1.isVersionAccepted(version)){
				headerLineFailure = ("\"" + arg1.toString() + "\" is only for ");
				if (isResponse){
					headerLineFailure += "request ";
				}
				else {
					headerLineFailure += "response ";
				}
				
				headerLineFailure += ("messages. Also it is only compatible with " + arg1.getAcceptedVersion().toString() + ".");
			}
			else if (!arg1.isLegalState(isResponse)){
				headerLineFailure = ("\"" + arg1.toString() + "\" is only for ");
				if (isResponse){
					headerLineFailure += "request ";
				}
				else {
					headerLineFailure += "response ";
				}
				
				headerLineFailure += "messages.";
			}
			else if (!arg1.isVersionAccepted(version)){
				headerLineFailure = ("\"" + arg1.toString() + "\" is only compatible with " + arg1.getAcceptedVersion().toString() + ".");
			}
			
			return true;
		}//If not a recognized head-line type, is an illegal state, or uses an incorrect version, then fail to add.
		
		//Checks for legal answers.
		if (!HeaderLine.isLegalAnswer(arg1, arg2)){
			headerLineFailure = ("\"" + arg2 + "\" is not legal content to \"" + arg1 + "\".");
			
			return true;
		}
		
		return false;	//No error was found.
	}
	
	/**
	 * Gets a description of why the last addHeaderLine(...) [any of them] attempt failed.
	 * 
	 * @return headerLineFailure String object containing a description of the failure.
	 * @since 0.4
	 */
	public String getHeaderLineFailure(){
		return headerLineFailure;
	}
	
	/**
	 * Tells whether or not there is another header line in the list.
	 * 
	 * @return Whether or not there is another header line in the list.
	 * @since 0.2
	 */
	public boolean hasNextHeaderLine() {
		return (headerLineIterator < headerLines.size());
	}
	
	/**
	 * Gets the next header line, as a HeaderLine object, in the list and increments the iterator.
	 * 
	 * @return Copy of the next HeaderLine object in the list.
	 * @since 0.2
	 */
	public HeaderLine getNextHeaderLine(){
		HeaderLine headerLineToReturn = new HeaderLine(headerLines.get(headerLineIterator).getType(), headerLines.get(headerLineIterator).getContents());
		
		headerLineIterator++;
		return headerLineToReturn;
	}
	
	/**
	 * Resets the iterator of the header lines back to 0 so each line can be re-retrieved.
	 * 
	 * @since 0.2
	 */
	public void resetHeaderLineIterator(){
		headerLineIterator = 0;
	}
	
	/**
	 * Tells whether or not the current message is a response message or not.
	 * 
	 * @return Whether or not the current message is a response message or not.
	 * @since 0.1
	 */
	public boolean isResponse(){
		return isResponse;
	}
	
	/**
	 * Converts a given string to a string that is legal for the convertFromString(String) method by 
	 * changing new lines to be appropriate (all new lines become "\r\n").
	 * 
	 * @param messageString String to convert.
	 * @return Converted string.
	 * @since 0.2
	 */
	public static String convertToLegalString(String messageString){
		StringBuilder build = new StringBuilder(messageString);
		
		for (int i = 0; i < build.length(); ++i){
			if(build.charAt(0) == '\n'){
				build.insert(0, '\r');
			}
			
			if (build.charAt(i) == '\r' && build.charAt(i + 1) != '\n'){
				build.insert(i + 1, '\n');
			}
			
			if (build.charAt(i) == '\n' && build.charAt(i - 1) != '\r'){
				build.insert(i, '\r');
			}
		}
		
		return build.toString();
	}
	
	/**
	 * Converts a String object of an entire HTTP message (request or response) to a Message object.
	 * 
	 * @param messageString String that contains the entire HTTP message.
	 * @return Message object properly converted from the String object, or null if an error was detected.
	 * @since 0.2
	 */
	public static Message convertFromString(String messageString){
		if (messageString.isEmpty()){
			return null;
		}
		
		if (messageString.charAt(0) == '\n') return null;	//A little fail-safe in case messageString starts with '\n'.
		for (int i = 0; i < messageString.length(); ++i){	//Check legality of the string.
			if ((messageString.charAt(i) == '\r' && messageString.charAt(i + 1) != '\n') || ((messageString.charAt(i) == '\n' && messageString.charAt(i - 1) != '\r'))){
				return null;
			}
		}
		
		Scanner scanString = new Scanner(messageString);
		
		Version version = null;
		
		Method method = null;
		String path = new String();
		
		Status status = null;
		
		byte[] data = null;	//TODO
		int dataStartIndex = 0;	//Marks the beginning of the data section of the message.
		
		boolean isRespone = false;
		
		Message messageToReturn = null;
		
		String currentLine = scanString.nextLine();	//First line.
		dataStartIndex += (currentLine.length() + 2);	//The "+ 2" is safe because every new-line has to be "\r\n".
		
		if (currentLine.startsWith(Version._1_0.toString())){	//Response.
			version = Version._1_0;
			
			boolean statusFound = false;
			for (Status stat : Status.values()){
				if (currentLine.substring(currentLine.indexOf(" ") + 1, currentLine.length()).equals(stat.toString())){
					status = stat;
					statusFound = true;
					break;
				}
			}
			
			if (!statusFound){
				scanString.close();
				return null;
			}
			
			isRespone = true;
		}
		else if (currentLine.startsWith(Version._1_1.toString())){	//Response.
			version = Version._1_1;
			
			boolean statusFound = false;
			for (Status stat : Status.values()){
				if (currentLine.substring(currentLine.indexOf(" ") + 1, currentLine.length()).equals(stat.toString())){
					status = stat;
					statusFound = true;
					break;
				}
			}
			
			if (!statusFound){
				scanString.close();
				return null;
			}
			
			isRespone = true;
		}
		else if (currentLine.endsWith(Version._1_0.toString())){	//Request.
			version = Version._1_0;
			
			method = Method.convertFromString(currentLine.substring(0, currentLine.indexOf(" ")));
			
			if (method == null){	//Checks if a recognized method is used.
				scanString.close();
				
				return null;
			}
			else if (!method.isVersionAccepted(version)){	//Check whether a legal method for the version is used.
				scanString.close();
				
				return null;
			}
			
			//path = 					this line starts it at the '/'				find next ' '	after first '/'
			path = currentLine.substring(currentLine.indexOf("/"), currentLine.indexOf(" ", currentLine.indexOf("/")));
			
			isRespone = false;	//Already false, but just in case.
		}
		else if (currentLine.endsWith(Version._1_1.toString())){	//Request.
			version = Version._1_1;
			
			method = Method.convertFromString(currentLine.substring(0, currentLine.indexOf(" ")));
			
			if (method == null){	//Checks if a recognized method is used.
				scanString.close();
				
				return null;
			}
			else if (!method.isVersionAccepted(version)){	//Check whether a legal method for the version is used.
				scanString.close();
				
				return null;
			}
			
			//path = 					this line starts it at the '/'				find next ' '	after first '/'
			path = currentLine.substring(currentLine.indexOf("/"), currentLine.indexOf(" ", currentLine.indexOf("/")));
			
			isRespone = false;	//Already false, but just in case.
		}
		else{	//Not a recognized message.
			scanString.close();
			
			return null;
		}
		
		if (isRespone){
			messageToReturn = new Message(version, status, new byte[0]);//new LinkedList<>());	//Data field will be set later.
		}else{
			messageToReturn = new Message(method, path, version);
		}
		
		while (scanString.hasNextLine()){	//Adds header lines.
			currentLine = scanString.nextLine();
			dataStartIndex += (currentLine.length() + 2);
			
			if (currentLine.isEmpty()){	//Time to start the data string.
				break;
			}
			
			if (!messageToReturn.addHeaderLine(currentLine)){	//Returns null if illegal header line was detected.
				scanString.close();
				
				return null;
			}
		}
		
		//Creates data field.
		data = new byte[messageString.length() - dataStartIndex];
		
		for (int i = dataStartIndex; i < messageString.length(); ++i){
			data[i - dataStartIndex] = (byte)messageString.charAt(i);
		}
			
		messageToReturn.setData(data);	//Sets the data field.
		
		scanString.close();
		
		return messageToReturn;
	}
	
	/**
	 * Copies a given Message and returns the reference to the copy.
	 * 
	 * @param original Original Message desired to be copied.
	 * @return Copied Message object.
	 * @since 0.5
	 */
	public static Message copyOf(Message original){
		if (original == null){
			return null;
		}
		
		Message messageToReturn = null;
		
		if (original.isResponse()){
			messageToReturn = new Message(original.getVersion(), original.getStatus(), original.getData());
			
			while (original.hasNextHeaderLine()){
				messageToReturn.addHeaderLine(original.getNextHeaderLine());
			}
			
			original.resetHeaderLineIterator();
		}
		else {
			messageToReturn = new Message(original.getMethod(), original.getPath(), original.getVersion());
			
			while (original.hasNextHeaderLine()){
				messageToReturn.addHeaderLine(original.getNextHeaderLine());
			}
			
			messageToReturn.setData(original.getData());
			
			original.resetHeaderLineIterator();
		}
		
		return messageToReturn;
	}
	
	/**
	 * Converts the entire message into an appropriate byte array representation. Safest way to send bytes 
	 * through a network without corruption.
	 * 
	 * @return Byte array representing the entire message.
	 * @since 1.0
	 */
	public byte[] toByteArray(){
		byte[] byteArrayToReturn = new byte[beginningString().length() + data.length];	//Even if the String sucks, it is the correct length. String(byte[]) created head-aches :(
		
		int positionOnArray = 0;
		
		if (isResponse){
			//Version:
			String versionString = version.toString();
			for (int i = positionOnArray; i < versionString.length() + positionOnArray; ++i){
				byteArrayToReturn[i] = (byte)versionString.charAt(i - positionOnArray);
			}
			positionOnArray += versionString.length();	//Updates positionOnArray;
			
			
			//'Space':
			byteArrayToReturn[positionOnArray] = (byte)' ';	//positionOnArray is already correct.
			positionOnArray++;
			
			
			//Status:
			String statusString = status.toString();
			for (int i = positionOnArray; i < statusString.length() + positionOnArray; ++i){
				byteArrayToReturn[i] = (byte)statusString.charAt(i - positionOnArray);
			}
			positionOnArray += statusString.length();	//Updates positionOnArray;
			
			
			//Done with start-line, onto header lines after this new line:
			byteArrayToReturn[positionOnArray] = (byte)'\r';
			positionOnArray++;
			byteArrayToReturn[positionOnArray] = (byte)'\n';
			positionOnArray++;
			
			
			//Header Lines:
			for (HeaderLine hLine : headerLines){
				String hLineString = hLine.toString();
				
				//As seen beforehand.
				for (int i = positionOnArray; i < hLineString.length() + positionOnArray; ++i){
					byteArrayToReturn[i] = (byte)hLineString.charAt(i - positionOnArray);
				}
				positionOnArray += hLineString.length();	//Updates positionOnArray;
				
				//Onto next header line.
				byteArrayToReturn[positionOnArray] = (byte)'\r';
				positionOnArray++;
				byteArrayToReturn[positionOnArray] = (byte)'\n';
				positionOnArray++;
			}
			
			
			//Standard empty line between header lines and data field:
			byteArrayToReturn[positionOnArray] = (byte)'\r';
			positionOnArray++;
			byteArrayToReturn[positionOnArray] = (byte)'\n';
			positionOnArray++;
			
			
			//Data:
			for (int i = positionOnArray; i < data.length + positionOnArray; ++i){
				byteArrayToReturn[i] = data[i - positionOnArray];
			}
			//No need to update positionOnArray, done converting.
		}
		
		return byteArrayToReturn;
	}
	
	
	/**
	 * Char-sets are annoying, this helps fix it.
	 * 
	 * @return Beginning of the toString().
	 * @since 1.0
	 */
	private String beginningString(){
		String stringToReturn = new String();
		
		if (isResponse){
			stringToReturn = version.toString();
			stringToReturn += " ";
			stringToReturn += status.toString();
			stringToReturn += "\r\n";
		}
		else{
			stringToReturn = method.toString();
			stringToReturn += " ";
			stringToReturn += path;
			stringToReturn += " ";
			stringToReturn += version.toString();
			stringToReturn += "\r\n";
		}
		
		for (HeaderLine hLine : headerLines){
			stringToReturn += hLine;
			stringToReturn += "\r\n";
		}
		
		stringToReturn += "\r\n";	//It's standard for an extra "\r\n" to be present here.
		
		return stringToReturn;
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn = beginningString();	//Look up by 1 method.
		
		
		stringToReturn += new String(data);	//There be annoying char-set afoot.
		
		
		return stringToReturn;
	}
	
	/**
	 * An unorganized terrible mess of a test that has evolved and changed over time. Now, it is completely 
	 * obsolete and should never be run (it may not even work).
	 * 
	 * @param args [Not Used]
	 * @since 0.1
	 */
	public static void main(String[] args){
		Message test = new Message(Version._1_1, Status.BAD_REQ, new byte[0]);
		
		String ultimateTestHeader1 = "Connection: this is a test header";
		String ultimateTestHeader2 = "Date: this is another test\r\n";
		
		test.addHeaderLine(ultimateTestHeader1);
		test.addHeaderLine(ultimateTestHeader2);
		
		System.out.print(test);
		
		System.out.println("Start");
		while (test.hasNextHeaderLine()){
			System.out.println("\t" + test.getNextHeaderLine());
		}
		System.out.println("Finish");
		
		Message test1 = Message.convertFromString("HTTP/1.0 200 OK\r\n");
		
		if (test1 == null){
			System.out.println("\n\nHEY");
		}
		
		if (Status.OK.toString().equals("200 OK")){
			System.out.println("Hmm");
		}
		
		String scan = "this is a test string\r\nthis is the next line\r\n\r\ntest\r\n";
		Scanner in = new Scanner(scan);
		
		System.out.println(in.nextLine());
		System.out.println(in.nextLine());
		
		if (in.nextLine().isEmpty()){
			System.out.println("it is");
		}
		
		in.close();
		
		System.out.println("\n\n");
		
		
		/*
		String message = "HTTP/1.1 200 OK\r\n";
		message += "Date: Sun, 26 Sep 2010 20:09:20 GMT\r\n";
		message += "Server: Apache/2.0.52 (CentOS)\r\n";
		message += "Last-Modified: Tue, 30 Oct 2007 17:00:02";
		message += "GMT\r\n";
		message += "ETag: \"17dc6-a5c-bf716880\"\r\n";
		message += "Accept-Ranges: bytes\r\n";
		message += "Content-Length: 2652\r\n";
		message += "Keep-Alive: timeout=10, max=100\r\n";
		message += "Connection: Keep-Alive\r\n";
		message += "Content-Type: text/html; charset=ISO-8859-1\r\n";
		message += "\r\n";
		message += "<!DOCTYPE html>\r\n<html>\r\n\r\n</html>\r\n";
		
		if (message.equals(Message.convertFromString(message).toString())){	//It works!!
			System.out.println("fucking nice");
		}
		
		//System.out.println(HTTP_Message.convertFromString(message).toString());
		
		String message1 = "DELETE /index.html HTTP/1.1\r\n";
		message1 += "Host: www-net.cs.umass.edu\r\n";
		message1 += "User-Agent: Firefox/3.6.10\r\n";
		message1 += "Accept: text/html,application/xhtml+xml\r\n";
		message1 += "Accept-Language: en-us,en;q=0.5\r\n";
		message1 += "Accept-Encoding: gzip,deflate\r\n";
		message1 += "Accept-Charset: ISO-8859-1,utf-8;q=0.7\r\n";
		//message1 += "Keep-Alive: 115\r\n";
		message1 += "Connection: keep-alive\r\n";
		message1 += "\r\n";
		message1 += "<!DOCTYPE html>\r\n<html>\r\n\r\n</html>\r\n";
		
		if (message1.equals(Message.convertFromString(message1).toString())){	//It works!!
			System.out.println("fucking nice1");
		}
		
		if (message1.equals(Message.copyOf(Message.convertFromString(message1)).toString())){	//It works!!
			System.out.println("fucking nice2");
		}*/
		
		//System.out.println(HTTP_Message.convertFromString(message1).toString());
		
		System.out.println(Message.convertToLegalString("\nthis is a test string\r" + "End"));
		
		Message pathBB = Message.convertFromString("GET /direct/index.html HTTP/1.1\r\n");
		
		System.out.println("Path: " + pathBB.getPath());
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Message bytesUhOh = new Message(Version._1_1, Status.OK, "Data B,\r\nit's me".getBytes());
		
		bytesUhOh.addHeaderLine(HeaderLineType.CONTENT_TYPE, "text/plain");
		
		String finishLineYisss = "\r\n---Byte---\r\n";
		finishLineYisss += new String(bytesUhOh.toByteArray());
		
		System.out.println(finishLineYisss);
		
		
		finishLineYisss = "\r\n---String---\r\n";
		finishLineYisss += bytesUhOh.toString();
		
		System.out.println(finishLineYisss);
	}
}


















