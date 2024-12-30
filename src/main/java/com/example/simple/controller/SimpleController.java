package com.example.simple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.simple.entity.Users;
import com.example.simple.model.UsersDetails;
import com.example.simple.service.SimpleService;

@RestController
public class SimpleController implements SimpleControllerAPI {

	@Autowired
	SimpleService simpleService;

	@Override
	public List<UsersDetails> getAllUserDetails() {

		return simpleService.getAllUsersDetails();
	}

	@Override
	public Users addUserDetails(UsersDetails usersDetails) {

		return simpleService.addUsersDetails(usersDetails);
	}

}
