package com.doctoranywhere.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AddressRowMapper implements RowMapper<Address> {
	@Override
	public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
		Address address = new Address();
		address.setAddressLine1(rs.getString("address_line1"));
		address.setAddressLine2(rs.getString("address_line2"));
		address.setCity(rs.getString("city"));
		address.setPostalCode(rs.getString("postal_code"));
		address.setCountry(rs.getString("country_code"));
		address.setState(rs.getString("state"));
		address.setId(rs.getLong("id"));
		address.setDefaultAddress(rs.getBoolean("is_default"));
		address.setPatientId(rs.getInt("patient_id"));
		return address;

	}
}