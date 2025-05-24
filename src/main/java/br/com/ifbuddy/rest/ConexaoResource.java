package br.com.ifbuddy.rest;

import br.com.ifbuddy.rest.dto.GerenciarConexaoDTO;
import br.com.ifbuddy.rest.dto.SolicitarConexaoDTO;
import br.com.ifbuddy.services.ConexaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/conexoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConexaoResource {
  @Inject
  ConexaoService conexaoService;

  @GET
  @Path("/{estudanteId}/confirmadas")
  public Response listarConexoesConfirmadas(@PathParam("estudanteId") Long estudanteId) {
    var resultado = conexaoService.listarConexoesConfirmadas(estudanteId);
    return Response.status(Response.Status.OK)
        .entity(resultado)
        .build();
  }

  @GET
  @Path("/{estudanteId}/solicitacoes")
  public Response listarSolicitacoesDeConexao(@PathParam("estudanteId") Long estudanteId) {
    var resultado = conexaoService.listarSolicitacoesDeConexao(estudanteId);
    return Response.status(Response.Status.OK)
        .entity(resultado)
        .build();
  }

  @POST
  public Response solicitarConexao(SolicitarConexaoDTO solicitarConexaoDTO) {
    var resultado = conexaoService.solicitarConexao(solicitarConexaoDTO);
    return Response.ok(Response.Status.CREATED)
        .entity(resultado)
        .build();
  }

  @PUT
  @Path("/aceitar")
  public Response aceitarConexao(GerenciarConexaoDTO gerenciarConexaoDTO) {
    var resultado = conexaoService.aceitarConexao(gerenciarConexaoDTO);
    return Response.status(Response.Status.OK)
        .entity(resultado)
        .build();
  }

  @Path("/recusar")
  @PUT
  public Response recusarConexao(GerenciarConexaoDTO gerenciarConexaoDTO) {
    var resultado = conexaoService.recusarConexao(gerenciarConexaoDTO);
    return Response.status(Response.Status.OK)
        .entity(resultado)
        .build();
  }

  @DELETE
  public Response desfazerConexao(GerenciarConexaoDTO gerenciarConexaoDTO) {
    var resultado = conexaoService.desfazerConexao(gerenciarConexaoDTO);
    return Response.status(Response.Status.OK)
        .entity(resultado)
        .build();
  }
}
