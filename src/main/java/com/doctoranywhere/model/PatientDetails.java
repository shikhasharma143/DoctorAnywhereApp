package com.doctoranywhere.model;

import java.util.List;

import lombok.Data;

@Data
public class PatientDetails {
	Patient patient;
	List<Address> addressList;

	public PatientDetails() {

	}

	public PatientDetails(Patient patient, List<Address> addressList) {
		this.patient = patient;
		this.addressList = addressList;
	}
}
