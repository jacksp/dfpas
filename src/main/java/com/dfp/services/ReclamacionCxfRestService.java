package com.dfp.services;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;


@Path("/")
@WebService(name="claimService")
public interface ReclamacionCxfRestService 
{
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/getClaimDetail")
	/*
	 * cabeceras de web services devuelve detalles de emplaeado
	 */
	public Response getClaimDetail(@QueryParam("claimId") String claimId);
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/aceptaSiguienteEstadoReclamacion")
	/*
	 * cabeceras de web services devuelve listado de emplaeado
	 */
	public Response aceptaSiguienteEstadoReclamacion(@QueryParam("claimId") String claimId);
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/rechazaSiguienteEstadoReclamacion")
	/*
	 * cabeceras de web services devuelve listado de emplaeado
	 */
	public Response rechazaSiguienteEstadoReclamacion(@QueryParam("claimId") String claimId);
	

	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/listClaims")
	/*
	 * cabeceras de web services devuelve listado de emplaeado
	 */
	public Response listClaims();
	
//	@GET
//	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
//	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
//	@Path("/insertClaim")
//	/*
//	 * cabeceras de web services devuelve listado de emplaeado
//	 */
//	public Response insertClaim(ReclamacionDTO claimDTO);
	
	
	@POST
	@Path("/enviaMail")
	public Response enviaMail(@Context HttpServletRequest request);

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/enviaMail2")
	Response enviaMail2(@Multipart("note") String note);
	
	
}
