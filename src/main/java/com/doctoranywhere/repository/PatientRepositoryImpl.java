package com.doctoranywhere.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.doctoranywhere.controller.PatientNotFoundException;
import com.doctoranywhere.model.Address;
import com.doctoranywhere.model.AddressRowMapper;
import com.doctoranywhere.model.Gender;
import com.doctoranywhere.model.Patient;

public class PatientRepositoryImpl implements PatientRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Patient update(Patient patient) {

		String sql = "update patients" + " set FIRST_NAME = ?, LAST_NAME = ?,  gender = ?, phone = ?, email = ? "
				+ " where id= ? ";
		jdbcTemplate.update(sql, patient.getFirstName(), patient.getLastName(), patient.getGender().name().charAt(0),
				patient.getPhone(), patient.getEmail(), patient.getId());
		return patient;
	}

	public Optional<Patient> findPatientByEmail(String email) {
		try {
			return Optional.of(jdbcTemplate.queryForObject("select * from patients where email=?",
					new Object[] { email }, new PatientRowMapper()));
		} catch (Exception e) {
			return null;
		}
	}

	class PatientRowMapper implements RowMapper<Patient> {
		@Override
		public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
			Patient patient = new Patient();
			patient.setId(rs.getLong("id"));
			patient.setFirstName(rs.getString("FIRST_NAME"));
			patient.setLastName(rs.getString("LAST_NAME"));
			patient.setEmail(rs.getString("EMAIL"));
			patient.setGender(Gender.valueOf(rs.getString("gender")));
			patient.setPhone(rs.getString("phone"));
			return patient;

		}
	}

	@Override
	public void deletePatientById(Long id) {
		jdbcTemplate.update("delete from patients where id=?", id);
		jdbcTemplate.update("delete from address where patient_id=?", id);
	}

	@Override
	public List<Patient> fetchAllPatients() {
		return jdbcTemplate.query("select * from patients", new PatientRowMapper());
	}

	@Override
	public Optional<Patient> findById(int id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject("select * from patients where id=?", new Object[] { id },
					new PatientRowMapper()));
		} catch (Exception e) {
			throw new PatientNotFoundException("Patient not found for patient id " + id);
		}
	}

	@Override
	public Patient save(Patient patient) {
		String sql = "insert into patients (FIRST_NAME, LAST_NAME,  gender, phone, email)  " + "values(?, ?, ?, ?,?)";
		long id = jdbcTemplate.update(sql, patient.getFirstName(), patient.getLastName(),
				patient.getGender().name().charAt(0), patient.getPhone(), patient.getEmail());
		return findPatientByEmail(patient.getEmail()).get();
	}

	@Override
	public <S extends Patient> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Patient> findById(Long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject("select * from patients where id=?", new Object[] { id },
					new PatientRowMapper()));
		} catch (Exception e) {
			throw new PatientNotFoundException("Patient not found for patient id " + id);
		}
	}

	@Override
	public boolean existsById(Long id) {
		return findById(id).isPresent();
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> patients = jdbcTemplate.query("select * from patients", new PatientRowMapper());
		return patients;

	}

	@Override
	public Iterable<Patient> findAllById(Iterable<Long> ids) {
		return null;
	}

	@Override
	public long count() {
		return findAll().size();
	}

	@Override
	public void deleteById(Long id) {
		deletePatientById(id);

	}

	@Override
	public void delete(Patient entity) {
		deletePatientById(entity.getId());

	}

	@Override
	public void deleteAll(Iterable<? extends Patient> entities) {
		deleteAll();

	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update("delete from patients ");
	}

	@Override
	public Optional<Patient> findPatientById(Long id) {
		return findById(id);
	}

}
