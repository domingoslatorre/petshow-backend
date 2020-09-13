package hyve.petshow.exceptions;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BusinessException() {
		
	}
	
	public BusinessException(String mensagem) {
		super(mensagem);
	}

}
