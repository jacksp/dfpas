package com.prueba.cxfrestservice;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
@WebService(name="employeeService")
public interface CxfRestService 
{
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/getemployeedetail")
	/*
	 * cabeceras de web services devuelve detalles de emplaeado
	 */
	public Response getEmployeeDetail(@QueryParam("employeeId") String employeeId);
	

	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
	@Path("/listEmployees")
	/*
	 * cabeceras de web services devuelve listado de emplaeado
	 */
	public Response listEmployees();
	
}
