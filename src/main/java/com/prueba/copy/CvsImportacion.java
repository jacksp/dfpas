package com.prueba.copy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.prueba.cxfrestservice.model.Employee;

public class CvsImportacion {
	//Delimitador de las filas del csv
    private static final String NEW_LINE_SEPARATOR = "\n";

    //Atributos de cada empleado
    private static final String employeeFormat[] = {"FIRST_NAME","LAST_NAME","BIRTH_DATE","POSITION"};



    public  List<Employee> importData() throws FileNotFoundException, IOException {
        //Crear el CSVFormat object
    	 CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
    	 String jrxmlFileName =   System.getenv("OPENSHIFT_DATA_DIR");
    	 
    	  FileInputStream fis = new FileInputStream(jrxmlFileName+"/test/employees.csv");
          InputStream is = fis;
//         
//         InputStream csv = 
//         		this.getClass().getClassLoader().getResourceAsStream(jrxmlFileName+"/test/employees.csv");
         Reader reader = new InputStreamReader(is);
         CSVParser parser = new CSVParser(reader, format);
         List<Employee> emps = new ArrayList<Employee>();
         for(CSVRecord record : parser){
             Employee emp = new Employee();
             emp.setFirstName(record.get(employeeFormat[0]));
             emp.setLastName(record.get(employeeFormat[1]));
             emp.setDateBirth(record.get(employeeFormat[2]));
             emp.setPosition(record.get(employeeFormat[3]));
             emps.add(emp);
         }
         parser.close();
         fis.close();  
        return emps;
    }

}
