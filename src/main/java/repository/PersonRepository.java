package repository;

import entity.Person;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface PersonRepository {
	Person findById(Integer id);
	List<Person> findAll();
	Response create(Person person);
	Response update(Integer id, Person person);
	Response delete(Integer id);
}

