package com.doctoranywhere.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.doctoranywhere.validator.Phone;

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
	@Column(name = "gender", nullable = false)
	private Gender gender;
	@Phone
	@Column(name = "phone")
	private String phone;
	@Email
	@NotBlank(message = "Enter a valid email address.")
	@Column(name = "email", nullable = false)
	private String email;

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
