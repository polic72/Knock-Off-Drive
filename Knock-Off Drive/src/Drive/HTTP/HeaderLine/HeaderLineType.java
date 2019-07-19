package Drive.HTTP.HeaderLine;

import Drive.HTTP.Version;

/**
 * What used to be a simple String object has evolved into quite a sizable enumeration; they grow up so 
 * fast *wipes tear*.
 * <p>
 * Stores every recognized header line type, as well as useful information about it.
 * 
 * @author Garrett Stonis
 * @version 1.0
 * @since 0.3
 *///TODO
public enum HeaderLineType {
	//General:
		DATE("Date", HeaderLineState.GENERAL, Version._1_0, "\\p{Alpha}{3}, \\p{Digit}{1,2} \\p{Alpha}{3} \\p{Digit}{4} \\p{Digit}{2}:\\p{Digit}{2}:\\p{Digit}{2} GMT"),
		CACHE_CONTROL("Cache-Control", HeaderLineState.GENERAL, Version._1_1, ""),//TODO add answers, handle
		CONNECTION("Connection", HeaderLineState.GENERAL, Version._1_1, "Closed\r\nKeep-Alive\r\nclose\r\nkeep-alive"),//TODO handle
		PRAGMA("Pragma", HeaderLineState.GENERAL, Version._1_0, "no-cache"),//TODO handle
		TRAILER("Trailer", HeaderLineState.GENERAL, Version._1_1, ""),//TODO add answers, handle
		TRANSFER_ENC("Transfer-Encoding", HeaderLineState.GENERAL, Version._1_1, "chunked"),//TODO add answers, handle
		UPGRADE("Upgrade", HeaderLineState.GENERAL, Version._1_1, ""),//TODO add answers, handle
		VIA("Via", HeaderLineState.GENERAL, Version._1_0, ""),//TODO add answers, handle
		WARNING("Warning", HeaderLineState.GENERAL, Version._1_0, "\\p{Digit}{1,} \\p{ASCII}{1,} \\p{Alpha}{1,} \\p{ASCII}{1,}"),//TODO handle
		
	//Request:
		ACCEPT("Accept", HeaderLineState.REQUEST, Version._1_0, "\\p{Alpha}{1,}/\\p{ASCII}{1,}\r\n\\p{Alpha}{1,}/\\p{ASCII}{1,};q=\\p{ASCII}{1,}\r\n\\*/\\*;q=\\p{ASCII}{1,}"),//TODO handle
		ACCEPT_CHARSET("Accept-Charset", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}\r\n\\p{ASCII}{1,};q=\\p{ASCII}{1,}"),//TODO add answers, handle
		ACCEPT_ENCODING("Accept-Encoding", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}\r\n\\p{ASCII}{1,};q=\\p{ASCII}{1,}"),//TODO add answers, handle
		ACCEPT_LANGUAGE("Accept-Language", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}\r\n\\p{ASCII}{1,};q=\\p{ASCII}{1,}"),//TODO add answers, handle
		AUTHORIZATION("Authorization", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}"),//TODO add answers, handle
		COOKIE("Cookie", HeaderLineState.REQUEST, Version._1_1, "\\p{ASCII}{3,}"),//TODO add answers, handle
		EXPECT("Excpect", HeaderLineState.REQUEST, Version._1_0, ""),//TODO add answers, handle
		FROM("From", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}@\\p{ASCII}{1,}"),//TODO add answers, handle
		HOST("Host", HeaderLineState.REQUEST, Version._1_0, "\\p{Digit}{1,3}.\\p{Digit}{1,3}.\\p{Digit}{1,3}.\\p{Digit}{1,3}"),//TODO add answers, handle
		IF_MATCH("If-Match", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}\r\n\\*"),//ETag //TODO add answers, handle
		IF_MOD_SINCE("If-Modified-Since", HeaderLineState.REQUEST, Version._1_0, "\\p{Alpha}{3}, \\p{Digit}{1,2} \\p{Alpha}{3} \\p{Digit}{4} \\p{Digit}{2}:\\p{Digit}{2}:\\p{Digit}{2} GMT\r\n\\*"),//Date //TODO add answers, handle
		IF_NONE_MATCH("If-None-Match", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}\r\n\\*"),//ETag //TODO add answers, handle
		IF_RANGE("If-Range", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}\r\n\\p{Alpha}{3}, \\p{Digit}{1,2} \\p{Alpha}{3} \\p{Digit}{4} \\p{Digit}{2}:\\p{Digit}{2}:\\p{Digit}{2} GMT"),//ETags and Dates //TODO add answers, handle
		IF_UNMOD_SINCE("If-Unmodified-Since", HeaderLineState.REQUEST, Version._1_0, "\\p{Alpha}{3}, \\p{Digit}{1,2} \\p{Alpha}{3} \\p{Digit}{4} \\p{Digit}{2}:\\p{Digit}{2}:\\p{Digit}{2} GMT\r\n\\*"),//Date //TODO add answers, handle
		MAX_FORWARDS("Max-Forwards", HeaderLineState.REQUEST, Version._1_0, "\\p{Digit}{1,}"),//TODO add answers, handle
		PROXY_AUTH("Proxy-Authorization", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{1,}"),//TODO add answers, handle
		RANGE("Range", HeaderLineState.REQUEST, Version._1_0, "bytes=\\p{Digit}{0,}-?\\p{Digit}{0,}"),//TODO add answers, handle
		REFERER("Referer", HeaderLineState.REQUEST, Version._1_0, "http://\\p{ASCII}{1,}"),//TODO add answers, handle
		TE("TE", HeaderLineState.REQUEST, Version._1_0, "\\p{ASCII}{0,}\r\n\\p{ASCII}{1,};q=\\p{ASCII}{1,}"),//TODO add answers, handle
		UP_INSECURE_REQUESTS("Upgrade-Insecure-Requests", HeaderLineState.REQUEST, Version._1_0, "\\p{Digit}{1,}"),//TODO add answers, handle
		USER_AGENT("User-Agent", HeaderLineState.REQUEST, Version._1_0, ""),//TODO handle
		
	//Response:
		ACCEPT_RANGES("Accept-Ranges", HeaderLineState.RESPONSE, Version._1_0, "bytes\r\nnone"),//TODO add answers, handle
		AGE("Age", HeaderLineState.RESPONSE, Version._1_0, "\\p{Digit}{1,}"),//TODO add answers, handle
		ETAG("ETag", HeaderLineState.RESPONSE, Version._1_0, "\\p{ASCII}{1,}"),//TODO add answers, handle
		KEEP_ALIVE("Keep-Alive", HeaderLineState.RESPONSE, Version._1_0, "timeout=\\p{Digit}{1,}, max=\\p{Digit}{1,}"),//TODO add answers, handle
		LOCATION("Location", HeaderLineState.RESPONSE, Version._1_0, "http://\\p{ASCII}{1,}"),//TODO add answers, handle
		PORXY_AUTH("Proxy-Authenticate", HeaderLineState.RESPONSE, Version._1_0, ""),//TODO add answers, handle
		RETRY_AFTER("Retry-After", HeaderLineState.RESPONSE, Version._1_0, "\\p{Digit}{1,}\r\n\\p{Alpha}{3}, \\p{Digit}{1,2} \\p{Alpha}{3} \\p{Digit}{4} \\p{Digit}{2}:\\p{Digit}{2}:\\p{Digit}{2} GMT"),//Date //TODO add answers, handle
		SERVER("Server", HeaderLineState.RESPONSE, Version._1_0, "GES - \\p{ASCII}{1,}"),//TODO add answers, handle
		SET_COOKIE("Set-Cookie", HeaderLineState.RESPONSE, Version._1_1, ""),//TODO add answers, handle
		VARY("Vary", HeaderLineState.RESPONSE, Version._1_0, "\\p{ASCII}{1,}\r\n\\*"),//TODO add answers, handle
		WWW_AUTH("WWW-Authenticate", HeaderLineState.RESPONSE, Version._1_0, ""),//TODO add answers, handle
	
	//Entity:
		ALLOW("Allow", HeaderLineState.ENTITY, Version._1_0, "GET\r\nPOST\r\nHEAD\r\nPUT\r\nDELETE\r\nCONNECT\r\nOPTIONS\r\nTRACE"),//TODO add answers, handle
		CONTENT_ENC("Content-Encoding", HeaderLineState.ENTITY, Version._1_1, "\\p{ASCII}{1,}"),//TODO add answers, handle
		CONTENT_LANG("Content-Language", HeaderLineState.ENTITY, Version._1_0, "\\p{ASCII}{1,}"),//TODO add answers, handle
		CONTENT_LENGTH("Content-Length", HeaderLineState.ENTITY, Version._1_0, "\\p{Digit}{1,}"),//TODO add answers, handle
		CONTENT_LOC("Content-Location", HeaderLineState.ENTITY, Version._1_0, "http://\\p{ASCII}{1,}"),//TODO add answers, handle
		CONTENT_MD5("Content-MD5", HeaderLineState.ENTITY, Version._1_1, "\\p{ASCII}{32}"),//TODO add answers, handle
		CONTENT_RANGE("Content-Range", HeaderLineState.ENTITY, Version._1_1, "bytes \\p{Digit}{1,}-\\p{Digit}{1,}/\\p{Digit}{1,}"),//TODO add answers, handle
		CONTENT_TYPE("Content-Type", HeaderLineState.ENTITY, Version._1_0, "\\p{Alpha}{1,}/\\p{ASCII}{1,}\r\n\\p{Alpha}{1,}/\\p{ASCII}{1,};charset=\\p{ASCII}{1,}"),//TODO add answers, handle
		EXPIRES("Expires", HeaderLineState.ENTITY, Version._1_0, "\\p{Alpha}{3}, \\p{Digit}{1,2} \\p{Alpha}{3} \\p{Digit}{4} \\p{Digit}{2}:\\p{Digit}{2}:\\p{Digit}{2} GMT"),//Date //TODO add answers, handle
		LAST_MODIFIED("Last-Modified", HeaderLineState.ENTITY, Version._1_0, "\\p{Alpha}{3}, \\p{Digit}{1,2} \\p{Alpha}{3} \\p{Digit}{4} \\p{Digit}{2}:\\p{Digit}{2}:\\p{Digit}{2} GMT");//Date //TODO add answers, handle
	
	
	private String type;
	private HeaderLineState state;	//0 = General, 1 = Request, 2 = Response, 3 = Entity
	private Version acceptedVersion;
	private String acceptedAnswers;
	
	/**
	 * Constructs a HeaderLineType enumeration.
	 * 
	 * @param type String object that represents exactly what the type should look like.
	 * @param state State enumeration that contains what kind of message can have this header line type.
	 * @param version Version enumeration that represents what version of HTTP is accepted.
	 * @since 0.3
	 */
	private HeaderLineType(String type, HeaderLineState state, Version acceptedVersion, String acceptedAnswers){
		this.type = type;
		this.state = state;
		this.acceptedVersion = acceptedVersion;
		this.acceptedAnswers = acceptedAnswers;
	}
	
	/**
	 * Converts a given string to an HeaderLineType enumeration.
	 * 
	 * @param typeString String object to convert.
	 * @return Correct HeaderLineType enumeration if applicable, otherwise null.
	 * @since 0.3
	 */
	public static HeaderLineType convertFromString(String typeString){
		HeaderLineType type = null;
		for (HeaderLineType ty : HeaderLineType.values()){
			if (typeString.equals(ty.toString())){
				type = ty;
				break;
			}
		}
		
		return type;
	}
	
	/**
	 * Tells whether or not a given version of HTTP can use the current header line type.
	 * 
	 * @param versionUsed Version the message is using.
	 * @return Whether or not a given version of HTTP can use the current header line type.
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
	 * Tells whether or not a State enumeration is legal to use with a message type.
	 * 
	 * @param isResponse Whether or not a message is a response message.
	 * @return Whether or not a State enumeration is legal to use with the given message type.
	 * @since 0.3
	 */
	public boolean isLegalState(boolean isResponse){
		if (isResponse && state != HeaderLineState.REQUEST){
			return true;
		}
		else if (!isResponse && state != HeaderLineState.RESPONSE){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Gets the State enumeration that the current HeaderLineType enumeration is bounded by.
	 * 
	 * @return The State enumeration that the current HeaderLineType enumeration is bounded by.
	 * @since 0.3
	 */
	public HeaderLineState getState(){
		return state;
	}
	
	/**
	 * Gets the Version enumeration representing what version of HTTP is accepted by the current 
	 * HeaderLineType enumeration.
	 * 
	 * @return The accepted Version enumeration used by the current HeaderLineType enumeration.
	 * @since 0.3
	 */
	public Version getAcceptedVersion(){
		return acceptedVersion;
	}
	
	/**
	 * Gets the String object of the accepted answers for the given HeaderLineType enumeration. Remember 
	 * that if the String is empty, any answer is accepted. Answers are separated by "\r\n" characters.
	 * 
	 * @return String object containing all acceptable answers for the current HeaderLineType enumeration.
	 * @since 0.7
	 */
	public String getAcceptedAnswers(){
		return acceptedAnswers;
	}
	
	public String toString(){
		String stringToReturn = new String();
		
		stringToReturn += type;
		
		return stringToReturn;
	}
}

















