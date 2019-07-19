package Drive.HTTP;

/**
 * Version is a utility enumeration to help identify different HTTP versions.
 * <p>
 * While it is known that other version of HTTP exist (being 0.9 and 2.0), only 1.0 and 1.1 are available as 
 * of version [that being the java-code-version, not HTTP version] 1.0.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.1
 */
public enum Version {
	_1_0(1.0),
	_1_1(1.1);
	
	
	private double version;
	
	/**
	 * Constructs a Version enumeration for an HTTP message.
	 * 
	 * @param version Number associated with the version of HTTP as a double.
	 * @since 0.1
	 */
	private Version(double version){
		this.version = version;
	}
	
	/**
	 * Gets the version number of HTTP as a double.
	 * 
	 * @return The version number of HTTP as a double.
	 * @since 0.1
	 */
	public double getVersionNumber(){
		return version;
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn += "HTTP/";
		stringToReturn += String.valueOf(version);
		
		return stringToReturn;
	}
	
	/**
	 * The bare minimum of what can be called a test method for the Version enum. For Pete's sake, it 
	 * only tests for whether or not the toString() method works. Ignore at all costs (not really, just 
	 * poking fun).
	 * 
	 * @param args [Not Used]
	 * @since 0.1
	 */
	public static void main(String[] args){
		System.out.println(_1_0);
		System.out.println(_1_1);
	}
}
















