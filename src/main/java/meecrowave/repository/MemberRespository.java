package meecrowave.repository;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.meecrowave.jpa.api.Unit;

import meecrowave.entity.Member;


/**
 * @author Ravisankar C
 *
 */

@ApplicationScoped
public class MemberRespository {

	@Inject
	@Unit(name = "meecrowave_test")
	private EntityManager em;
	
	public Member addMember(Member m) {
		em.persist(m);
		return m;
	}
	
	public Member findMember(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll() {
		return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
	}
	
}