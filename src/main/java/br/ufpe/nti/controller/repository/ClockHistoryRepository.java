package br.ufpe.nti.controller.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufpe.nti.model.Clock;

@Repository
public class ClockHistoryRepository {

	@PersistenceContext
	private EntityManager em;

	public ClockHistoryRepository() {

	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}

	public Clock find(Long id) {
		return em.find(Clock.class, id);
	}

	public List<Clock> listAll() {
		return em.createQuery("SELECT c FROM Clock c", Clock.class).getResultList();
	}

	@Transactional
	public Clock save(Clock c) {
		em.persist(c);
		return c;
	}

}
