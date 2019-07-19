package Drive.HTTP.HeaderLine;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Merely used to contain a header line; uses a HeaderLineType enumeration to store only recognized headers 
 * and a String object to store the contents.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.3
 *///TODO
public class HeaderLine {
	private HeaderLineType type;
	private String contents;
	
	/**
	 * Constructs a standard header line for an HTTP message.
	 * 
	 * @param type Recognized HeaderLineType (essentially what's before the ":" in the line).
	 * @param contents The rest of the line; contains data on the actual instruction for the header line.
	 * @since 0.3
	 */
	public HeaderLine(HeaderLineType type, String contents){
		this.type = type;
		this.contents = contents;
	}
	
	/**
	 * Gets the HeaderLineType enumeration that the current header line is using.
	 * 
	 * @return The HeaderLineType enumeration that the current header line is using.
	 * @since 0.3
	 */
	public HeaderLineType getType(){
		return type;
	}
	
	/**
	 * Gets the contents of the header line as a String object.
	 * 
	 * @return The contents of the header line as a String object.
	 * @since 0.5
	 */
	public String getContents(){
		return contents;
	}
	
	/**
	 * Tells whether or not the current header line has a legal answer.
	 * 
	 * @return Whether or not the current header line has a legal answer.
	 * @since 0.7
	 */
	public boolean isLegalAnswer(){
		if (type.getAcceptedAnswers().isEmpty()){
			return true;
		}
		
		Scanner reader = new Scanner(type.getAcceptedAnswers());
		
		while (reader.hasNextLine()){
			if (contents.equals(reader.nextLine())){
				reader.close();
				
				return true;
			}
		}
		
		reader.close();
		return false;
	}
	
	/**
	 * Tells whether or not a given header line type and the given contents are legal together.
	 * 
	 * @param type HeaderLineType enumeration to check for answers.
	 * @param contents Attempted contents of header line.
	 * @return Whether or not a given header line type and the given contents are legal together.
	 * @since 0.7
	 */
	public static boolean isLegalAnswer(HeaderLineType type, String contents){
		if (type.getAcceptedAnswers().isEmpty()){
			return true;	//Empty means that everything is accepted.
		}
		
		Scanner reader = new Scanner(type.getAcceptedAnswers());
		LinkedList<String> formats = new LinkedList<>();
		
		while (reader.hasNextLine()){		//Puts the known formats into a list.
			formats.add(reader.nextLine());
		}
		
		reader.close();	//The Scanner is no longer needed.
		
		
		if (type == HeaderLineType.DATE || type == HeaderLineType.IF_MOD_SINCE || 
				type == HeaderLineType.IF_UNMOD_SINCE || type == HeaderLineType.KEEP_ALIVE || 
				type == HeaderLineType.IF_RANGE || type == HeaderLineType.RETRY_AFTER || 
				type == HeaderLineType.SERVER || type == HeaderLineType.CONTENT_RANGE || 
				type == HeaderLineType.EXPIRES || type == HeaderLineType.LAST_MODIFIED){
			boolean stillLegal = false;
			for (int q = 0; q < formats.size(); ++q){
				if (contents.matches(formats.get(q))){	//The contents match the known respective formats.
					stillLegal = true;
					break;	//No need to keep checking.
				}
			}
			
			return stillLegal;
		}
		
		String subLine = new String();
		
		for (int i = 0; i < contents.length(); ++i){	//Form subLines to check for legality.
			/*if (contents.charAt(i) == ',' && type.getAreCommasAllowed){
				return false;	//Commas are banned.						//TODO implement
			}
			else */if (contents.charAt(i) == ','){	//Commas separate legal subLines.
				boolean stillLegal = false;
				
				for (int q = 0; q < formats.size(); ++q){
					if (subLine.matches(formats.get(q))){	//The subLine matches one of the known formats.
						stillLegal = true;
						break;	//No need to keep checking.
					}
				}
				
				if (!stillLegal) return false;	//One of the subLines is illegal.
			}
			
			if (contents.charAt(i) != ' ') subLine += contents.charAt(i);	//Spaces are ignored.
		}
		
		boolean stillLegal = false;
		
		for (int q = 0; q < formats.size(); ++q){
			if (subLine.matches(formats.get(q))){	//The last subLine matches one of the known formats.
				stillLegal = true;
				break;	//No need to keep checking.
			}
		}
		
		if (!stillLegal) return false;	//The last subLine is illegal.
		
		return true;
		
		
		
		
		
		
									//Failed Experiment
		/*Scanner reader = null;
		String subLine = new String();
		
		for (int i = 0; i < contents.length(); ++i){	//Form subLines to check for legality.
			if (contents.charAt(i) == ','){	//Commas separate legal subLines.
				reader = new Scanner(type.getAcceptedAnswers());
				
				System.out.println("Here?");
				String tempCompareLine = new String();
				while (reader.hasNextLine()){
					tempCompareLine = reader.nextLine();
					
					for (int q = 0; q < tempCompareLine.length(); ++q){
						boolean firstAsterik = true;
						
						if (tempCompareLine.charAt(q) == '*'){	//Looks for wild-card character.
							if (subLine.indexOf(';') != -1 && firstAsterik){	//If the line has a ';'.
								tempCompareLine = tempCompareLine.substring(0, q)
										+ subLine.substring(q, subLine.indexOf(';'))
										+ tempCompareLine.substring(subLine.indexOf(';') + 1);
								
								firstAsterik = false;
							}
							else {
								tempCompareLine = tempCompareLine.substring(0, q)
										+ subLine.substring(q)
										+ tempCompareLine.substring(q + 1);
							}
							
							System.out.println("tempCompareLine: " + tempCompareLine);
						}
						
					}
					
					if (!subLine.equals(tempCompareLine)){	//Compare subLine to legality.
						reader.close();
						
						return false;
					}
				}
				
				subLine = new String();	//Resets subLine to read the next subLine.
			}
			
			if (contents.charAt(i) != ' ') subLine += contents.charAt(i);	//Spaces are ignored.
		}
		
		reader = new Scanner(type.getAcceptedAnswers());
		
		String tempCompareLine = new String();
		while (reader.hasNextLine()){
			tempCompareLine = reader.nextLine();
			
			for (int q = 0; q < tempCompareLine.length(); ++q){
				boolean firstAsterik = true;
				
				if (tempCompareLine.charAt(q) == '*'){	//Looks for wild-card character.
					if (subLine.indexOf(';') != -1 && firstAsterik){	//If the line has a ';'.
						tempCompareLine = tempCompareLine.substring(0, q)
								+ subLine.substring(0, subLine.indexOf(';'))
								+ tempCompareLine.substring(subLine.indexOf(';') + 1);
						
						firstAsterik = false;
					}
					else {
						System.out.println("tempCompareLine before: " + tempCompareLine);
						
						
						
						tempCompareLine = tempCompareLine.substring(0, q);
						if (subLine.startsWith(tempCompareLine)){
							tempCompareLine += subLine.substring(0);subLine.ma
						}
						else {
							
						}
						tempCompareLine += tempCompareLine.substring(q + 1);
					}
					
					System.out.println("tempCompareLine after: " + tempCompareLine);
				}
				
			}
			
			if (!subLine.equals(tempCompareLine)){	//Compare subLine to legality.
				reader.close();
				
				return false;
			}
		}
		
		//reader.close();
		return true;*/
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn += type;
		stringToReturn += ": ";
		stringToReturn += contents;
		
		return stringToReturn;
	}
	
	/**
	 * Tester method for the HeaderLine class.
	 * 
	 * @param args [Not Used]
	 * @since 0.7
	 */
	public static void main(String[] args){
		System.out.println("aqwertyuiob".matches("a\\p{ASCII}{0,}b"));
		System.out.println("songw".matches("song\\p{Alpha}{0,}"));
		System.out.println("1234".matches("\\p{ASCII}{1,3}"));
		System.out.println("bytes=-".matches("bytes=\\p{Digit}{0,}-?\\p{Digit}{0,}"));
		
		System.out.println("Is this legal? \"Accept: text/html1\": "
				+ isLegalAnswer(HeaderLineType.ACCEPT, "text/html1"));
		
		System.out.println("Is this legal? \"Accept: text/html; q=1\": "
				+ isLegalAnswer(HeaderLineType.ACCEPT, "text/html; q=1"));
		
		System.out.println("Is this legal? \"Sat, 18 Nov 2017 19:05:56 GMT\": "
				+ isLegalAnswer(HeaderLineType.DATE, "Sat, 18 Nov 2017 19:05:56 GMT"));
	}
}



















