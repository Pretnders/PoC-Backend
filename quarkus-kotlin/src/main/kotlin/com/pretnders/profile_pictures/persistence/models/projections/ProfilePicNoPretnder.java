package com.pretnders.profile_pictures.persistence.models.projections;

public class ProfilePicNoPretnder {
	private Long id;
	private Short order;
	private String reference;
	
	public ProfilePicNoPretnder(Long id, Short order, String reference) {
		this.id = id;
		this.order = order;
		this.reference = reference;
	}
	
	@Override
	public String toString() {
		return "ProfilePicNoPretnder{" +
					   "id=" + id +
					   ", order=" + order +
					   ", reference='" + reference + '\'' +
					   '}';
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Short getOrder() {
		return order;
	}
	
	public void setOrder(Short order) {
		this.order = order;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
}
