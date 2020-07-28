package com.doctoranywhere.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.doctoranywhere.model.Address;
import com.doctoranywhere.model.Patient;
import com.doctoranywhere.model.PatientDetails;
import com.doctoranywhere.service.IAddressService;
import com.doctoranywhere.service.IPatientService;
import com.doctoranywhere.validator.PatientValidator;

@Controller
public class PatientController {
	@Autowired
	PatientValidator patientValidator;
	@Autowired
	IPatientService patientService;
	@Autowired
	IAddressService addressService;

	@GetMapping("/patients")
	public ResponseEntity<List<PatientDetails>> getAllPatients() {
		List<Patient> patients = patientService.findAllPatients();
		List<PatientDetails> patientDetails = new ArrayList<PatientDetails>();
		for (Patient patient : patients) {
			Address defaultAddress = addressService.findDefaultAddressesForPatient(patient.getId().intValue());
			patientDetails.add(new PatientDetails(patient, Arrays.asList(defaultAddress)));
		}
		return ResponseEntity.ok().body(patientDetails);
	}

	@GetMapping("/patients/{id}")
	public ResponseEntity<PatientDetails> getPatientById(@PathVariable(value = "id") int patientId)
			throws PatientNotFoundException {
		Patient patient = patientService.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found for this id :: " + patientId));
		List<Address> addressList = addressService.findAllAddressesForPatient(patient.getId().intValue());
		PatientDetails patientDetails = new PatientDetails(patient, addressList);
		return ResponseEntity.ok().body(patientDetails);
	}

	@PostMapping("/patients")
	public ResponseEntity<PatientDetails> addPatient(@Valid @RequestBody PatientDetails patientDetails)
			throws ValidationException {
		if (patientDetails.getAddressList().size() > 1) {
			throw new RuntimeException("Cannot add patient details with more than one address");
		}
		patientValidator.validate(patientDetails);
		Patient savedPatient = patientService.save(patientDetails.getPatient());
		if (savedPatient.getId() > 0) {
			Address address = patientDetails.getAddressList().get(0);
			address.setDefaultAddress(true);
			addressService.saveAddressForPatient(address, savedPatient.getId().intValue());
		}
		return new ResponseEntity<PatientDetails>(patientDetails, HttpStatus.OK);
	}

	@PutMapping("/patients/{id}")
	public ResponseEntity<PatientDetails> updatePatient(@PathVariable(value = "id") int patientId,
			@Valid @RequestBody PatientDetails patientDetails)
			throws PatientNotFoundException, AddressNotFoundException {
		patientValidator.validate(patientDetails);
		Patient patient = patientService.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found for this id :: " + patientId));
		patient.setEmail(patientDetails.getPatient().getEmail());
		patient.setLastName(patientDetails.getPatient().getLastName());
		patient.setFirstName(patientDetails.getPatient().getFirstName());
		patient.setGender(patientDetails.getPatient().getGender());
		patient.setPhone(patientDetails.getPatient().getPhone());

		List<Address> addressList = addressService.findAllAddressesForPatient(patientId);

		Address addressToUpdate = null;
		if (patientDetails.getAddressList().size() == 1) {
			Address addressRequest = patientDetails.getAddressList().get(0);
			Long addressId = addressRequest.getId();
			for (Address address : addressList) {
				if (address.getId() == addressId) {
					addressToUpdate = address;
					break;

				} else {
					throw new AddressNotFoundException("Address not found for this id : " + addressId);
				}
			}
			addressToUpdate.setAddressLine1(addressRequest.getAddressLine1());
			addressToUpdate.setAddressLine2(addressRequest.getAddressLine2());
			addressToUpdate.setCity(addressRequest.getCity());
			addressToUpdate.setCountry(addressRequest.getCountry());
			addressToUpdate.setState(addressRequest.getState());
			addressToUpdate.setId(addressId);
			addressToUpdate.setPostalCode(addressRequest.getPostalCode());
			addressToUpdate.setDefaultAddress(addressRequest.isDefaultAddress());
			addressToUpdate.setDefaultAddress(addressRequest.isDefaultAddress());
		} else {
			throw new RuntimeException("You cannot update more than one address at one time");
		}

		final Patient updatedPatient = patientService.update(patient);
		if (addressToUpdate.isDefaultAddress()) {
			for (Address addressForPatient : addressList) {
				if (addressForPatient.isDefaultAddress()) {
					addressService.updateIsDefaultField(addressForPatient.getId().intValue());
				}
			}
		}

		final Address updatedAddress = addressService.updateAddress(addressToUpdate);
		addressList = addressService.findAllAddressesForPatient(patientId);
		return ResponseEntity.ok(new PatientDetails(updatedPatient, addressList));
	}

	@DeleteMapping("/patients/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable(value = "id") int patientId)
			throws PatientNotFoundException {
		Patient patient = patientService.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found for this id :: " + patientId));
		patientService.delete(patient);
		addressService.deleteAllAddressesByPatientId(patientId);
		return ResponseEntity.ok("Patient deleted Successfully!");
	}
}
