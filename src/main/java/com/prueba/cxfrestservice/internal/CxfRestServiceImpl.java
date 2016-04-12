package com.prueba.cxfrestservice.internal;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.prueba.cxfrestservice.CxfRestService;
import com.prueba.cxfrestservice.dao.EmployeeDao;

public class CxfRestServiceImpl implements CxfRestService 
{
	@Autowired
	private EmployeeDao employeeDao; 

	@Override
	public Response getEmployeeDetail(String employeeId) 
	{
		if(employeeId == null)
		{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}		
		return Response.ok(employeeDao.getEmployeeDetails(employeeId)).build();
	}
	
	@Override
	public Response listEmployees() 
	{
		return Response.ok(employeeDao.listEmployees()).build();
	}
}
