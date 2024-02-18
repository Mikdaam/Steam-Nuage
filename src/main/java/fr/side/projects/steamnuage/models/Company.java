package fr.side.projects.steamnuage.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "companies")
public class Company {
	@Id
	private String name;
	private String country;

	public Company(String name, String country) {
		this.name = name;
		this.country = country;
	}
}
