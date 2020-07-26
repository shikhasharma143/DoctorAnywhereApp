package com.doctoranywhere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctoranywhere.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	Patient update(Patient entity);
}
