package com.dfp.services;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
@WebService(name="claimService")
public interface ReclamacionCxfAdjuntosService 
{
    
   
	@POST
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/enviaMail")
	public Response enviaMail();
	
	

	
}
