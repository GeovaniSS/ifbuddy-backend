package br.com.ifbuddy.rest;

import br.com.ifbuddy.services.EnderecoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/enderecos")
public class EnderecoResource {
  @Inject
  EnderecoService enderecoService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response listarEnderecos() {
    var resposta = enderecoService.listarEnderecos();
    return Response.ok(resposta).build();
  }
}
