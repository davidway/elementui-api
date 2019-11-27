package com.cxytiandi.elementui.model;

import com.cxytiandi.elementui.enums.UserSexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
	private Long id;
	private String name;
	private String username;
	private String password;
	private Integer age;
	private String email;


}