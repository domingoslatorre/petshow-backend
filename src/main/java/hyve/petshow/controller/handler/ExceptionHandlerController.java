package hyve.petshow.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> businessExceptionHandler(BusinessException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> notFoundExceptionHander(NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHander(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
}
