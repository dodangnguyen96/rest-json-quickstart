package repository;

import entity.Person;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface PersonRepository {
	Uni<Person> findById(Integer id);
	List<Person> findAll();
	Response create(Person person);
	Uni<Response> update(Integer id, Person person);
	Uni<Response> delete(Integer id);
}

