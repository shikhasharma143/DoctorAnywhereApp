package com.doctoranywhere.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctoranywhere.controller.PatientNotFoundException;
import com.doctoranywhere.model.Patient;
import com.doctoranywhere.repository.PatientRepository;
import com.doctoranywhere.service.IPatientService;

@Service
public class PatienceServiceImpl implements IPatientService {
	@Autowired
	PatientRepository patientRepository;

	@Override
	public List<Patient> findAllPatients() {
		return (List<Patient>) patientRepository.fetchAllPatients();
	}

	@Override
	public Optional<Patient> findById(int patientId) {
		try {
			return patientRepository.findById(patientId);
		} catch (Exception e) {
			throw new PatientNotFoundException("Patient doesn't exists for id " + patientId);
		}
	}

	@Override
	public Patient save(Patient details) {
		return patientRepository.save(details);
	}

	public Patient update(Patient details) {
		return patientRepository.update(details);
	}

	@Override
	public void delete(Patient details) {
		patientRepository.deletePatientById(details.getId());
	}

	@Override
	public boolean isPatientAlreadyExists(String email) {
		return patientRepository.findPatientByEmail(email).isPresent();
	}

}
