package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resume")
@NoArgsConstructor
@Data
public class Resume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	// path of the file in the system
	@Column(length = 100, unique = true)
	private String path;

	// name of the file
	@Column(length = 100, unique = true)
	private String name;

	public Resume(String path, String name) {
		super();
		this.path = path;
		this.name = name;
	}

}
