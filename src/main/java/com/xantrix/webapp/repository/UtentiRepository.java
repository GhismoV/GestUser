package com.xantrix.webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.xantrix.webapp.models.Utenti;
 
public interface UtentiRepository extends MongoRepository<Utenti, String> 
{
	public Utenti findByUserId(String UserId);
}
