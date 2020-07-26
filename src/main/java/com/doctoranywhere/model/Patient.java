package com.doctoranywhere.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.doctoranywhere.validator.Phone;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "age", nullable = false)
	private int age;
	@Column(name = "gender", nullable = false)
	private Gender gender;
	@JsonDeserialize(using = DateDeSerializer.class)
	@JsonSerialize(using = CDJsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(dataType = "java.lang.String", example = "yyyy-MM-dd")
	@Past(message = "Date input is invalid for a birth date.")
	@Column(name = "dob", nullable = true)
	private Date dateOfBirth;
	@Phone
	@Column(name = "phone")
	private String phone;
	@Email
	@NotBlank(message = "Enter a valid email address.")
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "address_line1", nullable = false)
	private String addressLine1;
	@Column(name = "address_line2")
	private String addressLine2;
	@Column(name = "city")
	private String city;
	@Column(name = "country_code")
	private String country;
	@Column(name = "postal_code")
	private String postalCode;
	@Column(name = "state")
	private String state;

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.id);
		hash = 79 * hash + Objects.hashCode(this.firstName);
		hash = 79 * hash + Objects.hashCode(this.lastName);
		hash = 79 * hash + Objects.hashCode(this.email);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Patient other = (Patient) obj;

		if (!Objects.equals(this.firstName, other.firstName) || !Objects.equals(this.lastName, other.lastName)
				|| !Objects.equals(this.email, other.email)) {
			return false;
		}
		return Objects.equals(this.id, other.id);
	}

}
