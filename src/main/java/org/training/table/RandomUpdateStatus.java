package org.training.table;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import io.quarkus.scheduler.Scheduled;

public class RandomUpdateStatus {
	@Inject
	PersonRepository personRepository;

	@Scheduled(every = "1s")
	@Transactional
	public void randomUpdateStatus() {
		List<Person> listPerson = personRepository.listAll();
		int randomperson = (int) (Math.random() * (listPerson.size()));
		Person perdson = listPerson.get(randomperson);
		perdson.setStatus(Status.StatusRandom());
		personRepository.persist(perdson);
	}
}
