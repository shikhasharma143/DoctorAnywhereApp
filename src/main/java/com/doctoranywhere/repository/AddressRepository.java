package com.doctoranywhere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctoranywhere.model.Address;
import com.doctoranywhere.model.Patient;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
	List<Address> findAllByPatientId(int patientId);

	Optional<Address> findAddressById(int addressId);

	Address updateAddress(Address address);

	void deleteAddressById(int addressId);

	void deleteAddressesByPatientId(int patientId);

	void updateIsDefaultField(int addressId);

	List<Address> findAll();

	Address findDefaultAddressByPatientId(int patientId);

	List<Address> findNonDefaultAddressesForPatient(int patientId);

	Address save(Patient details);

}
