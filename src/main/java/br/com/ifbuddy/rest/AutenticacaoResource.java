package br.com.ifbuddy.rest;

import br.com.ifbuddy.rest.dto.CadastroDTO;
import br.com.ifbuddy.rest.dto.LoginDTO;
import br.com.ifbuddy.services.AutenticacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/autenticacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutenticacaoResource {
  @Inject
  AutenticacaoService autenticacaoService;

  @Path("/cadastro")
  @POST
  public Response cadastro(@Valid CadastroDTO cadastroDTO) {
    var resultado = autenticacaoService.cadastro(cadastroDTO);
    return Response.ok(Response.Status.CREATED)
        .entity(resultado)
        .build();
  }

  @Path("/login")
  @POST
  public Response login(LoginDTO loginDTO) {
    var resultado = autenticacaoService.login(loginDTO);
    return Response.status(Response.Status.OK)
        .entity(resultado)
        .build();
  }

  @Path("/valida-token")
  @POST
  public Response validaToken() {
    var resultado = autenticacaoService.validarToken();
    return Response.status(Response.Status.OK)
        .entity(resultado)
        .build();
  }
}