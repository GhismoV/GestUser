package it.ghismo.corso1.gestuser.exception;

import org.springframework.http.HttpStatus;

import it.ghismo.corso1.gestuser.error.ResultEnum;

public class NotFoundException extends BaseResultException {
	private static final long serialVersionUID = -2404717895107950786L;
	
	public NotFoundException() { 
		super(ResultEnum.NotFound); 
	}
	
	@Override public HttpStatus getHttpStatus() { return HttpStatus.NOT_FOUND; }

}
