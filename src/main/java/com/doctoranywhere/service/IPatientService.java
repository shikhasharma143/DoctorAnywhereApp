package com.doctoranywhere.service;

import java.util.List;
import java.util.Optional;

import com.doctoranywhere.model.Patient;

public interface IPatientService {
	List<Patient> findAllPatients();

	Optional<Patient> findById(Long patientId);

	Patient save(Patient patient);

	Patient update(Patient patient);

	void delete(Patient patient);
}
