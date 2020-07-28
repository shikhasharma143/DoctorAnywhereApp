package com.doctoranywhere.service;

import java.util.List;
import java.util.Optional;

import com.doctoranywhere.model.Address;

public interface IAddressService {
	List<Address> findAllAddresses();

	Address findDefaultAddressesForPatient(int patientId);

	List<Address> findAllAddressesForPatient(int patientId);

	Optional<Address> findAddressById(int addressId);

	Address updateAddress(Address address);

	void deleteAddressById(int addressId);

	void deleteAllAddressesByPatientId(int patientId);

	void updateIsDefaultField(int addressId);

	void saveAddressForPatient(Address address, int patientId);

	List<Address> findNonDefaultAddressesForPatient(int patientId);
}
