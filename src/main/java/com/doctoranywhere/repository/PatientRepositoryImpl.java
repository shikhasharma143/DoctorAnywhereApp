package com.doctoranywhere.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.doctoranywhere.model.Gender;
import com.doctoranywhere.model.Patient;

public class PatientRepositoryImpl implements PatientRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public <S extends Patient> S save(S entity) {
		String sql = "insert into patients (FIRST_NAME, LAST_NAME, age, gender,dob, phone, email, address_line1, address_line2, city,postal_code, country_code, state)  "
				+ "values(?, ?, ?, ?,?, ?, ?, ?,?,?,?, ?, ?)";
		long id = jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(), entity.getAge(),
				entity.getGender().name().charAt(0), entity.getDateOfBirth(), entity.getPhone(), entity.getEmail(),
				entity.getAddressLine1(), entity.getAddressLine2(), entity.getCity(), entity.getPostalCode(),
				entity.getCountry(), entity.getState());

		Optional<Patient> patient = findByFirstName(entity.getFirstName());
		return (S) patient.get();
	}

	public Patient update(Patient patient) {
		String sql = "update patients"
				+ " set FIRST_NAME = ?, LAST_NAME = ?, age = ?, gender = ?,dob = ?, phone = ?, email = ?, address_line1 = ?, address_line2 = ?, city= ? , postal_code= ?, country_code= ?, state= ? "
				+ " where id= ? ";
		long id = jdbcTemplate.update(sql, patient.getFirstName(), patient.getLastName(), patient.getAge(),
				patient.getGender().name().charAt(0), patient.getDateOfBirth(), patient.getPhone(), patient.getEmail(),
				patient.getAddressLine1(), patient.getAddressLine2(), patient.getCity(), patient.getPostalCode(),
				patient.getCountry(), patient.getState(), patient.getId());

		Optional<Patient> updatePatient = findByFirstName(patient.getFirstName());
		return updatePatient.get();
	}

	@Override
	public Optional<Patient> findById(Long id) {
		return Optional.of(jdbcTemplate.queryForObject("select * from patients where id=?", new Object[] { id },
				new PatientRowMapper()));
	}

	public Optional<Patient> findByFirstName(String firstName) {
		return Optional.of(jdbcTemplate.queryForObject("select * from patients where first_name=?",
				new Object[] { firstName }, new PatientRowMapper()));
	}

	@Override
	public boolean existsById(Long id) {
		return Optional.of(findById(id)).isPresent();
	}

	class PatientRowMapper implements RowMapper<Patient> {
		@Override
		public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
			Patient patient = new Patient();
			patient.setId(rs.getLong("id"));
			patient.setFirstName(rs.getString("FIRST_NAME"));
			patient.setLastName(rs.getString("LAST_NAME"));
			patient.setAge(rs.getInt("age"));
			Calendar tzCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			Date date = rs.getDate("dob", tzCal);
			patient.setDateOfBirth(new java.util.Date(date.getTime()));
			patient.setGender(Gender.valueOf(rs.getString("gender")));
			patient.setPhone(rs.getString("phone"));
			patient.setAddressLine1(rs.getString("address_line1"));
			patient.setAddressLine2(rs.getString("address_line2"));
			patient.setCity(rs.getString("city"));
			patient.setPostalCode(rs.getString("postal_code"));
			patient.setCountry(rs.getString("country_code"));
			patient.setState(rs.getString("state"));
			patient.setEmail(rs.getString("email"));
			return patient;

		}
	}

	@Override
	public void deleteById(Long id) {
		jdbcTemplate.update("delete from patients where id=?", id);

	}

	@Override
	public List<Patient> findAll() {
		return jdbcTemplate.query("select * from patients", new PatientRowMapper());
	}

	@Override
	public List<Patient> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Patient> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Patient> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Patient> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public Patient getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Patient> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Patient> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Patient> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Patient entity) {
		deleteById(entity.getId());
	}

	@Override
	public void deleteAll(Iterable<? extends Patient> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Patient> Optional<S> findOne(Example<S> example) {
		return null;
	}

	@Override
	public <S extends Patient> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Patient> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Patient> boolean exists(Example<S> example) {
		return false;
	}

}
