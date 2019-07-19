package Drive.HTTP;

/**
 * Small class that represents the exception thrown if an HTTP session has ended, but the user attempted to 
 * use the dead object. Good for determining logic errors in a server.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.5
 */
@SuppressWarnings("serial")
public class SessionEndedException extends Exception{
	public SessionEndedException(){
		this("The session has ended.");
	}
	
	public SessionEndedException(String message){
		super(message);
	}
}






















