package hazelcast_examples.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Office implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private List<Employee>employees=new ArrayList<>();

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
