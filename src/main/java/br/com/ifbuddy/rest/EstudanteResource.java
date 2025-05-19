package br.com.ifbuddy.rest;

import br.com.ifbuddy.rest.dto.FiltrosDTO;
import br.com.ifbuddy.services.EstudanteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/estudantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudanteResource {
  @Inject
  EstudanteService estudanteService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response listarEstudantes(FiltrosDTO filtrosDTO) {
    var resposta = estudanteService.listarEstudantes(filtrosDTO);
    return Response.ok(resposta).build();
  }

  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response buscarEstudantePorId(@PathParam("id") Long id) {
    var resposta = estudanteService.buscarEstudantePorId(id);
    return Response.ok(resposta).build();
  }
}
