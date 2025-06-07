package br.com.ifbuddy.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

  @Context
  private Request request;

  @Override
  public Response toResponse(Exception e) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", Instant.now().toString());
    body.put("error", e.getClass().getSimpleName());

    if (e instanceof BusinessException) {
      body.put("status", Response.Status.BAD_REQUEST.getStatusCode());
      body.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST)
          .type(MediaType.APPLICATION_JSON)
          .entity(body)
          .build();
    }

    if (e instanceof NotFoundException) {
      body.put("status", Response.Status.NOT_FOUND.getStatusCode());
      body.put("message", e.getMessage());
      return Response.status(Response.Status.NOT_FOUND)
          .type(MediaType.APPLICATION_JSON)
          .entity(body)
          .build();
    }

    if (e instanceof IllegalStateException || e instanceof IllegalArgumentException) {
      body.put("status", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
      body.put("message", e.getMessage());
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .type(MediaType.APPLICATION_JSON)
          .entity(body)
          .build();
    }

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .type(MediaType.APPLICATION_JSON)
        .entity("Erro: Entre em contato com o suporte")
        .build();
  }
}