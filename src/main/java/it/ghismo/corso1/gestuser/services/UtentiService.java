package it.ghismo.corso1.gestuser.services;

import java.util.List;

import it.ghismo.corso1.gestuser.models.Utenti;

public interface UtentiService {
	Utenti readUtente(String userid);
	Utenti addUtente(Utenti user);
	Utenti findUtente(String uid);
	List<Utenti> findUtenti(String partialUid);
	void deleteUtente(String userid);
}
