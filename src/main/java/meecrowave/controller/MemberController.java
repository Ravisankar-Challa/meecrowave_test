package meecrowave.controller;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import meecrowave.entity.Member;
import meecrowave.repository.MemberRespository;


/**
 * @author Ravisankar C
 *
 */
@Path("/")
@ApplicationScoped
public class MemberController {
	
	private static final Logger LOG = Logger.getLogger(MemberController.class.getName());
	
	@Inject
	private MemberRespository repository;
	
	public MemberController() {
		LOG.info("Creating Hello World Resource Object");
	}
	
	@PostConstruct
	public void init() {
		LOG.info("Inside Hello World Resource @Post Construct");
	}
	
	@GET
	@Path("member/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Member findMember(@PathParam("id") String id) {
		System.out.println(id+" number");
		return repository.findMember(Long.valueOf(id));
	}
	
	@GET
	@Path("members")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Member> findAll() {
		return repository.findAll();
	}
	
	@POST
	@Path("members")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Member addMember(Member member) {
		return repository.addMember(member);
	}
	
	@PreDestroy
	public void destroy() {
		LOG.info("Inside Hello World Resource @PreDestroy");
	}
}