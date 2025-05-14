package help;

public class InvalidPrimaryKeyException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPrimaryKeyException(String msg){
		super(msg);
	}
	
	public InvalidPrimaryKeyException(){
		super();
	}
	
}