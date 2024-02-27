package org.acme.rest.json;

import java.util.List;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.training.table.Person;
import org.training.table.PersonRepository;

@Path("/person")
public class PersionResource {

	@Inject
	PersonRepository personRepository;

	@GET
	public List<Person> list() {
		return personRepository.listAll();
	}

	@POST
	@Transactional
	public List<Person> add(Person person) {
		personRepository.persist(person);
		return personRepository.listAll();
	}

	@PUT
	@Path("/{id}")
	@Transactional
	public List<Person> putPerson(@PathParam(("id")) long personId, Person person) {
		Person per = personRepository.findById(personId);
		per.setName(person.getName());
		per.setStatus(person.getStatus());
		per.setBirth(person.getBirth());
		personRepository.persist(per);
		return personRepository.listAll();
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	public List<Person> delete(@PathParam(("id")) long personId) {
		personRepository.deleteById(personId);
		return personRepository.listAll();
	}

	/**
	 * delete list person
	 * 
	 * @param ids
	 * @return
	 */
	@DELETE
	@Transactional
	public List<Person> delete(List<Person> listPersion) {
		List<Long> ids = listPersion.stream().map(person -> person.getId()).collect(Collectors.toList());
//		personRepository
//				.delete(String.format("id in (%s)", ids.stream().map(id -> id + "").collect(Collectors.joining(","))));
		personRepository.delete("id in :ids", ids);
		return personRepository.listAll();
	}
}
