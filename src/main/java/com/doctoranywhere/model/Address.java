package com.doctoranywhere.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ADDRESS")
public class Address {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@OneToMany
//	@PrimaryKeyJoinColumn(name = "id")
	@Column(name = "PATIENT_ID", nullable = false)
	private int patientId;
	@Column(name = "address_line1", nullable = false)
	private String addressLine1;
	@Column(name = "address_line2")
	private String addressLine2;
	@Column(name = "city")
	private String city;
	@Column(name = "country_code")
	private String country;
	@Column(name = "postal_code")
	private String postalCode;
	@Column(name = "state")
	private String state;
	@Column(name = "IS_DEFAULT")
	private boolean isDefaultAddress;

}
