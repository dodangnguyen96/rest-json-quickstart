package org.training.sse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.reactivestreams.Publisher;

@Path("/hello")
public class ReactiveGreetingResource {

	@Inject
	ReactiveGreetingService service;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/greeting/{name}")
	public Uni<String> greeting(@PathParam(("name")) String name) {
		return service.greeting(name);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "hello";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/greeting/{count}/{name}")
	public Multi<String> greetings(@PathParam(("count")) int count,@PathParam(("name")) String name) {
		return service.greetings(count, name);
	}
}