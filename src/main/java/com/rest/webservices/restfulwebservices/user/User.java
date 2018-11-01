package com.rest.webservices.restfulwebservices.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer id;

	@Size(min = 2, message = "name should have atleast 2 character")
	private String name;

	@Past
	private Date birthData;

	@OneToMany(mappedBy="user")
	private List<Post> post;
	
	public User() {
	}

	public User(Integer id, String name, Date birthData) {
		super();
		this.id = id;
		this.name = name;
		this.birthData = birthData;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthData() {
		return birthData;
	}

	public void setBirthData(Date birthData) {
		this.birthData = birthData;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthData=" + birthData + "]";
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

}
