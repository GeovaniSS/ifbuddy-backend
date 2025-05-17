package br.com.ifbuddy.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.*;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    List<Map<String, String>> errors = new ArrayList<>();

    for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      Map<String, String> error = new HashMap<>();
      String field = violation.getPropertyPath().toString().split("\\.")[2];
      error.put("field", field);
      error.put("message", violation.getMessage());
      errors.add(error);
    }

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("timestamp", Instant.now().toString());
    responseBody.put("status", Response.Status.BAD_REQUEST.getStatusCode());
    responseBody.put("errors", errors);

    return Response
        .status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(responseBody)
        .build();
  }
}
