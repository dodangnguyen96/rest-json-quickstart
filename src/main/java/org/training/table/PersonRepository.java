package org.training.table;

import java.util.List;



import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

	public Person findByName(String name) {
		return find("name", name).firstResult();
	}

	public List<Person> findAlive() {
		return list("status", Status.Alive);
	}

	public void deleteStefs() {
		delete("name", "Stef");
	}
}
