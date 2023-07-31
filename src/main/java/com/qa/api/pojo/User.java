package com.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Builder
public class User {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("email")
	private String email;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("status")
	private String status;

	public User(String name, String email, String gender, String status) {
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.status = status;
	}

}
