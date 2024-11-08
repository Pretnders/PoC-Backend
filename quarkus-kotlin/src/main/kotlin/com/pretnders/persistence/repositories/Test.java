package com.pretnders.persistence.repositories;

public class Test {
	private Long id;
	private String reference;
	
	public Test() {
	}
	
	public Test(Long id, String reference) {
		this.id = id;
		this.reference = reference;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@Override
	public String toString() {
		return "Test{" +
					   "id=" + id +
					   ", reference='" + reference + '\'' +
					   '}';
	}
}
