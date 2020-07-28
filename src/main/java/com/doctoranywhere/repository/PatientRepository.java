package com.doctoranywhere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctoranywhere.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

	Patient update(Patient entity);

	Optional<Patient> findById(int patientId);

	Patient save(Patient details);

	void deletePatientById(Long id);

	List<Patient> fetchAllPatients();

	Optional<Patient> findPatientById(Long id);

	Optional<Patient> findPatientByEmail(String email);

}
