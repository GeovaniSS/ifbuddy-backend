package br.com.ifbuddy.rest;

import br.com.ifbuddy.rest.dto.PerfilEstudanteDTO;
import br.com.ifbuddy.services.PerfilService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/perfil")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PerfilResource {
  @Inject
  PerfilService perfilService;

  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response consultarPerfilDoEstudante(@PathParam("id") Long id) {
    var resposta = perfilService.consultarPerfilEstudante(id);
    return Response.ok(resposta).build();
  }

  @PATCH
  @Produces(MediaType.APPLICATION_JSON)
  public Response atualizarPerfilEstudante(PerfilEstudanteDTO perfilEstudanteDTO) {
    var resposta = perfilService.atualizarPerfilEstudante(perfilEstudanteDTO);
    return Response.ok(resposta).build();
  }
}
