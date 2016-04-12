package com.prueba.cxfrestservice.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.prueba.cxfrestservice.model.Employee;
import com.prueba.copy.CvsImportacion;

public class EmployeeDao 
{
	/**
	 * devuelve la lista de empleados
	 */
	public List<Employee> listEmployees() {
		List<Employee> oListEmployee = new ArrayList<Employee>();
		CvsImportacion oCvsImportacion = new CvsImportacion();

		try {
			oListEmployee = oCvsImportacion.importData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return oListEmployee;
	}

	/**
	 * acceso a datos según el apellido del empleado
	 * @param employeeLastName
	 * @return
	 */
	public Employee getEmployeeDetails(String employeeLastName) {
		List<Employee> oListEmployee = this.listEmployees();
		int index = 0;
		while (index < oListEmployee.size() && !oListEmployee.get(index).getLastName().equals(employeeLastName)) {
			index++;
		}
			
		if (index < oListEmployee.size())
			return ((Employee)oListEmployee.get(index));
		else
			return null;
	}
}
