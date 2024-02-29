package exception;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Set;

@Provider
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {


    @Override
    public Response toResponse(RuntimeException e) {
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        ;
        String message = e.getMessage();
        if (e.getCause() instanceof ConstraintViolationException) {
            status = Response.Status.BAD_REQUEST.getStatusCode();
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e.getCause()).getConstraintViolations();
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<?> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
            }
            message = errorMessage.toString().trim();
        }
        if (e instanceof WebApplicationException) {
            status = ((WebApplicationException) e).getResponse().getStatus();
            message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        }
        return Response.status(status).
                entity(new ErrorMessage(message, false)).build();
    }
}
