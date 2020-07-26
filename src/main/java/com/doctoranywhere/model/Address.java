package com.doctoranywhere.model;

import javax.persistence.Column;

import lombok.Data;

@Data
public class Address {
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
}
