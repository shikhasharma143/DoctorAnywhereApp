package com.doctoranywhere.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctoranywhere.model.Address;
import com.doctoranywhere.repository.AddressRepository;
import com.doctoranywhere.service.IAddressService;

@Service
public class AddressServiceImpl implements IAddressService {
	@Autowired
	AddressRepository addressRepository;

	@Override
	public List<Address> findAllAddresses() {
		return addressRepository.findAll();
	}

	@Override
	public List<Address> findAllAddressesForPatient(int patientId) {
		return addressRepository.findAllByPatientId(patientId);
	}

	@Override
	public Optional<Address> findAddressById(int addressId) {
		return addressRepository.findAddressById(addressId);
	}

	@Override
	public Address updateAddress(Address address) {
		return addressRepository.updateAddress(address);
	}

	@Override
	public void deleteAddressById(int addressId) {
		addressRepository.deleteAddressById(addressId);
	}

	@Override
	public void updateIsDefaultField(int addressId) {
		addressRepository.updateIsDefaultField(addressId);
	}

	@Override
	public Address findDefaultAddressesForPatient(int patientId) {
		return addressRepository.findDefaultAddressByPatientId(patientId);
	}

	@Override
	public void saveAddressForPatient(Address address, int patientId) {
		address.setPatientId(patientId);
		addressRepository.save(address);
	}

	@Override
	public void deleteAllAddressesByPatientId(int patientId) {
		addressRepository.deleteAddressesByPatientId(patientId);

	}

	@Override
	public List<Address> findNonDefaultAddressesForPatient(int patientId) {
		return addressRepository.findNonDefaultAddressesForPatient(patientId);
	}

}
