package com.doctoranywhere.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.doctoranywhere.exception.ValidationException;
import com.doctoranywhere.model.Patient;
import com.doctoranywhere.service.IPatientService;
import com.doctoranywhere.validator.PatientValidator;

@Controller
public class PatientController {
	@Autowired
	PatientValidator patientValidator;
	@Autowired
	IPatientService patientService;

	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = (List<Patient>) patientService.findAllPatients();
		return ResponseEntity.ok().body(patients);
	}

	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long patientId)
			throws PatientNotFoundException {
		Patient patient = patientService.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found for this id :: " + patientId));
		return ResponseEntity.ok().body(patient);
	}

	@PostMapping("/patient")
	public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) throws ValidationException {
		patientValidator.validate(patient);
		Patient savedPatient = patientService.save(patient);
		return new ResponseEntity<Patient>(savedPatient, HttpStatus.OK);
	}

	@PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable(value = "id") Long patientId,
			@Valid @RequestBody Patient patientDetails) throws PatientNotFoundException {
		patientValidator.validate(patientDetails);
		Patient patient = patientService.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found for this id :: " + patientId));

		patient.setEmail(patientDetails.getEmail());
		patient.setLastName(patientDetails.getLastName());
		patient.setFirstName(patientDetails.getFirstName());
		patient.setAge(patientDetails.getAge());
		patient.setDateOfBirth(patientDetails.getDateOfBirth());
		patient.setGender(patientDetails.getGender());
		patient.setAddressLine1(patientDetails.getAddressLine1());
		patient.setAddressLine2(patientDetails.getAddressLine2());
		patient.setCity(patientDetails.getCity());
		patient.setCountry(patientDetails.getCountry());
		patient.setState(patientDetails.getState());
		patient.setPostalCode(patientDetails.getPostalCode());
		patient.setPhone(patientDetails.getPhone());
		final Patient updatedPatient = patientService.update(patient);
		return ResponseEntity.ok(updatedPatient);
	}

	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Patient> deletePatient(@PathVariable(value = "id") Long patientId)
			throws PatientNotFoundException {
		Patient patient = patientService.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found for this id :: " + patientId));
		patientService.delete(patient);
		return ResponseEntity.ok(patient);
	}
}
