package Drive.HTTP;

import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import Drive.HTTP.HeaderLine.HeaderLine;
import Drive.HTTP.HeaderLine.HeaderLineType;
import Drive.HTTP.Message.Message;
import Drive.HTTP.Message.Method;
import Drive.HTTP.Message.Status;

import java.io.*;

/**
 * HTTP handles the entirety of an HTTP server, allowing for any client (ex. Chrome browser) to access the 
 * server's contents. The class handles any and all socket programming, allowing any user to quickly set up 
 * a server with no prior knowledge of network programming.
 * <p>
 * Any and all servers running this class use port 80, as HTTP would do, so make sure no other application 
 * that also uses port 80 is run in conjunction with this class, you may run into some funky errors.
 * <p>
 * The standard order of methods goes:</p>
 * <ol>
 * 		<li>constructor</li>
 * 		<li>makeConnection()</li>
 * 		<li>readMessage()</li>
 * 		<li>interpretMessage() (optional)</li>
 * 		<li>sendMessage()</li>
 * 		<li>breakConnection() (if necessary)</li>
 * 		<li>[loop from 2]</li>
 * 		<li>endSession()</li>
 * </ol>
 * <p>
 * REMINDER TO ANY AND ALL USERS: this class does not handle encrypted messages in any way, making each 
 * connection susceptible to man-in-the-middle and other such attacks. Therefore, it is highly advised 
 * NOT to request or send any sensitive data (such as passwords or Credit Card info) over a server made with 
 * this class. You have been warned...
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.0
 *///TODO finish this
public class HTTP {
	private static String serverName = "GES - 0.7";	//TODO update this every time the server is
	
	private ServerSocket server;
	private Socket sock = null;
	
	private boolean isEnded = false;
	private boolean isBroken = true;
	
	private InputStream inStrm = null;
	private InputStreamReader inStrmRd = null;
	private BufferedReader buffInStrmRd = null;
	
	private OutputStream outStrm = null;
	
	private Message messageRead = null;
	private Message messageSend = null;
	
	private boolean DebugMode = false;
	
	private final String workingDirectory;
	
	private static final DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");	
														//e.x. Sun, 12 Nov 2017 22:17:00 GMT
	private File Not_Found_HTML;
	private boolean usingDefaultNot_Found_HTML;
	
	/**
	 * Creates a new HTTP server via ServerSocket object on port 80 (HTTP port). Also sets some necessary 
	 * constants to allow the server to run. (Do not include a "/" or "\" at the end of the workingDirectory 
	 * String object.)
	 * <p>
	 * NOTE: All files in the working directory and sub-directories of the working directory should not be 
	 * larger than 2Gb individually. The program is not able to handle such large files, as the entire file is 
	 * loaded into memory, and larger files are too dangerous to use so many resources.
	 * <p>
	 * Sets the defaults for possible client and server errors.
	 * 
	 * @param workingDirectory String object representing the working directory of the server.
	 * @throws IOException If port 80 is being used by another application or the permission is denied.
	 * @since 0.0
	 */
	public HTTP(String workingDirectory) throws IOException{
		server = new ServerSocket(80);
		
		this.workingDirectory = workingDirectory;
		
		Not_Found_HTML = null;
		usingDefaultNot_Found_HTML = true;
	}
	
	/**
	 * Sets the Not_Found_HTML field to the File object given; server will now longer use default.
	 * 
	 * @param file File object containing the HTML document used for a "404 Not Found" page.
	 * @since 0.6
	 */
	public void setNot_Found_HTML(File file){
		Not_Found_HTML = file;
		usingDefaultNot_Found_HTML = false;
	}
	
	/**
	 * Makes a connection to the current client, if there isn't one, sets up variables to do so.
	 * 
	 * @throws Exception In the case that the session has ended.
	 * @since 0.0
	 */
	public void makeConnection() throws Exception{
		checkEnded();
		
		if (isBroken){
			sock = server.accept();
			
			inStrm = sock.getInputStream();
			outStrm = sock.getOutputStream();
			
			inStrmRd = new InputStreamReader(inStrm);
			buffInStrmRd = new BufferedReader(inStrmRd);
			
			isBroken = false;
		}
	}
	
	/**
	 * Breaks current connection, if there is one.
	 * 
	 * @throws Exception In the case that the session has ended.
	 * @since 0.0
	 */
	public void breakConnection() throws Exception{
		checkEnded();
		
		if (!isBroken){
			sock.close();
			
			inStrm.close();
			outStrm.close();
			
			inStrmRd.close();
			buffInStrmRd.close();
			
			isBroken = true;
		}
	}
	
	/**
	 * Tells whether or not a connection exists.
	 * 
	 * @return Whether or not a connection exists.
	 * @throws Exception In the case that the session has ended.
	 * @since 0.0
	 */
	public boolean hasConnection() throws Exception{
		checkEnded();
		
		return (!isBroken);
	}
	
	/**
	 * Ends the session completely, forcing the user to make a new object if another connection is to be 
	 * made.
	 * 
	 * @throws Exception In the case that the session has ended.
	 * @since 0.0
	 */
	public void endSession() throws Exception{
		checkEnded();
		
		breakConnection();
		
		server.close();
		
		isEnded = true;
	}
	
	/**
	 * Tells whether or not the session has ended. Is one of few methods that doesn't throw an exception if 
	 * the session has already ended when called.
	 * 
	 * @return Whether or not the session has ended.
	 * @since 0.5
	 */
	public boolean hasEnded(){
		return isEnded;
	}
	
	/**
	 * Encodes the expected request message into the Message messageRead object.
	 * 
	 * @return Whether or not the message was properly encoded
	 * @throws Exception In the case that the session has ended or read() fails.
	 * @since 0.0
	 *///TODO finish
	public boolean readMessage() throws Exception{
		checkEnded();
		
		if (!isBroken){
			
			String messageString = new String();
			String currentLine = new String();
			do{
				currentLine = buffInStrmRd.readLine();//System.out.println(currentLine);
				
				if (currentLine == null){
					breakConnection();
					break;
				}
				
				messageString += (currentLine + "\r\n");
			}while(!currentLine.isEmpty());
			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//			do {
//				messageChar = inStrm.read();System.out.println("Char read");
//				messageString += (char)messageChar;
//			} while (messageChar != -1);
			
			if (DebugMode) System.out.println(messageString + "---End messageString---\n");
			
			messageRead = Message.convertFromString(Message.convertToLegalString(messageString));
			
			if (DebugMode && messageRead == null) System.out.println("messageRead is null!!");
			
			if (messageRead == null){
				return false;
			}
			
			//Get data field of request message.
			String tempData = new String();
			
			if (messageRead.getMethod() == Method.POST || messageRead.getMethod() == Method.PUT){
				while (messageRead.hasNextHeaderLine()){
					HeaderLine tempHeader = messageRead.getNextHeaderLine();
					
					if (tempHeader.getType() == HeaderLineType.CONTENT_LENGTH){
						for (int i = 0; i < Integer.parseInt(tempHeader.getContents()); ++i){
							tempData += (char)inStrm.read();
						}
						
						break;
					}
				}
			}
			
			messageRead.setData(tempData);	//Will be empty if method wasn't POST or PUT.
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Gets a copy of the last read message as a Message object.
	 * 
	 * @return A copy of the last read message as a Message object.
	 * @throws Exception In the case that the session has ended.
	 * @since 0.5
	 */
	public Message getMessageRead() throws Exception{
		checkEnded();
		
		return Message.copyOf(messageRead);
	}
	
	//TODO this @since 0.2 @Excetion In the case that the session has ended.
	/**
	 * Interprets the message received from the client and interprets it, making a message to send back to 
	 * the client.
	 * <p>
	 * Automatically handles the path of GET messages, and the production of 404 errors.
	 * 
	 * @return Whether or not the request message was successfully interpreted.
	 * @throws Exception In the case that the session has ended.
	 *///TODO needs so much work, like damn
	public boolean interpretMessage() throws Exception{
		checkEnded();
		
		if (messageRead == null){
			return false;
		}
		
		if (messageRead.getMethod() == Method.TRACE){
			//Create response message with a data field containing the request message.
			
			messageSend = new Message(Version._1_1, Status.OK, messageRead.toByteArray());
			messageSend.addHeaderLine(HeaderLineType.DATE, getTimeGMT());
			messageSend.addHeaderLine(HeaderLineType.SERVER, serverName);
			messageSend.addHeaderLine(HeaderLineType.CONNECTION, "close");
			messageSend.addHeaderLine(HeaderLineType.CONTENT_TYPE, "message/http");
			messageSend.addHeaderLine(HeaderLineType.CONTENT_LENGTH, intToString(messageRead.toString().length()));
		}
		else if (messageRead.getMethod() == Method.GET){
			FileInputStream reader = null;
			File file = null;
			
			if (messageRead.getPath().equals("/")){	//Attempted root access attempts to find the index.html file.
				try {
					file = new File(workingDirectory + "/index.html");
					reader = new FileInputStream(file);
					
				} catch (FileNotFoundException e) {	//File could not be found.
					makeNotFound();
					
					return false;
				}
			}
			else{
				try {
					file = new File(workingDirectory + messageRead.getPath());
					reader = new FileInputStream(file);
					
				} catch (FileNotFoundException e) {	//File could not be found.
					makeNotFound();
					
					return false;
				}
			}
			
			byte[] tempFileData = new byte[(int)file.length()];	//"Small" files only (roughly 2Gb max).
			
			reader.read(tempFileData);
			reader.close();
			
			messageSend = new Message(messageRead.getVersion(), Status.OK, tempFileData);
			messageSend.addHeaderLine(HeaderLineType.DATE, getTimeGMT());
			messageSend.addHeaderLine(HeaderLineType.SERVER, serverName);
			messageSend.addHeaderLine(HeaderLineType.LAST_MODIFIED, getTimeLastModified(file));
			messageSend.addHeaderLine(HeaderLineType.CONTENT_LENGTH, intToString(tempFileData.length));
			messageSend.addHeaderLine(HeaderLineType.CONTENT_TYPE, getContentType(file));
			
			messageSend.addHeaderLine(HeaderLineType.CONNECTION, "keep-alive");
			messageSend.addHeaderLine(HeaderLineType.KEEP_ALIVE, "timeout=10, max=100");
			
			
			return true;
		}
		else if (messageRead.getMethod() == Method.POST){	//TODO
			//
		}
		
		return true;
	}
	
	/**
	 * Delegates the creation of the classic "404 Not Found" message to send back to the client. Only should 
	 * be seen in the interpretMessage() method.
	 * 
	 * @param reader Scanner object to read through possibly non-default "404 Not Found" HTML file.
	 * @throws FileNotFoundException If the Not_Found_HTML File object can't be found.
	 * @since 0.6
	 */
	private void makeNotFound() throws FileNotFoundException{
		if (usingDefaultNot_Found_HTML){
			messageSend = new Message(messageRead.getVersion(), Status.NOT_FOUND, DefaultHTMLs.NOT_FOUND.toString().getBytes());
		}
		else{
			String tempData = new String();
			Scanner reader = new Scanner(Not_Found_HTML);
			
			while (reader.hasNextLine()){
				tempData += reader.nextLine();
				tempData += "\r\n";
			}
			
			reader.close();
			
			messageSend = new Message(messageRead.getVersion(), Status.NOT_FOUND, tempData.getBytes());
		}
		messageSend.addHeaderLine("Date: " + HTTP.getTimeGMT());
		messageSend.addHeaderLine("Server: " + serverName);
		if (!usingDefaultNot_Found_HTML) messageSend.addHeaderLine(HeaderLineType.LAST_MODIFIED, getTimeLastModified(Not_Found_HTML));
		if (usingDefaultNot_Found_HTML){
			messageSend.addHeaderLine(HeaderLineType.CONTENT_LENGTH, intToString(DefaultHTMLs.NOT_FOUND.toString().length()));
		}
		else{
			messageSend.addHeaderLine(HeaderLineType.CONTENT_LENGTH, intToString(messageSend.getData().length));
		}
	}
	
	/**
	 * Gets a copy of the next message to send as a Message object.
	 * 
	 * @return A copy of the next message to send as a Message object.
	 * @throws Exception In the case that the session has ended.
	 * @since 0.5
	 */
	public Message getMessageSend() throws Exception{
		checkEnded();
		
		return Message.copyOf(messageSend);
	}
	
	/**
	 * Sends the generated Message object from interpretMessage() as a response message to the client.
	 * 
	 * @return Whether or not the message was sent successfully.
	 * @throws Exception In the case that the session has ended or write() fails.
	 * @since 0.2
	 *///TODO finish
	public boolean sendMessage() throws Exception{
		checkEnded();
		
		if (messageSend == null){	//Read message wasn't interpreted.
			return false;
		}
		
		if (!isBroken){
			outStrm.write(messageSend.toByteArray());
			
			if (DebugMode) System.out.println("sent");
			
			messageSend = null;	//Resets the message as to not confuse with past requests.
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sends a given Message to the current client.
	 * 
	 * @param messageToSend Message object to send to client.
	 * @return Whether or not the message was successfully sent.
	 * @throws Exception In the case that the session has ended or write() fails.
	 * @since 0.2
	 *///TODO finish
	public boolean sendMessage(Message messageToSend) throws Exception{
		checkEnded();
		
		if (messageToSend == null){
			return false;
		}
		
		if (!isBroken){
			outStrm.write(messageToSend.toByteArray());
			
			if (DebugMode) System.out.println("sent");
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Gets the current time and date in GMT; very useful to use with the "Date" header line.
	 * 
	 * @return String object that contains the exact date and time in GMT.
	 * @since 0.3
	 */
	public static String getTimeGMT(){
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String stringToReturn = new String();
		
		stringToReturn += df.format(new Date());
		
		return stringToReturn;
	}
	
	/**
	 * Gets the last modified time and date of a given file; very useful to use with the "Last-Modified" 
	 * header line.
	 * 
	 * @param file File object of file in need of a time-stamp.
	 * @return String object of the file's last modified time in GMT.
	 * @since 0.6
	 */
	public static String getTimeLastModified(File file){
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String stringToReturn = new String();
		
		stringToReturn += df.format(file.lastModified());
		
		return stringToReturn;
	}
	
	/**
	 * Gets the last modified time and date of a given file; very useful to use with the "Last-Modified" 
	 * header line. This instance of the method is not recommended, because it restricts access to other 
	 * File objects from using the same file given, until the GC picks it up (which is very unreliable).
	 * 
	 * @param file String object containing the location and name of the intended file.
	 * @return String object of the file's last modified time in GMT.
	 * @since 0.6
	 */
	public static String getTimeLastModified(String file){
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String stringToReturn = new String();
		
		stringToReturn += df.format(new File(file).lastModified());
		
		return stringToReturn;
	}
	
	/**
	 * Converts an integer to a String object. Meant to be used for the "Content-Length" header line.
	 * 
	 * @param integer Integer to convert.
	 * @return String object containing the converted integer.
	 * @since 0.6
	 */
	private static String intToString(int integer){
		String stringToReturn = new String();
		
		stringToReturn += integer;
		
		return stringToReturn;
	}
	
	/**
	 * Gets the content type of a given file; very useful for the "Content-Type" header line.
	 * 
	 * @param file File object of file in need of a content evaluation.
	 * @return String object containing the type of file.
	 * @since 0.7
	 */
	private static String getContentType(File file){
		String stringToReturn = new String();
		
		String secondHalf = file.getName().substring(file.getName().indexOf('.') + 1);
		
		if (secondHalf.equals("png")){
			stringToReturn += "image/";
		}
		else{
			stringToReturn += "text/";
		}
		
		stringToReturn += secondHalf;
		
		return stringToReturn;
	}
	
	/**
	 * Checks whether or not the session has ended and throws an exception if it was.
	 * 
	 * @throws Exception If session has ended; message of exception let's user know the session has ended.
	 * @since 0.1
	 */
	private void checkEnded() throws Exception{
		if (isEnded){
			throw new SessionEndedException();
		}
	}
	
	/**
	 * Simple testing server. Not to be used as the real application of the class, but merely an example.
	 * <p>
	 * Will run an HTTP server for 1 request and will return with a testing web-page every time.
	 * <p>
	 * The server can be accessed with any standard client (preferably a browser) with the host's IP 
	 * address or Domain Name (if applicable).
	 * 
	 * @param args [Not used]
	 * @since 0.1
	 *///TODO this
	public static void main(String[] args){
		try {
			HTTP test = new HTTP("C:\\Users\\stonisg\\Desktop\\ShitWebsite");
			
			test.DebugMode = true;
			
			for (int i = 0; i < 2; ++i){
				if (!test.hasConnection()){
				test.makeConnection();
				}
				else {
				test.breakConnection();
				test.makeConnection();
				}
			
				System.out.println("Connected");
			
				if (!test.readMessage()){	//Connection was broken or conversion failed.
					System.out.println("Failed on read.");
					test.endSession();
				}
			
				if (!test.interpretMessage()){	//Interpret message has failed; messageRead could be null.
					System.out.println("Failed on interpret.");
					//test.endSession();
				}
			
				if (!test.sendMessage()){	//The messageSend object was null.
					System.out.println("Failed on send.");
					test.endSession();
				}
			}
			
			//System.out.println(HTTP.getTimeGMT());
			
			test.endSession();
			
			System.out.println("Server ran successfully, no errors.");
			
		} catch (Exception e) {
			System.out.println("The server has failed, here's why:");
			System.out.println(e.getMessage());
		}
	}
}





















