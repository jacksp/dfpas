package com.prueba.cxfrestservice.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="employee")
public class Employee 
{
	  private String firstName;
	    private String lastName;
	    private String dateBirth;
	    private String position;

	    public Employee() {
	    }

	    public String getFirstName() {
	        return this.firstName;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public String getLastName() {
	        return this.lastName;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }

	    public String getDateBirth() {
			return dateBirth;
		}

		public void setDateBirth(String dateBirth) {
			this.dateBirth = dateBirth;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

}
