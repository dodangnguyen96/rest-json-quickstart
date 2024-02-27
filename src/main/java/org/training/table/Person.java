package org.training.table;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private LocalDate birth;
	private Status status;
    @Id
    @SequenceGenerator(name = "personSeq", sequenceName = "person_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "personSeq")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
	@Column(length = 128)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}