package repository.iml;

import entity.Person;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.hibernate.reactive.mutiny.Mutiny;
import repository.PersonRepository;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@ApplicationScoped
public class PersonRepositoryImpl implements PersonRepository {
    @Inject
    EntityManager entityManager;
    @Inject
    Mutiny.SessionFactory sf;

    @Override
    public Uni<Person> findById(Integer id) {
        return  sf.withTransaction((s,t) -> s.find(Person.class, id));
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
    public Uni<Response> update(Integer id, Person person) {
        return sf.withTransaction((s,t) -> s.find(Person.class, id)
                        .onItem().ifNull().failWith(new WebApplicationException("Person missing from database.", NOT_FOUND))
                        .invoke(entity -> {
                            entity.setName(person.getName());
                            entity.setName(person.getName());
                            entity.setAge(person.getAge());
                        }))
                .map(entity -> Response.ok(entity).build());
    }

    @Override
    @Transactional
    public Uni<Response> delete(Integer id) {
        return sf.withTransaction((s, t) -> s.find(Person.class, id)
                        .onItem().ifNull().failWith(new WebApplicationException("Person missing from database.", NOT_FOUND))
                        // If entity exists then delete it
                        .call(s::remove))
                .replaceWith(Response.ok().status(NO_CONTENT)::build);
    }
}