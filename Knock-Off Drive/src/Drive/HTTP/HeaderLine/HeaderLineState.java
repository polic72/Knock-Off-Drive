package Drive.HTTP.HeaderLine;

/**
 * Small, utility enumeration that contains a possible state that a header line type can be, as in what 
 * types of message can legally use the given header line.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.3
 */
public enum HeaderLineState{
	GENERAL(0, "General"),
	REQUEST(1, "Request"),
	RESPONSE(2, "Response"),
	ENTITY(3, "Entity");
	
	
	int stateNumber;
	String description;
	
	/**
	 * Constructs a HeaderLineState enumeration.
	 * 
	 * @param stateNumber Integer used to refer to different states.
	 * @param description String object that describes what the state is.
	 * @since 0.3
	 */
	private HeaderLineState(int stateNumber, String description){
		this.stateNumber = stateNumber;
		this.description = description;
	}
	
	/**
	 * Gets the description of the enumeration as a String object.
	 * 
	 * @return The description of the enumeration as a String object.
	 * @since 0.3
	 */
	public String getDespription(){
		return description;
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn += stateNumber;
		
		return stringToReturn;
	}
}



















