package help;

public class negativeNumberException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public negativeNumberException(String msg){
		super(msg);
	}
	
	public negativeNumberException(){
		super();
	}
	
}