package com.doctoranywhere.controller;

import java.util.ArrayList;
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

import com.doctoranywhere.exception.PatientAlreadyExistsException;
import com.doctoranywhere.exception.ValidationException;
import com.doctoranywhere.model.Address;
import com.doctoranywhere.model.Patient;
import com.doctoranywhere.model.PatientDetails;
import com.doctoranywhere.service.IAddressService;

@Controller
public class AddressController {
	@Autowired
	IAddressService addressService;

	@GetMapping("/addresses")
	public ResponseEntity<List<Address>> getAllAddressList() {
		List<Address> addresses = (List<Address>) addressService.findAllAddresses();
		return ResponseEntity.ok().body(addresses);
	}

	@GetMapping("/addresses/patients/{patientId}")
	public ResponseEntity<List<Address>> getAllAddressForPatient(@PathVariable(value = "patientId") int patientId) {
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(addressService.findDefaultAddressesForPatient(patientId));
		addresses.addAll(addressService.findNonDefaultAddressesForPatient(patientId));
		return ResponseEntity.ok().body(addresses);
	}

	@GetMapping("/addresses/{addressId}")
	public ResponseEntity<Address> getAddressById(@PathVariable(value = "addressId") int addressId) {
		Address addresses = (Address) addressService.findAddressById(addressId)
				.orElseThrow(() -> new AddressNotFoundException("Address not found for this id : " + addressId));
		return ResponseEntity.ok().body(addresses);
	}

	@DeleteMapping("/addresses/{addressId}")
	public ResponseEntity<Address> deleteAddressById(@PathVariable(value = "addressId") int addressId) {
		Address addresses = addressService.findAddressById(addressId)
				.orElseThrow(() -> new AddressNotFoundException("Address not found for this id : " + addressId));
		addressService.deleteAddressById(addressId);
		return ResponseEntity.ok().body(addresses);
	}

	@PutMapping("/address/{addressId}")
	public ResponseEntity<Address> updateAddress(@PathVariable(value = "addressId") int addressId,
			@Valid @RequestBody Address addressRequest) {
		Address addressToUpdate = (Address) addressService.findAddressById(addressId)
				.orElseThrow(() -> new AddressNotFoundException("Address not found for this id : " + addressId));
		addressToUpdate.setAddressLine1(addressRequest.getAddressLine1());
		addressToUpdate.setAddressLine2(addressRequest.getAddressLine2());
		addressToUpdate.setCity(addressRequest.getCity());
		addressToUpdate.setCountry(addressRequest.getCountry());
		addressToUpdate.setState(addressRequest.getState());
		addressToUpdate.setPostalCode(addressRequest.getPostalCode());
		addressToUpdate.setDefaultAddress(addressRequest.isDefaultAddress());
		
		// if make default address then change first the earlier default address
		if (addressRequest.isDefaultAddress()) {
			List<Address> addressesForPatient = addressService
					.findAllAddressesForPatient(addressToUpdate.getPatientId());
			for (Address addressForPatient : addressesForPatient) {
				if (addressForPatient.isDefaultAddress()) {
					addressService.updateIsDefaultField(addressForPatient.getId().intValue());
				}
			}
		}
		final Address updateAddress = addressService.updateAddress(addressToUpdate);
		return ResponseEntity.ok().body(updateAddress);
	}

	@PostMapping("/address/{patientId}")
	public ResponseEntity<Address> addPatient(@Valid @RequestBody Address address,
			@PathVariable(value = "patientId") int patientId) throws ValidationException {
		addressService.saveAddressForPatient(address, patientId);
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
}
