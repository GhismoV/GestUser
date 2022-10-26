package it.ghismo.corso1.gestuser.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ghismo.corso1.gestuser.models.Utenti;
import it.ghismo.corso1.gestuser.repository.UtentiRepository;

@Service
public class UtentiServiceImpl implements UtentiService {

	@Autowired
	private UtentiRepository repo;
	
	@Override
	public Utenti readUtente(String id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Utenti addUtente(Utenti user) {
		return repo.save(user);
	}

	@Override
	public Utenti findUtente(String uid) {
		return repo.findByUserId(uid);
	}

	@Override
	public List<Utenti> findUtenti(String partialUid) {
		return Objects.isNull(partialUid) ? repo.findAll() : repo.findByUserIdLike(partialUid);
	}

	@Override
	public void deleteUtente(String userid) {
		repo.deleteById(userid);
	}

}
