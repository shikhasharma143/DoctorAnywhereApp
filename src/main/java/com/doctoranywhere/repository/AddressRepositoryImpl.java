package com.doctoranywhere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.doctoranywhere.controller.AddressNotFoundException;
import com.doctoranywhere.model.Address;
import com.doctoranywhere.model.AddressRowMapper;

public class AddressRepositoryImpl implements AddressRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Address save(Address address) {
		String sql = "insert into address ( address_line1, address_line2, city,postal_code, country_code, state, patient_id, is_default )  "
				+ "values(?, ?, ?, ?,?,?, ?, ?)";
		int id = jdbcTemplate.update(sql, address.getAddressLine1(), address.getAddressLine2(), address.getCity(),
				address.getPostalCode(), address.getCountry(), address.getState(), address.getPatientId(),
				address.isDefaultAddress());
		return findAddressById(id).get();
	}

	@Override
	public <S extends Address> Iterable<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public Optional<Address> findById(Long id) {
		return Optional.of(jdbcTemplate.queryForObject("select * from address where id=?",
				new Object[] { id.intValue() }, new AddressRowMapper()));
	}

	@Override
	public boolean existsById(Long id) {
		return findById(id).isPresent();
	}

	@Override
	public Iterable<Address> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		return findAll().size();
	}

	@Override
	public void deleteById(Long id) {
		deleteAddressById(id.intValue());
	}

	@Override
	public void delete(Address entity) {
		deleteAddressById(entity.getId().intValue());
	}

	@Override
	public void deleteAll(Iterable<? extends Address> entities) {
		deleteAll();

	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update("delete from address ");
	}

	@Override
	public List<Address> findAllByPatientId(int patientId) {
		try {
			return jdbcTemplate.query("select * from address where patient_id=?", new Object[] { patientId },
					new AddressRowMapper());
		} catch (Exception e) {
			throw new AddressNotFoundException("Address not found for patient id" + patientId);
		}
	}

	@Override
	public Optional<Address> findAddressById(int addressId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject("select * from address where id=?",
					new Object[] { addressId }, new AddressRowMapper()));
		} catch (Exception e) {
			throw new AddressNotFoundException("Address not found for address id : " + addressId);
		}

	}

	@Override
	public Address updateAddress(Address address) {
		String sql = "update address"
				+ " set address_line1 = ?, address_line2 = ?, city= ? , postal_code= ?, country_code= ? , state= ?, is_default=?"
				+ " where id= ? ";
		jdbcTemplate.update(sql, address.getAddressLine1(), address.getAddressLine2(), address.getCity(),
				address.getPostalCode(), address.getCountry(), address.getState(), address.isDefaultAddress(),
				address.getId());
		return address;
	}

	@Override
	public void deleteAddressById(int addressId) {
		jdbcTemplate.update("delete from address where id=?", addressId);
	}

	@Override
	public void updateIsDefaultField(int addressId) {
		jdbcTemplate.update("update address set is_default=FALSE where is_default=TRUE and id=?", addressId);
	}

	@Override
	public List<Address> findAll() {
		List<Address> addressList = jdbcTemplate.query("select * from address", new AddressRowMapper());
		return addressList;
	}

	@Override
	public Address findDefaultAddressByPatientId(int patientId) {
		try {
			return jdbcTemplate.queryForObject("select * from address where  is_default=true and patient_id=? ",
					new Object[] { patientId }, new AddressRowMapper());
		} catch (Exception e) {
			throw new AddressNotFoundException("Address not found for patient id" + patientId);
		}
	}

	@Override
	public void deleteAddressesByPatientId(int patientId) {
		jdbcTemplate.update("delete from address where patient_id=?", patientId);
	}

	@Override
	public List<Address> findNonDefaultAddressesForPatient(int patientId) {
		try {
			return jdbcTemplate.query("select * from address where is_default= false and patient_id=?",
					new Object[] { patientId }, new AddressRowMapper());
		} catch (Exception e) {
			throw new AddressNotFoundException("Address not found for patient id" + patientId);
		}
	}

}
