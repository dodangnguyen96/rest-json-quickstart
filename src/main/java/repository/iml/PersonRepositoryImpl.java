package repository.iml;

import entity.Person;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import repository.PersonRepository;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.*;

@ApplicationScoped
public class PersonRepositoryImpl implements PersonRepository {
    @Inject
    SessionFactory sf;

    @Override
    public Uni<Person> findById(Integer id) {
        return sf.withTransaction((s, t) -> s.find(Person.class, id));
    }

    @Override
    public Uni<List<Person>> findAll() {
        return sf.withTransaction((s, t) -> s
                .createQuery("SELECT p FROM Person p", Person.class)
                .getResultList()
        );
    }

    @Override
    public Uni<Response> create(Person person) {

        return sf.withTransaction((s, t) -> s.persist(person))
                .replaceWith(Response.ok(person).status(CREATED)::build);

    }

    @Override
    public Uni<Response> update(Integer id, Person person) {
        return sf.withTransaction((s, t) -> s.find(Person.class, id)
                        .onItem().ifNull().failWith(new WebApplicationException("Person missing from database.", NOT_FOUND))
                        .invoke(entity -> {
                            entity.setName(person.getName());
                            entity.setName(person.getName());
                            entity.setAge(person.getAge());
                        }))
                .map(entity -> Response.ok(entity).build());
    }

    @Override
    public Uni<Response> delete(Integer id) {
        return sf.withTransaction((s, t) -> s.find(Person.class, id)
                        .onItem().ifNull().failWith(new WebApplicationException("Person missing from database.", NOT_FOUND))
                        .call(s::remove))
                .replaceWith(Response.ok().status(NO_CONTENT)::build);
    }
}