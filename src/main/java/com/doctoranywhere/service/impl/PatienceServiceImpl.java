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
		return (List<Patient>) patientRepository.findAll();
	}

	@Override
	public Optional<Patient> findById(Long patientId) {
		try {
			return patientRepository.findById(patientId);
		} catch (Exception e) {
			throw new PatientNotFoundException("Patient doesn't exists for id " + patientId);
		}
	}

	@Override
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}

	public Patient update(Patient patient) {
		return patientRepository.update(patient);
	}

	@Override
	public void delete(Patient patient) {
		patientRepository.delete(patient);
	}

}
