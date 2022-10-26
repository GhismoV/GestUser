package it.ghismo.corso1.gestuser.error;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.ghismo.corso1.gestuser.dto.ResultDto;



public enum ResultEnum {
	@Result(code = "0", msg = "Operazione eseguita")	
	Ok

	,@Result(code = "0", msg = "Operazione eseguita correttamente sull'elemento [%1$s]")	
	OkParam1
	
	,@Result(code = "0", msg = "Autenticazione OK")	
	AuthOk

	,@Result(code = "1", msg = "Utente non trovato")
	NotFound

	,@Result(code = "3", msg = "Errore di validazione sul campo [%1$s.%2$s] avente valore [%3$s]")
	BindingValidationError
	
	,@Result(code = "4", msg = "Articolo con codice [%1$s] già esistente. Utilizzare il servizio di modifica.")
	DuplicateError

	,@Result(code = "5", msg = "Il campo [%1$s] non può assumere il valore [%2$s]")
	InfoInvalidValueError

	,@Result(code = "6", msg = "Utente e/o Password non validi!!!")
	AuthenticationError
	;
	
	public Result getResult() {
		try {
			return ResultEnum.class.getDeclaredField(this.name()).getAnnotation(Result.class);
		} catch (NoSuchFieldException | SecurityException e) {
			return null;
		}
	}
	
	public ResultDto getDto() {
		Result r = getResult();
		return Objects.nonNull(r) ? new ResultDto(r.code(), r.msg()) : null; 
	}

	public ResultDto getDto(Object... params) {
		Result r = getResult();
		return Objects.nonNull(r) ? new ResultDto(r.code(), String.format(r.msg(), params) ) : null; 
	}
	
	public ResponseEntity<ResultDto> getResultEntity(HttpStatus status, Object... params) {
		return new ResponseEntity<ResultDto>(this.getDto(params), status);
	}
	
}
