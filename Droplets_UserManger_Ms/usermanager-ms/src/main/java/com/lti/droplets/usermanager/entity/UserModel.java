package com.lti.droplets.usermanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
public class UserModel {
	
	@SequenceGenerator(
			name = "userId_sequence",
			sequenceName = "userId_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "userId_sequence"
	)
	private Long userId;
	private String userName;
	private String userEmail;
	private String userMobileNumber;
	private String address;
	private Boolean isActivated;
	private String password;

}
