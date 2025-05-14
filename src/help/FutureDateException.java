package help;

public class FutureDateException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FutureDateException(String msg){
		super(msg);
	}
	
	public FutureDateException(){
		super();
	}
	
}