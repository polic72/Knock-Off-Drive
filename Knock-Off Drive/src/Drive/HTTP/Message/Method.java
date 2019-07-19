package Drive.HTTP.Message;

import Drive.HTTP.Version;

/**
 * Method is a utility enumeration to help identify different HTTP request methods.
 * <p>
 * It contains useful methods that convert a string to the appropriate method and check if a method is 
 * legal in a given version.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.3
 */
public enum Method {
	GET("GET", Version._1_0),	//Retrieve requested data.
	POST("POST", Version._1_0),	//Send data to server.
	HEAD("HEAD", Version._1_0),	//Same as get, but ignores data field.
	PUT("PUT", Version._1_1),	//Replaces data at URI with given data field.
	DELETE("DELETE", Version._1_1),	//Removes data at URI.
	CONNECT("CONNECT", Version._1_1),	//Establishes a tunnel to the server identified by a given URI.
	OPTIONS("OPTIONS", Version._1_1),	//Describes the communication options for the target resource.
	TRACE("TRACE", Version._1_1);	//Performs a message loop-back test along the path to the target.
	
	
	private String method;
	private Version acceptedVersion;
	
	/**
	 * Constructs a Method enumeration for an HTTP request message
	 * 
	 * @param method Method phrase as a String object.
	 * @param acceptedVersion Version enumeration representing the allowed version of HTTP the method uses.
	 * @since 0.3
	 */
	private Method(String method, Version acceptedVersion){
		this.method = method;
		this.acceptedVersion = acceptedVersion;
	}
	
	/**
	 * Tells whether or not a given version of HTTP can use the current method.
	 * 
	 * @param versionUsed Version enumeration the message is using.
	 * @return Whether or not a given version of HTTP can use the current method.
	 * @since 0.3
	 */
	public boolean isVersionAccepted(Version versionUsed){
		if (versionUsed == acceptedVersion){
			return true;
		}
		else if (acceptedVersion == Version._1_0 && versionUsed == Version._1_1){
			//1.1 can use every 1.0 method.
			return true;
		}
		
		return false;
	}
	
	/**
	 * Converts a given string to an Method enumeration.
	 * 
	 * @param methodString String object to convert.
	 * @return Correct Method enumeration if applicable, otherwise null.
	 * @since 0.3
	 */
	public static Method convertFromString(String methodString){
		Method method = null;
		for (Method meth : Method.values()){
			if (methodString.equals(meth.toString())){
				method = meth;
				break;
			}
		}
		
		return method;
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn += method;
		
		return stringToReturn;
	}
	
	/**
	 * A simple test to see if the methods in the enum are working. Could be, and should be, completely
	 * ignored in every sense on the word.
	 * 
	 * @param args [Not Used]
	 * @since 0.3
	 */
	public static void main(String[] args){
		Method test = Method.GET;
		
		System.out.println(test.isVersionAccepted(Version._1_0));	//Should be true.
		
		Method test1 = Method.GET;
		
		System.out.println(test1.isVersionAccepted(Version._1_1));	//Should be true.
		
		Method test2 = Method.PUT;
		
		System.out.println(test2.isVersionAccepted(Version._1_0));	//Should be false.
		
		System.out.println(Method.convertFromString("GET"));	//Should be "GET".
		
		System.out.println(Method.convertFromString("JHFKJERKJF"));	//Should be null.
	}
}



















