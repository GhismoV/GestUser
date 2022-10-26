package it.ghismo.corso1.gestuser.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.ghismo.corso1.gestuser.models.Utenti;
 
public interface UtentiRepository extends MongoRepository<Utenti, String> {
	public Utenti findByUserId(String userId);
	public List<Utenti> findByUserIdLike(String partialUid);
}
