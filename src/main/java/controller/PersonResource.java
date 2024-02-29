package controller;

import java.util.List;


import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import entity.Person;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.PersonRepository;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonRepository personRepository;

    @GET
    public Uni<List<Person>> getAllPersons() {
        return personRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Person> getPersonById(@PathParam("id") Integer id) {
        return personRepository.findById(id);
    }

    @POST
    public Uni<Response> createPerson(Person person) {
        return personRepository.create(person);
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updatePerson(@PathParam(("id")) Integer personId, Person person) {
        return personRepository.update(personId, person) ;
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deletePerson(@PathParam("id") Integer id) {
        return personRepository.delete(id);
    }

}
