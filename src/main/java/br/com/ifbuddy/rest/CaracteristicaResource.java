package br.com.ifbuddy.rest;

import br.com.ifbuddy.services.CaracteristicaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/caracteristicas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CaracteristicaResource {
  @Inject
  CaracteristicaService caracteristicaService;

  @GET
  public Response listarCaracteristicas() {
    var caracteristicas = caracteristicaService.listarCaracteristicas();
    return Response.ok(caracteristicas).build();
  }
}
