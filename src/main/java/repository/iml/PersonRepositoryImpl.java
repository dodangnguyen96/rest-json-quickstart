package repository.iml;

import entity.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import repository.PersonRepository;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@ApplicationScoped
public class PersonRepositoryImpl implements PersonRepository {
    @Inject
    EntityManager entityManager;

    @Override
    public Person findById(Integer id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Override
    @Transactional
    public Response create(Person person) {
        try {
            entityManager.persist(person);
            return Response.status(Response.Status.CREATED).entity(person).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error creating person: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional
    public Response update(Integer id, Person person) {
        Person existingPerson = findById(id);
        if (existingPerson != null) {
            existingPerson.setName(person.getName());
            existingPerson.setName(person.getName());
            existingPerson.setAge(person.getAge());
            return Response.ok().status(NO_CONTENT).build();
        }
        return Response.ok().status(NOT_FOUND).build();
    }

    @Override
    @Transactional
    public Response delete(Integer id) {
        Person person = findById(id);
        if (person != null) {
            entityManager.remove(person);
            return Response.ok().status(NO_CONTENT).build();
        };
        return Response.ok().status(NOT_FOUND).build();
    }
}