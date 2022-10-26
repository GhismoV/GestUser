package it.ghismo.corso1.gestuser.controllers;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ghismo.corso1.gestuser.dto.ResultDto;
import it.ghismo.corso1.gestuser.error.ResultEnum;
import it.ghismo.corso1.gestuser.exception.BindingValidationException;
import it.ghismo.corso1.gestuser.exception.DuplicateException;
import it.ghismo.corso1.gestuser.exception.NotFoundException;
import it.ghismo.corso1.gestuser.models.Utenti;
import it.ghismo.corso1.gestuser.services.UtentiService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(value = {"http://localhost:4200"})
@Slf4j
@Validated
public class UtentiController {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ResourceBundleMessageSource rb;
	
	@Autowired
	private UtentiService utentiSvc;

	@PostMapping(
			value = "/inserisci", 
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@SneakyThrows
	public ResponseEntity<ResultDto> createUser(
			@RequestBody(required = true)
			@Valid
			Utenti user,
			
			BindingResult bindingResult
			) {
		log.debug("--- Servizio Inserisci Utente --- \ninput userid: [{}]", user.getUserId());
		checkInputUtente(user, bindingResult);
		
		Utenti u = utentiSvc.findUtente(user.getUserId());
		if(Objects.isNull(u)) {
			log.debug("--- Servizio Inserisci Utente --- Modalità Inserimento");
		} else {
			log.debug("--- Servizio Inserisci Utente --- Modalità Modifica");
			BeanUtils.copyProperties(user, u, "id", "userId");
			user  = u;
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword())); // crypt pwd
		utentiSvc.addUtente(user);
		return ResultEnum.OkParam1.getResultEntity(HttpStatus.CREATED, user.getUserId());
	}
	
	@GetMapping(
			value = "/cerca/userid/{uid}", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@SneakyThrows
	public ResponseEntity<Utenti> findUsers(
			@PathVariable(name = "uid", required = true)
			@NotEmpty
			String userid) {
		log.debug("--- Servizio Cerca Utenti --- \ninput userid: [{}]", userid);
		Utenti u = utentiSvc.findUtente(userid);
		if(Objects.isNull(u)) {
			throw new NotFoundException();
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@GetMapping(
			value = "/cerca/tutti", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@SneakyThrows
	public ResponseEntity<List<Utenti>> findAllUsers() {
		log.debug("--- Servizio Cerca Tutti gli Utenti ---");
		List<Utenti> us = utentiSvc.findUtenti(null);
		if(us.isEmpty()) {
			throw new NotFoundException();
		}
		return new ResponseEntity<>(us, HttpStatus.OK);
	}
	
	@DeleteMapping(
			value = "/elimina/{uid}", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@SneakyThrows
	public ResponseEntity<ResultDto> deleteUser(
			@PathVariable(name = "uid", required = true)
			@NotEmpty
			String userid) {
		log.debug("--- Servizio Cancella Utente ---/nuserid : [{}]", userid);
		Utenti u = utentiSvc.findUtente(userid);
		if(Objects.isNull(u)) {
			throw new NotFoundException();
		}
		utentiSvc.deleteUtente(u.getId());
		return ResultEnum.OkParam1.getResultEntity(HttpStatus.OK, userid);
	}
	

	/*
	 * 
	 * 
	 */
	
	@SneakyThrows
	private boolean checkInputUtente(Utenti in, BindingResult bindingResult) {
		log.debug("Utente: {}", in.toString());
		if(bindingResult.hasErrors()) {
			FieldError f = bindingResult.getFieldError();
			String errTranslated = rb.getMessage(f, LocaleContextHolder.getLocale());
			log.warn(errTranslated);
			throw new BindingValidationException(f);
		}
		Utenti u = utentiSvc.readUtente(in.getUserId());
		if(Objects.nonNull(u)) {
			throw new DuplicateException(in.getUserId());
		}
		return true;
	}
	
}
