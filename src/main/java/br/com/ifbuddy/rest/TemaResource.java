package br.com.ifbuddy.rest;

import br.com.ifbuddy.rest.dto.FiltrosDTO;
import br.com.ifbuddy.services.TemaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/temas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TemaResource {
  @Inject
  TemaService temaService;

  @GET
  public Response listarTemas(FiltrosDTO filtrosDTO) {
    var temas = temaService.listarTemas();
    return Response.ok(temas).build();
  }
}
