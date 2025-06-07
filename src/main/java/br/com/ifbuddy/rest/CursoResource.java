package br.com.ifbuddy.rest;

import br.com.ifbuddy.services.CursoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cursos")
public class CursoResource {
  @Inject
  CursoService cursoService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response listarCursos() {
    var resposta = cursoService.listarCursos();
    return Response.ok(resposta).build();
  }
}
